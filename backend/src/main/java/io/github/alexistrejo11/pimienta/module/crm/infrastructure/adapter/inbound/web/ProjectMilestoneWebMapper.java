package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.crm.core.application.CreateProjectMilestoneParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.UpdateProjectMilestoneParams;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.CreateProjectMilestoneRequest;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.ProjectMilestoneResponse;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.UpdateProjectMilestoneRequest;

public final class ProjectMilestoneWebMapper {

  private ProjectMilestoneWebMapper() {}

  public static CreateProjectMilestoneParams toCreateParams(CreateProjectMilestoneRequest request) {
    return new CreateProjectMilestoneParams(
        request.name(),
        request.description(),
        request.plannedDate(),
        request.billingAmount(),
        request.sortOrder());
  }

  public static UpdateProjectMilestoneParams toUpdateParams(UpdateProjectMilestoneRequest request) {
    return new UpdateProjectMilestoneParams(
        request.name(),
        request.description(),
        request.plannedDate(),
        request.billingAmount(),
        request.sortOrder());
  }

  public static ProjectMilestoneResponse toResponse(ProjectMilestone m) {
    return new ProjectMilestoneResponse(
        m.getId(),
        m.getProjectId(),
        m.getName(),
        m.getDescription(),
        m.getStatus(),
        m.getPlannedDate(),
        m.getActualDate(),
        m.getBillingAmount(),
        m.isBilled(),
        m.getSortOrder(),
        m.getCreatedAt(),
        m.getUpdatedAt());
  }
}
