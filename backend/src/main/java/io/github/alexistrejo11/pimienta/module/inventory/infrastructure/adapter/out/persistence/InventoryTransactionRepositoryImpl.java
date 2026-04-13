package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryTransactionSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryTransaction;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.InventoryTransactionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryTransactionRepositoryImpl implements InventoryTransactionRepository {

  private final InventoryTransactionJpaRepository jpaRepository;
  private final InventoryMovementJpaRepository movementJpaRepository;
  private final TransactionPersistenceMapper transactionMapper;
  private final MovementPersistenceMapper movementMapper;

  public InventoryTransactionRepositoryImpl(
      InventoryTransactionJpaRepository jpaRepository,
      InventoryMovementJpaRepository movementJpaRepository,
      TransactionPersistenceMapper transactionMapper,
      MovementPersistenceMapper movementMapper) {
    this.jpaRepository = jpaRepository;
    this.movementJpaRepository = movementJpaRepository;
    this.transactionMapper = transactionMapper;
    this.movementMapper = movementMapper;
  }

  @Override
  public Optional<InventoryTransaction> findById(long id) {
    Optional<InventoryTransactionJpaEntity> row = jpaRepository.findByIdAndDeletedAtIsNull(id);
    if (row.isEmpty()) {
      return Optional.empty();
    }
    InventoryTransaction tx = transactionMapper.toDomain(row.get());
    List<InventoryMovementJpaEntity> movementRows =
        movementJpaRepository.findByTransactionIdOrderByIdAsc(id);
    List<InventoryMovement> movements =
        movementRows.stream().map(movementMapper::toDomain).toList();
    tx.replaceMovementsForLoad(movements);
    return Optional.of(tx);
  }

  @Override
  public Page<InventoryTransaction> search(InventoryTransactionSearchCriteria criteria, Pageable pageable) {
    InventoryTransactionSearchCriteria effective =
        criteria != null ? criteria : InventoryTransactionSearchCriteria.empty();
    Specification<InventoryTransactionJpaEntity> spec = TransactionSpecifications.fromCriteria(effective);
    return jpaRepository.findAll(spec, pageable).map(transactionMapper::toDomain);
  }

  @Override
  public InventoryTransaction save(InventoryTransaction transaction) {
    InventoryTransactionJpaEntity entity = transactionMapper.toJpa(transaction);
    InventoryTransactionJpaEntity saved = jpaRepository.save(entity);
    return transactionMapper.toDomain(saved);
  }
}
