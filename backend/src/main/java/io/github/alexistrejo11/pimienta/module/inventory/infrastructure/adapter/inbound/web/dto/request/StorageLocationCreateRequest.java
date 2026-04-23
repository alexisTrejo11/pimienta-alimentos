package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StorageLocationCreateRequest(
    @NotBlank String code,
    @NotBlank String name,
    String description,
    @NotNull LocationType type,
    Long parentId,
    @Min(0) int maxCapacity) {}
