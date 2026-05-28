package io.github.alexistrejo11.pimienta.shared.storage;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

/**
 * Generates pre-signed S3 GET URLs. Shared by the employee module and the general file module so
 * the signing logic (bucket name, TTL) is defined in one place.
 */
@Service
public class S3PresignService {

  private static final Logger log = LoggerFactory.getLogger(S3PresignService.class);

  private final S3Presigner s3Presigner;
  private final String bucketName;

  public S3PresignService(
      S3Presigner s3Presigner,
      @Value("${aws.s3.bucket}") String bucketName) {
    this.s3Presigner = s3Presigner;
    this.bucketName = bucketName;
  }

  /** Generates a URL valid for {@code ttl}. */
  public String presign(String s3Key, Duration ttl) {
    GetObjectRequest getRequest =
        GetObjectRequest.builder().bucket(bucketName).key(s3Key).build();
    GetObjectPresignRequest presignRequest =
        GetObjectPresignRequest.builder()
            .signatureDuration(ttl)
            .getObjectRequest(getRequest)
            .build();
    String url = s3Presigner.presignGetObject(presignRequest).url().toString();
    log.debug("presigned key={} ttl={}", s3Key, ttl);
    return url;
  }

  /** Convenience: 24-hour pre-signed URL (default for most downloads). */
  public String presign24h(String s3Key) {
    return presign(s3Key, Duration.ofHours(24));
  }
}
