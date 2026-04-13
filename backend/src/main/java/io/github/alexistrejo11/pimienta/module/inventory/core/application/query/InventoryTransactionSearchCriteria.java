package io.github.alexistrejo11.pimienta.module.inventory.core.application.query;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryTransaction.TransactionStatus;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryTransaction.TransactionType;
import java.time.LocalDateTime;

public record InventoryTransactionSearchCriteria(
    TransactionType type,
    TransactionStatus status,
    LocalDateTime fromDate,
    LocalDateTime toDate,
    Long initiatedById) {

  public static InventoryTransactionSearchCriteria empty() {
    return new InventoryTransactionSearchCriteria(null, null, null, null, null);
  }
}
