package io.github.alexistrejo11.pimienta.module.inventory.core.port;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryMovementSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryMovementRepository {

  Optional<InventoryMovement> findById(long id);

  Page<InventoryMovement> search(InventoryMovementSearchCriteria criteria, Pageable pageable);

  List<InventoryMovement> findByItemId(long itemId);

  List<InventoryMovement> findByLocationId(long locationId);

  List<InventoryMovement> findByReferenceNumber(String referenceNumber);

  List<InventoryMovement> findByTransactionId(long transactionId);

  InventoryMovement save(InventoryMovement movement);
}
