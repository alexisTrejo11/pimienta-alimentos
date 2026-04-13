package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationStatus;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationType;
import java.util.List;

/** Nodo del árbol de ubicaciones (read model para GET /locations/tree). */
public record LocationTreeNode(
    Long id,
    String code,
    String name,
    LocationType type,
    LocationStatus status,
    Long parentId,
    int maxCapacity,
    int occupiedCapacity,
    List<LocationTreeNode> children) {}
