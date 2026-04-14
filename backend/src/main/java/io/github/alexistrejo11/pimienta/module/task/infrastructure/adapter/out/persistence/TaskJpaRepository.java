package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskJpaRepository
    extends JpaRepository<TaskJpaEntity, Long>, JpaSpecificationExecutor<TaskJpaEntity> {

  Optional<TaskJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  long countByOpportunityIdAndDeletedAtIsNull(Long opportunityId);

  long countByOpportunityIdAndDeletedAtIsNullAndStatusNotIn(
      Long opportunityId, Collection<Task.Status> excludedStatuses);

  long countByProjectIdAndDeletedAtIsNull(Long projectId);

  long countByProjectIdAndDeletedAtIsNullAndStatusNotIn(
      Long projectId, Collection<Task.Status> excludedStatuses);
}
