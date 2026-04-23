package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.entity.InventoryJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface InventoryJpaRepository
    extends JpaRepository<InventoryJpaEntity, Long>, JpaSpecificationExecutor<InventoryJpaEntity> {

  Optional<InventoryJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  Optional<InventoryJpaEntity> findByItemIdAndLocationIdAndDeletedAtIsNull(Long itemId, Long locationId);

  long countByLocationIdAndDeletedAtIsNull(Long locationId);

  List<InventoryJpaEntity> findByItemIdAndDeletedAtIsNullOrderByIdAsc(Long itemId);

  List<InventoryJpaEntity> findByLocationIdAndDeletedAtIsNullOrderByIdAsc(Long locationId);

  @Query("""
      select i from InventoryJpaEntity i join ItemJpaEntity it on i.itemId = it.id
      where i.deletedAt is null and it.deletedAt is null
        and i.availableQuantity > 0 and i.availableQuantity <= it.reorderPoint
      """)
  Page<InventoryJpaEntity> findLowStock(Pageable pageable);

  Page<InventoryJpaEntity> findByDeletedAtIsNullAndAvailableQuantity(int availableQuantity, Pageable pageable);
}
