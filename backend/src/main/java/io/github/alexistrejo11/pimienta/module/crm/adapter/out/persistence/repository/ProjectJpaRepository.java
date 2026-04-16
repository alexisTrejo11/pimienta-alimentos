package io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.repository;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import java.util.Optional;

import io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.model.ProjectJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectJpaRepository
    extends JpaRepository<ProjectJpaEntity, Long>, JpaSpecificationExecutor<ProjectJpaEntity> {

  Optional<ProjectJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  Optional<ProjectJpaEntity> findByProjectCodeAndDeletedAtIsNull(String projectCode);

  long countByDeletedAtIsNullAndStatus(Project.ProjectStatus status);
}
