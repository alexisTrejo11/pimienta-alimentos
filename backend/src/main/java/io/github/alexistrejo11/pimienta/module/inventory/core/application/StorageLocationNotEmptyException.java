package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.util.Map;

public class StorageLocationNotEmptyException extends ConflictException {

  public StorageLocationNotEmptyException(Long locationId) {
    super(
        ErrorCode.STORAGE_LOCATION_NOT_EMPTY,
        "The location still has stock or occupied capacity; it cannot be deleted.",
        Map.of("locationId", locationId),
        "Storage location not empty: id=" + locationId);
  }
}
