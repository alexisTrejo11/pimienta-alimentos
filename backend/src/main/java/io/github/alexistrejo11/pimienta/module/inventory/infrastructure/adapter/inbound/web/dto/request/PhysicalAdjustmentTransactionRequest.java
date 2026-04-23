package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PhysicalAdjustmentTransactionRequest(
    @NotNull Long inventoryId,
    @Min(0) int newQuantity,
    @NotBlank String reason,
    Long performedById) {}
