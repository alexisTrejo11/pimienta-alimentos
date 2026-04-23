package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.repository.jpa;

import java.util.List;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.entity.InventoryMovementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryMovementJpaRepository
    extends JpaRepository<InventoryMovementJpaEntity, Long>, JpaSpecificationExecutor<InventoryMovementJpaEntity> {

  List<InventoryMovementJpaEntity> findByItemIdOrderByCreatedAtDesc(Long itemId);

  List<InventoryMovementJpaEntity> findByTransactionIdOrderByIdAsc(Long transactionId);

  List<InventoryMovementJpaEntity> findByReferenceNumberOrderByCreatedAtDesc(String referenceNumber);

  @Query("""
      select m from InventoryMovementJpaEntity m
      where m.sourceLocationId = :id or m.destinationLocationId = :id
      order by m.createdAt desc
      """)
  List<InventoryMovementJpaEntity> findByAnyLocation(@Param("id") Long locationId);
}
