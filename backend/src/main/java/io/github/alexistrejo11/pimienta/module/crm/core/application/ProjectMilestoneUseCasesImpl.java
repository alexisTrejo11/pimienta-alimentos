package io.github.alexistrejo11.pimienta.module.crm.core.application;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import io.github.alexistrejo11.pimienta.module.crm.core.port.ProjectMilestoneRepository;
import io.github.alexistrejo11.pimienta.module.crm.core.port.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectMilestoneUseCasesImpl implements ProjectMilestoneUseCases {

  private final ProjectMilestoneRepository milestoneRepository;
  private final ProjectRepository projectRepository;

  public ProjectMilestoneUseCasesImpl(
      ProjectMilestoneRepository milestoneRepository, ProjectRepository projectRepository) {
    this.milestoneRepository = milestoneRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public Page<ProjectMilestone> listByProject(Long projectId, Pageable pageable) {
    ensureProjectExists(projectId);
    return milestoneRepository.findByProjectId(projectId, pageable);
  }

  @Override
  public ProjectMilestone getById(Long projectId, Long milestoneId) {
    ensureProjectExists(projectId);
    return milestoneRepository
        .findByIdAndProjectId(milestoneId, projectId)
        .orElseThrow(() -> new ProjectMilestoneNotFoundException(projectId, milestoneId));
  }

  @Override
  public ProjectMilestone create(Long projectId, CreateProjectMilestoneParams params) {
    ensureProjectExists(projectId);
    ProjectMilestone m =
        ProjectMilestone.create(
            projectId,
            params.name(),
            params.description(),
            params.plannedDate(),
            params.billingAmount(),
            params.sortOrder());
    return milestoneRepository.save(m);
  }

  @Override
  public ProjectMilestone update(Long projectId, Long milestoneId, UpdateProjectMilestoneParams params) {
    ProjectMilestone m = getById(projectId, milestoneId);
    if (params.name() != null) {
      m.setName(params.name());
    }
    if (params.description() != null) {
      m.setDescription(params.description());
    }
    if (params.plannedDate() != null) {
      m.setPlannedDate(params.plannedDate());
    }
    if (params.billingAmount() != null) {
      m.setBillingAmount(params.billingAmount());
    }
    if (params.sortOrder() != null) {
      m.setSortOrder(params.sortOrder());
    }
    return milestoneRepository.save(m);
  }

  @Override
  public void delete(Long projectId, Long milestoneId) {
    ProjectMilestone m = getById(projectId, milestoneId);
    m.softDelete();
    milestoneRepository.save(m);
  }

  @Override
  public ProjectMilestone start(Long projectId, Long milestoneId) {
    ProjectMilestone m = getById(projectId, milestoneId);
    m.start();
    return milestoneRepository.save(m);
  }

  @Override
  public ProjectMilestone complete(Long projectId, Long milestoneId) {
    ProjectMilestone m = getById(projectId, milestoneId);
    m.complete();
    return milestoneRepository.save(m);
  }

  @Override
  public ProjectMilestone markDelayed(Long projectId, Long milestoneId) {
    ProjectMilestone m = getById(projectId, milestoneId);
    m.markDelayed();
    return milestoneRepository.save(m);
  }

  @Override
  public ProjectMilestone cancel(Long projectId, Long milestoneId) {
    ProjectMilestone m = getById(projectId, milestoneId);
    m.cancel();
    return milestoneRepository.save(m);
  }

  @Override
  public ProjectMilestone markBilled(Long projectId, Long milestoneId) {
    ProjectMilestone m = getById(projectId, milestoneId);
    m.markBilled();
    return milestoneRepository.save(m);
  }

  private void ensureProjectExists(Long projectId) {
    projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
  }
}
