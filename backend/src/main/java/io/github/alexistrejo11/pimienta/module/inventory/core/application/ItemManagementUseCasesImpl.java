package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.ItemSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Item;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.ItemRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.ItemNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.ItemSkuConflictException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemManagementUseCasesImpl implements ItemManagementUseCases {

  private final ItemRepository itemRepository;

  public ItemManagementUseCasesImpl(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public Page<Item> search(ItemSearchCriteria criteria, Pageable pageable) {
    ItemSearchCriteria effective = criteria != null ? criteria : ItemSearchCriteria.empty();
    return itemRepository.search(effective, pageable);
  }

  @Override
  public Item getById(Long id) {
    return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
  }

  @Override
  public Item getBySkuOrBarcode(String skuOrBarcode) {
    return itemRepository
        .findBySkuOrBarcode(skuOrBarcode)
        .orElseThrow(() -> new ItemNotFoundException(skuOrBarcode));
  }

  @Override
  public Item create(Item item) {
    if (itemRepository.existsBySkuIgnoreCaseExcludingId(item.getSku(), null)) {
      throw new ItemSkuConflictException(item.getSku());
    }
    return itemRepository.save(item);
  }

  @Override
  public Item update(Long id, Item merged) {
    Item existing = getById(id);
    if (itemRepository.existsBySkuIgnoreCaseExcludingId(merged.getSku(), id)) {
      throw new ItemSkuConflictException(merged.getSku());
    }
    existing.setSku(merged.getSku());
    existing.setName(merged.getName());
    existing.setDescription(merged.getDescription());
    existing.setCategory(merged.getCategory());
    existing.setUnit(merged.getUnit());
    existing.setBrand(merged.getBrand());
    existing.setBarcode(merged.getBarcode());
    existing.setCostPrice(merged.getCostPrice());
    existing.setSalePrice(merged.getSalePrice());
    existing.setReorderPoint(merged.getReorderPoint());
    existing.setReorderQuantity(merged.getReorderQuantity());
    existing.setStatus(merged.getStatus());
    return itemRepository.save(existing);
  }

  @Override
  public Item discontinue(Long id) {
    Item item = getById(id);
    item.discontinue();
    return itemRepository.save(item);
  }

  @Override
  public Item activate(Long id) {
    Item item = getById(id);
    item.activate();
    return itemRepository.save(item);
  }

  @Override
  public void delete(Long id) {
    Item item = getById(id);
    item.delete();
    itemRepository.save(item);
  }
}
