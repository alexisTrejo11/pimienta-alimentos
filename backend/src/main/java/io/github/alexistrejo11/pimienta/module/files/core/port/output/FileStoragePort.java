package io.github.alexistrejo11.pimienta.module.files.core.port.output;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {

  /** Stores the file at {@code s3Key} and returns the key used (same value passed in). */
  String store(MultipartFile file, String s3Key);

  /** Hard-deletes the object. Silently no-ops if the key does not exist. */
  void delete(String s3Key);

  /** Returns a 24-hour pre-signed download URL. */
  String presignedDownloadUrl(String s3Key);
}
