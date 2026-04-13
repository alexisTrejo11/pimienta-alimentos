package io.github.alexistrejo11.pimienta.module.inventory.core.application.query;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation;

public record StorageLocationSearchCriteria(
    StorageLocation.LocationType type, StorageLocation.LocationStatus status) {

  public static StorageLocationSearchCriteria empty() {
    return new StorageLocationSearchCriteria(null, null);
  }
}
