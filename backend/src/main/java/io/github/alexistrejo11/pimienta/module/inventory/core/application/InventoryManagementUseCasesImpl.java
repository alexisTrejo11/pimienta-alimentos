package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventorySearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Inventory;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Item;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationStatus;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.InventoryRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.ItemRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.StorageLocationRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.InventoryAlreadyExistsException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.InventoryNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.ItemNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.StorageLocationNotFoundException;
import io.github.alexistrejo11.pimienta.shared.exception.BusinessValidationException;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryManagementUseCasesImpl implements InventoryManagementUseCases {

  private final InventoryRepository inventoryRepository;
  private final ItemRepository itemRepository;
  private final StorageLocationRepository storageLocationRepository;

  public InventoryManagementUseCasesImpl(
      InventoryRepository inventoryRepository,
      ItemRepository itemRepository,
      StorageLocationRepository storageLocationRepository) {
    this.inventoryRepository = inventoryRepository;
    this.itemRepository = itemRepository;
    this.storageLocationRepository = storageLocationRepository;
  }

  @Override
  public Page<Inventory> search(InventorySearchCriteria criteria, Pageable pageable) {
    InventorySearchCriteria effective =
        criteria != null ? criteria : InventorySearchCriteria.empty();
    return inventoryRepository.search(effective, pageable);
  }

  @Override
  public Inventory getById(Long id) {
    return inventoryRepository.findById(id).orElseThrow(() -> new InventoryNotFoundException(id));
  }

  @Override
  public List<Inventory> findByItemId(Long itemId) {
    return inventoryRepository.findByItemId(itemId);
  }

  @Override
  public List<Inventory> findByLocationId(Long locationId) {
    return inventoryRepository.findByLocationId(locationId);
  }

  @Override
  public Page<Inventory> findLowStock(Pageable pageable) {
    return inventoryRepository.findLowStock(pageable);
  }

  @Override
  public Page<Inventory> findOutOfStock(Pageable pageable) {
    return inventoryRepository.findOutOfStock(pageable);
  }

  @Override
  public Inventory createInitialStock(long itemId, long locationId, int initialQuantity) {
    if (inventoryRepository.findByItemIdAndLocationId(itemId, locationId).isPresent()) {
      throw new InventoryAlreadyExistsException(itemId, locationId);
    }
    Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
    StorageLocation location =
        storageLocationRepository
            .findById(locationId)
            .orElseThrow(() -> new StorageLocationNotFoundException(locationId));
    if (location.getStatus() == LocationStatus.BLOCKED) {
      throw new BusinessValidationException(
          "Cannot create stock in a blocked location.",
          Map.of("locationId", locationId),
          "location blocked");
    }
    Inventory inv = Inventory.create(item, location, initialQuantity);
    return inventoryRepository.save(inv);
  }
}
