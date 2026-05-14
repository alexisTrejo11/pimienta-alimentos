package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Component
public class ImageProcessor {
  @Value("${pimienta.image.max-width}")
  private int maxWidth;

  @Value("${pimienta.image.max-height}")
  private int maxHeight;

  @Value("${pimienta.image.quality}")
  private float quality;

  private record Dimension(int width, int height) {
  }

  public byte[] proccessImage(MultipartFile file) throws IOException {
    var originalImage = ImageIO.read(file.getInputStream());

    if (originalImage == null) {
      throw new IOException("Error reading image. Maybe format is not supported");
    }

    var newSize = calculateDimensions(
        originalImage.getWidth(),
        originalImage.getHeight());

    var resizedImage = Thumbnails.of(originalImage)
        .size(newSize.width, newSize.height)
        .keepAspectRatio(true)
        .asBufferedImage();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    String format = getFormatName(file.getContentType());

    Thumbnails.of(resizedImage)
        .scale(1)
        .outputFormat(format)
        .outputQuality(quality)
        .toOutputStream(baos);

    return baos.toByteArray();
  }

  private Dimension calculateDimensions(int originalWidth, int originalHeight) {
    double widthRatio = (double) originalWidth / maxWidth;
    double heightRatio = (double) originalHeight / maxHeight;
    double ratio = Math.min(widthRatio, heightRatio);

    if (ratio >= 1.0) {
      return new Dimension(originalWidth, originalHeight);
    }

    int newWidth = (int) (originalWidth / ratio);
    int newHeight = (int) (originalHeight / ratio);

    return new Dimension(newWidth, newHeight);
  }

  private String getFormatName(String contentType) {
    return contentType.contains("png") ? "png" : "jpg";
  }

  /** Content-Type for the bytes produced by {@link #proccessImage(MultipartFile)}. */
  public String outputContentType(String fileContentType) {
    if (fileContentType != null && fileContentType.toLowerCase().contains("png")) {
      return "image/png";
    }
    return "image/jpeg";
  }
}
