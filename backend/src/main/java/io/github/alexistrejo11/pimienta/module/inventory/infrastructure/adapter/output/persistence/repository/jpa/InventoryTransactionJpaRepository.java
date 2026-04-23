package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.repository.jpa;

import java.util.Optional;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.entity.InventoryTransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventoryTransactionJpaRepository
    extends JpaRepository<InventoryTransactionJpaEntity, Long>,
    JpaSpecificationExecutor<InventoryTransactionJpaEntity> {

  Optional<InventoryTransactionJpaEntity> findByIdAndDeletedAtIsNull(Long id);
}
