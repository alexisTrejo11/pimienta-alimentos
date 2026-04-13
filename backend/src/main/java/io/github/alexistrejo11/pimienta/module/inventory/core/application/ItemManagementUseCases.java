package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.ItemSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemManagementUseCases {

  Page<Item> search(ItemSearchCriteria criteria, Pageable pageable);

  Item getById(Long id);

  Item getBySkuOrBarcode(String skuOrBarcode);

  Item create(Item item);

  Item update(Long id, Item updatedFields);

  Item discontinue(Long id);

  Item activate(Long id);

  void delete(Long id);
}
