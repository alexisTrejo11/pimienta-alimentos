package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.util.Map;

public class ItemSkuConflictException extends ConflictException {

  public ItemSkuConflictException(String sku) {
    super(
        ErrorCode.ITEM_SKU_ALREADY_EXISTS,
        "An item with this SKU already exists.",
        Map.of("sku", sku),
        "Duplicate SKU: " + sku);
  }
}
