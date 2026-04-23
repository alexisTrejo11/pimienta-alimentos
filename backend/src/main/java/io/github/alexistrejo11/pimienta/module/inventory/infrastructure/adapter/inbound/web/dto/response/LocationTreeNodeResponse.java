package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationStatus;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationType;
import java.util.List;

public record LocationTreeNodeResponse(
    Long id,
    String code,
    String name,
    LocationType type,
    LocationStatus status,
    Long parentId,
    int maxCapacity,
    int occupiedCapacity,
    List<LocationTreeNodeResponse> children) {}
