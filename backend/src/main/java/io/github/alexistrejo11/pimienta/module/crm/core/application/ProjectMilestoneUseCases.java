package io.github.alexistrejo11.pimienta.module.crm.core.application;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectMilestoneUseCases {

  Page<ProjectMilestone> listByProject(Long projectId, Pageable pageable);

  ProjectMilestone getById(Long projectId, Long milestoneId);

  ProjectMilestone create(Long projectId, CreateProjectMilestoneParams params);

  ProjectMilestone update(Long projectId, Long milestoneId, UpdateProjectMilestoneParams params);

  void delete(Long projectId, Long milestoneId);

  ProjectMilestone start(Long projectId, Long milestoneId);

  ProjectMilestone complete(Long projectId, Long milestoneId);

  ProjectMilestone markDelayed(Long projectId, Long milestoneId);

  ProjectMilestone cancel(Long projectId, Long milestoneId);

  ProjectMilestone markBilled(Long projectId, Long milestoneId);
}
