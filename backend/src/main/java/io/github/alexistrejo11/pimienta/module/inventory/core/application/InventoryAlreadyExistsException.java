package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.util.Map;

public class InventoryAlreadyExistsException extends ConflictException {

  public InventoryAlreadyExistsException(long itemId, long locationId) {
    super(
        ErrorCode.INVENTORY_ALREADY_EXISTS,
        "Stock for this item at this location already exists.",
        Map.of("itemId", itemId, "locationId", locationId),
        "Duplicate inventory: itemId=" + itemId + " locationId=" + locationId);
  }
}
