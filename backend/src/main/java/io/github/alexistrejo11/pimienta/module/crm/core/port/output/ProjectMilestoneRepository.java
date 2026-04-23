package io.github.alexistrejo11.pimienta.module.crm.core.port.output;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectMilestoneRepository {

  Optional<ProjectMilestone> findByIdAndProjectId(long milestoneId, long projectId);

  Page<ProjectMilestone> findByProjectId(long projectId, Pageable pageable);

  /** Todas las filas no borradas (export global). */
  Page<ProjectMilestone> findAll(Pageable pageable);

  long countByProjectId(long projectId);

  long countCompletedByProjectId(long projectId);

  ProjectMilestone save(ProjectMilestone milestone);
}
