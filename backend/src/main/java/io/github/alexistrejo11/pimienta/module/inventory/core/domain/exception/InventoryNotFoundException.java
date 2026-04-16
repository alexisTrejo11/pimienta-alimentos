package io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class InventoryNotFoundException extends ResourceNotFoundException {

  public InventoryNotFoundException(Long id) {
    super(
        ErrorCode.INVENTORY_NOT_FOUND,
        "The requested inventory record was not found.",
        Map.of("inventoryId", id),
        "Inventory not found: id=" + id);
  }
}
