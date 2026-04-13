package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class InventoryMovementNotFoundException extends ResourceNotFoundException {

  public InventoryMovementNotFoundException(Long id) {
    super(
        ErrorCode.INVENTORY_MOVEMENT_NOT_FOUND,
        "The requested inventory movement was not found.",
        Map.of("movementId", id),
        "Movement not found: id=" + id);
  }
}
