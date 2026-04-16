package io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class StorageLocationNotFoundException extends ResourceNotFoundException {

  public StorageLocationNotFoundException(Long id) {
    super(
        ErrorCode.STORAGE_LOCATION_NOT_FOUND,
        "The requested storage location was not found.",
        Map.of("locationId", id),
        "Storage location not found: id=" + id);
  }
}
