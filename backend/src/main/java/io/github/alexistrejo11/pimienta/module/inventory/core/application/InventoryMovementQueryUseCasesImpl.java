package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryMovementSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.InventoryMovementRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryMovementQueryUseCasesImpl implements InventoryMovementQueryUseCases {

  private final InventoryMovementRepository movementRepository;

  public InventoryMovementQueryUseCasesImpl(InventoryMovementRepository movementRepository) {
    this.movementRepository = movementRepository;
  }

  @Override
  public Page<InventoryMovement> search(InventoryMovementSearchCriteria criteria, Pageable pageable) {
    InventoryMovementSearchCriteria effective =
        criteria != null ? criteria : InventoryMovementSearchCriteria.empty();
    return movementRepository.search(effective, pageable);
  }

  @Override
  public InventoryMovement getById(Long id) {
    return movementRepository.findById(id).orElseThrow(() -> new InventoryMovementNotFoundException(id));
  }

  @Override
  public List<InventoryMovement> findByItemId(Long itemId) {
    return movementRepository.findByItemId(itemId);
  }

  @Override
  public List<InventoryMovement> findByLocationId(Long locationId) {
    return movementRepository.findByLocationId(locationId);
  }

  @Override
  public List<InventoryMovement> findByReferenceNumber(String referenceNumber) {
    return movementRepository.findByReferenceNumber(referenceNumber);
  }
}
