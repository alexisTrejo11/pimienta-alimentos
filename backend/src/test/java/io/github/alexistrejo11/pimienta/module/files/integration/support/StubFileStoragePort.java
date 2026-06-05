package io.github.alexistrejo11.pimienta.module.files.integration.support;

import io.github.alexistrejo11.pimienta.module.files.core.port.output.FileStoragePort;
import java.nio.charset.StandardCharsets;
import org.springframework.web.multipart.MultipartFile;

/**
 * In-memory {@link FileStoragePort} for integration tests. Never talks to AWS S3.
 */
public final class StubFileStoragePort implements FileStoragePort {

  public static final String PRESIGN_BASE = "https://integration.test/presigned/";

  @Override
  public String store(MultipartFile file, String s3Key) {
    return s3Key;
  }

  @Override
  public void delete(String s3Key) {
    // no-op
  }

  @Override
  public String presignedDownloadUrl(String s3Key) {
    return PRESIGN_BASE + java.net.URLEncoder.encode(s3Key, StandardCharsets.UTF_8);
  }
}
