package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.repository;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryMovementSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.output.InventoryMovementRepository;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.entity.InventoryMovementJpaEntity;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.mapper.MovementPersistenceMapper;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.repository.jpa.InventoryMovementJpaRepository;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.specification.MovementSpecifications;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryMovementRepositoryImpl implements InventoryMovementRepository {

  private final InventoryMovementJpaRepository jpaRepository;
  private final MovementPersistenceMapper mapper;

  public InventoryMovementRepositoryImpl(
      InventoryMovementJpaRepository jpaRepository, MovementPersistenceMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<InventoryMovement> findById(long id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Page<InventoryMovement> search(InventoryMovementSearchCriteria criteria, Pageable pageable) {
    InventoryMovementSearchCriteria effective = criteria != null ? criteria : InventoryMovementSearchCriteria.empty();
    Specification<InventoryMovementJpaEntity> spec = MovementSpecifications.fromCriteria(effective);
    return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
  }

  @Override
  public List<InventoryMovement> findByItemId(long itemId) {
    return jpaRepository.findByItemIdOrderByCreatedAtDesc(itemId).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<InventoryMovement> findByLocationId(long locationId) {
    return jpaRepository.findByAnyLocation(locationId).stream().map(mapper::toDomain).toList();
  }

  @Override
  public List<InventoryMovement> findByReferenceNumber(String referenceNumber) {
    return jpaRepository.findByReferenceNumberOrderByCreatedAtDesc(referenceNumber).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<InventoryMovement> findByTransactionId(long transactionId) {
    return jpaRepository.findByTransactionIdOrderByIdAsc(transactionId).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public InventoryMovement save(InventoryMovement movement) {
    InventoryMovementJpaEntity entity = mapper.toJpa(movement);
    InventoryMovementJpaEntity saved = jpaRepository.save(entity);
    return mapper.toDomain(saved);
  }
}
