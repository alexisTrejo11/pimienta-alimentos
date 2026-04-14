package io.github.alexistrejo11.pimienta.module.crm.core.application;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.ProjectSearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import io.github.alexistrejo11.pimienta.module.crm.core.port.ProjectMilestoneRepository;
import io.github.alexistrejo11.pimienta.module.crm.core.port.ProjectRepository;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskRepository;
import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectUseCasesImpl implements ProjectUseCases {

  private final ProjectRepository projectRepository;
  private final ProjectMilestoneRepository milestoneRepository;
  private final TaskRepository taskRepository;

  public ProjectUseCasesImpl(
      ProjectRepository projectRepository,
      ProjectMilestoneRepository milestoneRepository,
      TaskRepository taskRepository) {
    this.projectRepository = projectRepository;
    this.milestoneRepository = milestoneRepository;
    this.taskRepository = taskRepository;
  }

  @Override
  public Page<Project> search(ProjectSearchCriteria criteria, Pageable pageable) {
    ProjectSearchCriteria effective = criteria != null ? criteria : ProjectSearchCriteria.empty();
    return projectRepository.search(effective, pageable);
  }

  @Override
  public Project getById(Long id) {
    return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
  }

  @Override
  public Project create(CreateProjectParams params) {
    String code = params.projectCode().trim();
    if (projectRepository.findByProjectCode(code).isPresent()) {
      throw new ConflictException(
          ErrorCode.CONFLICT,
          "A project with this code already exists.",
          Map.of("projectCode", code),
          "Duplicate projectCode: " + code);
    }
    Project project =
        Project.create(
            code,
            params.projectName(),
            params.description(),
            params.clientId(),
            params.originOpportunityId(),
            params.type(),
            params.priority(),
            params.projectManagerId(),
            params.assignedSalesmanId(),
            params.plannedStartDate(),
            params.plannedEndDate(),
            params.contractedValue(),
            params.estimatedCost());
    return projectRepository.save(project);
  }

  @Override
  public Project update(Long id, UpdateProjectParams params) {
    Project p = getById(id);
    if (params.projectName() != null) {
      p.setProjectName(params.projectName());
    }
    if (params.description() != null) {
      p.setDescription(params.description());
    }
    if (params.type() != null) {
      p.setType(params.type());
    }
    if (params.priority() != null) {
      p.setPriority(params.priority());
    }
    if (params.projectManagerId() != null) {
      p.setProjectManagerId(params.projectManagerId());
    }
    if (params.assignedSalesmanId() != null) {
      p.setAssignedSalesmanId(params.assignedSalesmanId());
    }
    if (params.plannedStartDate() != null) {
      p.setPlannedStartDate(params.plannedStartDate());
    }
    if (params.plannedEndDate() != null) {
      p.setPlannedEndDate(params.plannedEndDate());
    }
    if (params.contractedValue() != null) {
      p.setContractedValue(params.contractedValue());
    }
    if (params.estimatedCost() != null) {
      p.setEstimatedCost(params.estimatedCost());
    }
    if (params.progressPercent() != null) {
      p.updateProgress(params.progressPercent());
    }
    return projectRepository.save(p);
  }

  @Override
  public void delete(Long id) {
    Project p = getById(id);
    p.softDelete();
    projectRepository.save(p);
  }

  @Override
  public ProjectSummary getSummary(Long id) {
    Project p = getById(id);
    long milestoneCount = milestoneRepository.countByProjectId(id);
    long milestoneCompleted = milestoneRepository.countCompletedByProjectId(id);
    long taskCount = taskRepository.countByProjectId(id);
    return new ProjectSummary(p, milestoneCount, milestoneCompleted, taskCount, p.isOverdue());
  }

  @Override
  public Project activate(Long id) {
    Project p = getById(id);
    p.activate();
    return projectRepository.save(p);
  }

  @Override
  public Project putOnHold(Long id, String reason) {
    Project p = getById(id);
    p.putOnHold(reason);
    return projectRepository.save(p);
  }

  @Override
  public Project complete(Long id) {
    Project p = getById(id);
    p.complete();
    return projectRepository.save(p);
  }

  @Override
  public Project cancel(Long id, String reason) {
    Project p = getById(id);
    p.cancel(reason);
    return projectRepository.save(p);
  }

  @Override
  public Project archive(Long id) {
    Project p = getById(id);
    p.archive();
    return projectRepository.save(p);
  }
}
