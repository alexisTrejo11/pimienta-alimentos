package io.github.alexistrejo11.pimienta.module.inventory.core.application.usecase;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryMovementSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.InventoryMovementNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.input.InventoryMovementQueryUseCases;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.output.InventoryMovementRepository;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryMovementQueryUseCasesImpl implements InventoryMovementQueryUseCases {

  private static final Logger log = LoggerFactory.getLogger(InventoryMovementQueryUseCasesImpl.class);

  private final InventoryMovementRepository movementRepository;

  public InventoryMovementQueryUseCasesImpl(InventoryMovementRepository movementRepository) {
    this.movementRepository = movementRepository;
  }

  @Override
  public Page<InventoryMovement> search(InventoryMovementSearchCriteria criteria, Pageable pageable) {
    InventoryMovementSearchCriteria effective = criteria != null ? criteria : InventoryMovementSearchCriteria.empty();

    log.debug(
        "search inventory movements query start page={} size={} type={} direction={} itemId={} locationId={}",
        pageable != null ? pageable.getPageNumber() : null,
        pageable != null ? pageable.getPageSize() : null,
        effective.type(),
        effective.direction(),
        effective.itemId(),
        effective.locationId());

    Page<InventoryMovement> page = movementRepository.search(effective, pageable);

    log.debug(
        "search inventory movements query complete totalElements={} numberOfElements={}",
        page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  public InventoryMovement getById(Long id) {
    log.debug("get inventory movement by id query start movementId={}", id);

    InventoryMovement m = movementRepository.findById(id).orElseThrow(() -> new InventoryMovementNotFoundException(id));

    log.debug("get inventory movement by id query complete movementId={}", m.getId());
    return m;
  }

  @Override
  public List<InventoryMovement> findByItemId(Long itemId) {
    log.debug("find inventory movements by item query start itemId={}", itemId);

    List<InventoryMovement> list = movementRepository.findByItemId(itemId);

    log.debug("find inventory movements by item query complete itemId={} size={}", itemId, list.size());
    return list;
  }

  @Override
  public List<InventoryMovement> findByLocationId(Long locationId) {
    log.debug("find inventory movements by location query start locationId={}", locationId);

    List<InventoryMovement> list = movementRepository.findByLocationId(locationId);

    log.debug(
        "find inventory movements by location query complete locationId={} size={}",
        locationId,
        list.size());
    return list;
  }

  @Override
  public List<InventoryMovement> findByReferenceNumber(String referenceNumber) {
    int refLen = referenceNumber != null ? referenceNumber.length() : 0;
    log.debug("find inventory movements by reference query start referenceLen={}", refLen);

    List<InventoryMovement> list = movementRepository.findByReferenceNumber(referenceNumber);

    log.debug("find inventory movements by reference query complete size={}", list.size());
    return list;
  }
}
