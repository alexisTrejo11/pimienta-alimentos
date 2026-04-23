package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateInitialStockRequest(
    @NotNull Long itemId,
    @NotNull Long locationId,
    @Min(0) int initialQuantity) {}
