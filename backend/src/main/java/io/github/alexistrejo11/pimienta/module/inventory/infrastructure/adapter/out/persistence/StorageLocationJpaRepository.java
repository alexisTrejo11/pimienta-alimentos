package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StorageLocationJpaRepository
    extends JpaRepository<StorageLocationJpaEntity, Long>, JpaSpecificationExecutor<StorageLocationJpaEntity> {

  Optional<StorageLocationJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  List<StorageLocationJpaEntity> findByDeletedAtIsNullOrderByCodeAsc();

  List<StorageLocationJpaEntity> findByParentIdAndDeletedAtIsNullOrderByCodeAsc(Long parentId);
}
