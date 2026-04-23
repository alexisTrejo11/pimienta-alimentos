package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ScrapTransactionRequest(
    String externalReference,
    String notes,
    Long initiatedById,
    @NotEmpty @Valid List<ScrapLineRequest> lines) {

  public record ScrapLineRequest(
      long itemId, long locationId, int quantity, @NotNull java.math.BigDecimal unitCost) {}
}
