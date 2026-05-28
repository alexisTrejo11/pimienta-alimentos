package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.out.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileAssetJpaRepository
    extends JpaRepository<FileAssetJpaEntity, UUID>,
        JpaSpecificationExecutor<FileAssetJpaEntity> {}
