package io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class ItemNotFoundException extends ResourceNotFoundException {

  public ItemNotFoundException(Long id) {
    super(
        ErrorCode.ITEM_NOT_FOUND,
        "The requested item was not found.",
        Map.of("itemId", id),
        "Item not found: id=" + id);
  }

  public ItemNotFoundException(String skuOrBarcode) {
    super(
        ErrorCode.ITEM_NOT_FOUND,
        "The requested item was not found.",
        Map.of("skuOrBarcode", skuOrBarcode),
        "Item not found: skuOrBarcode=" + skuOrBarcode);
  }
}
