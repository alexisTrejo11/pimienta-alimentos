package io.github.alexistrejo11.pimienta.module.files.core.domain.exception;

import java.util.UUID;

public class FileAssetNotFoundException extends RuntimeException {

  public FileAssetNotFoundException(UUID id) {
    super("File asset not found: " + id);
  }
}
