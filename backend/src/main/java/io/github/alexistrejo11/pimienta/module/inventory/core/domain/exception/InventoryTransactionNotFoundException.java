package io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class InventoryTransactionNotFoundException extends ResourceNotFoundException {

  public InventoryTransactionNotFoundException(Long id) {
    super(
        ErrorCode.INVENTORY_TRANSACTION_NOT_FOUND,
        "The requested inventory transaction was not found.",
        Map.of("transactionId", id),
        "Transaction not found: id=" + id);
  }
}
