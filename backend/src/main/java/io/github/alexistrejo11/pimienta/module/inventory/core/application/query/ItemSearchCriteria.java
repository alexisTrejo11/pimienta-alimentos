package io.github.alexistrejo11.pimienta.module.inventory.core.application.query;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Item;

public record ItemSearchCriteria(String name, String sku, Item.ItemCategory category, Item.ItemStatus status) {

  public static ItemSearchCriteria empty() {
    return new ItemSearchCriteria(null, null, null, null);
  }
}
