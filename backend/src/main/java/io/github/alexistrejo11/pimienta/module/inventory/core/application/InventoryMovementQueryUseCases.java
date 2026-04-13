package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryMovementSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryMovementQueryUseCases {

  Page<InventoryMovement> search(InventoryMovementSearchCriteria criteria, Pageable pageable);

  InventoryMovement getById(Long id);

  List<InventoryMovement> findByItemId(Long itemId);

  List<InventoryMovement> findByLocationId(Long locationId);

  List<InventoryMovement> findByReferenceNumber(String referenceNumber);
}
