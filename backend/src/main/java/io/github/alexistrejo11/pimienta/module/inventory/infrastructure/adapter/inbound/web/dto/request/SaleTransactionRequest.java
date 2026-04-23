package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SaleTransactionRequest(
    String externalReference,
    String notes,
    Long initiatedById,
    @NotEmpty @Valid List<SaleLineRequest> lines) {

  public record SaleLineRequest(
      long itemId, long locationId, int quantity, @NotNull java.math.BigDecimal unitCost) {}
}
