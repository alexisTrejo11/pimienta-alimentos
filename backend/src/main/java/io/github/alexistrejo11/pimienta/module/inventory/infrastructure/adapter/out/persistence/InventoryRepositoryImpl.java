package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventorySearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Inventory;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.InventoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

  private final InventoryJpaRepository jpaRepository;
  private final InventoryPersistenceMapper mapper;

  public InventoryRepositoryImpl(InventoryJpaRepository jpaRepository, InventoryPersistenceMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<Inventory> findById(long id) {
    return jpaRepository.findByIdAndDeletedAtIsNull(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Inventory> findByItemIdAndLocationId(long itemId, long locationId) {
    return jpaRepository
        .findByItemIdAndLocationIdAndDeletedAtIsNull(itemId, locationId)
        .map(mapper::toDomain);
  }

  @Override
  public Page<Inventory> search(InventorySearchCriteria criteria, Pageable pageable) {
    Specification<InventoryJpaEntity> spec = InventorySpecifications.fromCriteria(criteria);
    return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
  }

  @Override
  public List<Inventory> findByItemId(long itemId) {
    return jpaRepository.findByItemIdAndDeletedAtIsNullOrderByIdAsc(itemId).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<Inventory> findByLocationId(long locationId) {
    return jpaRepository.findByLocationIdAndDeletedAtIsNullOrderByIdAsc(locationId).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public Page<Inventory> findLowStock(Pageable pageable) {
    return jpaRepository.findLowStock(pageable).map(mapper::toDomain);
  }

  @Override
  public Page<Inventory> findOutOfStock(Pageable pageable) {
    return jpaRepository.findByDeletedAtIsNullAndAvailableQuantity(0, pageable).map(mapper::toDomain);
  }

  @Override
  public long countByLocationId(long locationId) {
    return jpaRepository.countByLocationIdAndDeletedAtIsNull(locationId);
  }

  @Override
  public Inventory save(Inventory inventory) {
    InventoryJpaEntity entity = mapper.toJpa(inventory);
    InventoryJpaEntity saved = jpaRepository.save(entity);
    return mapper.toDomain(saved);
  }
}
