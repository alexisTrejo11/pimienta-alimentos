package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.ReconstructProjectParams;

final class ProjectPersistenceMapper {

  private ProjectPersistenceMapper() {}

  static ProjectJpaEntity toJpa(Project domain) {
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

  static Project toDomain(ProjectJpaEntity e) {
    return Project.reconstruct(
        new ReconstructProjectParams(
            e.getId(),
            e.getClientId(),
            e.getOriginOpportunityId(),
            e.getProjectCode(),
            e.getProjectName(),
            e.getDescription(),
            e.getType(),
            e.getStatus(),
            e.getPriority(),
            e.getProjectManagerId(),
            e.getAssignedSalesmanId(),
            e.getPlannedStartDate(),
            e.getPlannedEndDate(),
            e.getActualStartDate(),
            e.getActualEndDate(),
            e.getOnHoldReason(),
            e.getContractedValue(),
            e.getEstimatedCost(),
            e.getActualCost(),
            e.getProgressPercent(),
            e.getCancellationReason(),
            e.getCreatedAt(),
            e.getUpdatedAt(),
            e.getDeletedAt(),
            e.getVersion()));
  }
}
