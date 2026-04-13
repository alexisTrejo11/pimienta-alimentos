package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.util.Map;

public class InventoryTransactionInvalidStateException extends ConflictException {

  public InventoryTransactionInvalidStateException(Long transactionId, String detail) {
    super(
        ErrorCode.INVENTORY_TRANSACTION_INVALID_STATE,
        "This operation is not allowed for the current transaction state.",
        Map.of("transactionId", transactionId),
        detail);
  }
}
