package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMilestoneJpaRepository extends JpaRepository<ProjectMilestoneJpaEntity, Long> {

  Optional<ProjectMilestoneJpaEntity> findByIdAndProjectIdAndDeletedAtIsNull(Long id, Long projectId);

  Page<ProjectMilestoneJpaEntity> findByProjectIdAndDeletedAtIsNullOrderBySortOrderAsc(
      Long projectId, Pageable pageable);

  long countByProjectIdAndDeletedAtIsNull(Long projectId);

  long countByProjectIdAndDeletedAtIsNullAndStatus(
      Long projectId, ProjectMilestone.MilestoneStatus status);
}
