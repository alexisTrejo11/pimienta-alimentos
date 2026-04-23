package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.StorageLocationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationStatus;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationType;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;

public class StorageLocationSearchRequest extends PageableRequest {

  private LocationType type;
  private LocationStatus status;

  public LocationType getType() {
    return type;
  }

  public void setType(LocationType type) {
    this.type = type;
  }

  public LocationStatus getStatus() {
    return status;
  }

  public void setStatus(LocationStatus status) {
    this.status = status;
  }

  public StorageLocationSearchCriteria toCriteria() {
    return new StorageLocationSearchCriteria(type, status);
  }
}
