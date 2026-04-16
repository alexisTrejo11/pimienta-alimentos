package io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.mapper;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.ReconstructProjectParams;
import io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.model.ProjectJpaEntity;

public class ProjectPersistenceMapper {

  private ProjectPersistenceMapper() {
  }

  public static ProjectJpaEntity toJpa(Project domain) {
    ProjectJpaEntity e = new ProjectJpaEntity();
    if (domain.getId() != null && domain.getId() > 0) {
      e.setId(domain.getId());
    }
    e.setClientId(domain.getClientId());
    e.setOriginOpportunityId(domain.getOriginOpportunityId());
    e.setProjectCode(domain.getProjectCode());
    e.setProjectName(domain.getProjectName());
    e.setDescription(domain.getDescription());
    e.setType(domain.getType());
    e.setStatus(domain.getStatus());
    e.setPriority(domain.getPriority());
    e.setProjectManagerId(domain.getProjectManagerId());
    e.setAssignedSalesmanId(domain.getAssignedSalesmanId());
    e.setPlannedStartDate(domain.getPlannedStartDate());
    e.setPlannedEndDate(domain.getPlannedEndDate());
    e.setActualStartDate(domain.getActualStartDate());
    e.setActualEndDate(domain.getActualEndDate());
    e.setOnHoldReason(domain.getOnHoldReason());
    e.setContractedValue(domain.getContractedValue());
    e.setEstimatedCost(domain.getEstimatedCost());
    e.setActualCost(domain.getActualCost());
    e.setProgressPercent(domain.getProgressPercent());
    e.setCancellationReason(domain.getCancellationReason());
    e.setCreatedAt(domain.getCreatedAt());
    e.setUpdatedAt(domain.getUpdatedAt());
    e.setDeletedAt(domain.getDeletedAt());
    e.setVersion(domain.getVersion() != null ? domain.getVersion() : 0L);
    return e;
  }

  public static Project toDomain(ProjectJpaEntity e) {
    ReconstructProjectParams params = ReconstructProjectParams.builder()
        .id(e.getId())
        .clientId(e.getClientId())
        .originOpportunityId(e.getOriginOpportunityId())
        .projectCode(e.getProjectCode())
        .projectName(e.getProjectName())
        .description(e.getDescription())
        .type(e.getType())
        .status(e.getStatus())
        .priority(e.getPriority())
        .projectManagerId(e.getProjectManagerId())
        .assignedSalesmanId(e.getAssignedSalesmanId())
        .plannedStartDate(e.getPlannedStartDate())
        .plannedEndDate(e.getPlannedEndDate())
        .actualStartDate(e.getActualStartDate())
        .actualEndDate(e.getActualEndDate())
        .onHoldReason(e.getOnHoldReason())
        .contractedValue(e.getContractedValue())
        .estimatedCost(e.getEstimatedCost())
        .actualCost(e.getActualCost())
        .progressPercent(e.getProgressPercent())
        .cancellationReason(e.getCancellationReason())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt())
        .deletedAt(e.getDeletedAt())
        .version(e.getVersion())
        .build();
    return Project.reconstruct(params);
  }
}
