package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.ReconstructProjectMilestoneParams;

final class ProjectMilestonePersistenceMapper {

  private ProjectMilestonePersistenceMapper() {}

  static ProjectMilestoneJpaEntity toJpa(ProjectMilestone domain) {
    ProjectMilestoneJpaEntity e = new ProjectMilestoneJpaEntity();
    if (domain.getId() != null && domain.getId() > 0) {
      e.setId(domain.getId());
    }
    e.setProjectId(domain.getProjectId());
    e.setName(domain.getName());
    e.setDescription(domain.getDescription());
    e.setStatus(domain.getStatus());
    e.setPlannedDate(domain.getPlannedDate());
    e.setActualDate(domain.getActualDate());
    e.setBillingAmount(domain.getBillingAmount());
    e.setBilled(domain.isBilled());
    e.setSortOrder(domain.getSortOrder());
    e.setCreatedAt(domain.getCreatedAt());
    e.setUpdatedAt(domain.getUpdatedAt());
    e.setDeletedAt(domain.getDeletedAt());
    e.setVersion(domain.getVersion() != null ? domain.getVersion() : 0L);
    return e;
  }

  static ProjectMilestone toDomain(ProjectMilestoneJpaEntity e) {
    return ProjectMilestone.reconstruct(
        new ReconstructProjectMilestoneParams(
            e.getId(),
            e.getProjectId(),
            e.getName(),
            e.getDescription(),
            e.getStatus(),
            e.getPlannedDate(),
            e.getActualDate(),
            e.getBillingAmount(),
            e.isBilled(),
            e.getSortOrder(),
            e.getCreatedAt(),
            e.getUpdatedAt(),
            e.getDeletedAt(),
            e.getVersion()));
  }
}
