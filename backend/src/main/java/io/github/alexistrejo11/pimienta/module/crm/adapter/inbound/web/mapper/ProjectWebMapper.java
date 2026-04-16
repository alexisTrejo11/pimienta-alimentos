package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.crm.core.application.command.CreateProjectParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.summary.ProjectSummary;
import io.github.alexistrejo11.pimienta.module.crm.core.application.command.UpdateProjectParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.query.ProjectSearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.CreateProjectRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.ProjectResponse;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.ProjectSearchRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.ProjectSummaryResponse;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.UpdateProjectRequest;

public final class ProjectWebMapper {

  private ProjectWebMapper() {}

  public static ProjectSearchCriteria toCriteria(ProjectSearchRequest request) {
    return new ProjectSearchCriteria(
        request.getStatus(),
        request.getClientId(),
        request.getProjectManagerId(),
        request.getOriginOpportunityId());
  }

  public static CreateProjectParams toCreateParams(CreateProjectRequest request) {
    return new CreateProjectParams(
        request.projectCode(),
        request.projectName(),
        request.description(),
        request.clientId(),
        request.originOpportunityId(),
        request.type(),
        request.priority(),
        request.projectManagerId(),
        request.assignedSalesmanId(),
        request.plannedStartDate(),
        request.plannedEndDate(),
        request.contractedValue(),
        request.estimatedCost());
  }

  public static UpdateProjectParams toUpdateParams(UpdateProjectRequest request) {
    return new UpdateProjectParams(
        request.projectName(),
        request.description(),
        request.type(),
        request.priority(),
        request.projectManagerId(),
        request.assignedSalesmanId(),
        request.plannedStartDate(),
        request.plannedEndDate(),
        request.contractedValue(),
        request.estimatedCost(),
        request.progressPercent());
  }

  public static ProjectResponse toResponse(Project p) {
    return new ProjectResponse(
        p.getId(),
        p.getClientId(),
        p.getOriginOpportunityId(),
        p.getProjectCode(),
        p.getProjectName(),
        p.getDescription(),
        p.getType(),
        p.getStatus(),
        p.getPriority(),
        p.getProjectManagerId(),
        p.getAssignedSalesmanId(),
        p.getPlannedStartDate(),
        p.getPlannedEndDate(),
        p.getActualStartDate(),
        p.getActualEndDate(),
        p.getOnHoldReason(),
        p.getContractedValue(),
        p.getEstimatedCost(),
        p.getActualCost(),
        p.getProgressPercent(),
        p.getCancellationReason(),
        p.getCreatedAt(),
        p.getUpdatedAt());
  }

  public static ProjectSummaryResponse toSummaryResponse(ProjectSummary s) {
    return new ProjectSummaryResponse(
        toResponse(s.project()),
        s.milestoneCount(),
        s.milestoneCompletedCount(),
        s.taskCount(),
        s.overdue());
  }
}
