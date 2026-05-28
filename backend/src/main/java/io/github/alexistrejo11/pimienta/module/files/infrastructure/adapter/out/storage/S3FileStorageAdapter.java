package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.out.storage;

import io.github.alexistrejo11.pimienta.module.files.core.port.output.FileStoragePort;
import io.github.alexistrejo11.pimienta.shared.storage.S3PresignService;
import java.time.Instant;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Component
public class S3FileStorageAdapter implements FileStoragePort {

  private static final Logger log = LoggerFactory.getLogger(S3FileStorageAdapter.class);

  private final S3Client s3Client;
  private final S3PresignService s3PresignService;
  private final String bucketName;

  public S3FileStorageAdapter(
      S3Client s3Client,
      S3PresignService s3PresignService,
      @Value("${aws.s3.bucket}") String bucketName) {
    this.s3Client = s3Client;
    this.s3PresignService = s3PresignService;
    this.bucketName = bucketName;
  }

  @Override
  public String store(MultipartFile file, String s3Key) {
    try {
      byte[] bytes = file.getBytes();
      String contentType = file.getContentType() != null
          ? file.getContentType()
          : "application/octet-stream";

      PutObjectRequest request = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(s3Key)
          .contentType(contentType)
          .contentLength((long) bytes.length)
          .metadata(Map.of(
              "original-name",
              file.getOriginalFilename() != null ? file.getOriginalFilename() : "",
              "uploaded-at",
              Instant.now().toString()))
          .build();

      s3Client.putObject(request, RequestBody.fromBytes(bytes));
      log.info("file stored bucket={} key={}", bucketName, s3Key);
      return s3Key;
    } catch (S3Exception e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Failed to store file in S3: " + s3Key, e);
    }
  }

  @Override
  public void delete(String s3Key) {
    try {
      s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(s3Key).build());
      log.info("file deleted bucket={} key={}", bucketName, s3Key);
    } catch (S3Exception e) {
      log.warn("S3 delete failed key={} — treating as no-op", s3Key, e);
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete file from S3: " + s3Key, e);
    }
  }

  @Override
  public String presignedDownloadUrl(String s3Key) {
    return s3PresignService.presign24h(s3Key);
  }
}
