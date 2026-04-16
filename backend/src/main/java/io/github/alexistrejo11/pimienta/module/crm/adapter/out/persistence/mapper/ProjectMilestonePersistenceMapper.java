package io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.mapper;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.ReconstructProjectMilestoneParams;
import io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.model.ProjectMilestoneJpaEntity;

public class ProjectMilestonePersistenceMapper {

  private ProjectMilestonePersistenceMapper() {
  }

  public static ProjectMilestoneJpaEntity toJpa(ProjectMilestone domain) {
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

  public static ProjectMilestone toDomain(ProjectMilestoneJpaEntity e) {
    ReconstructProjectMilestoneParams params = ReconstructProjectMilestoneParams.builder()
        .id(e.getId())
        .projectId(e.getProjectId())
        .name(e.getName())
        .description(e.getDescription())
        .status(e.getStatus())
        .plannedDate(e.getPlannedDate())
        .actualDate(e.getActualDate())
        .billingAmount(e.getBillingAmount())
        .billed(e.isBilled())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt())
        .deletedAt(e.getDeletedAt())
        .version(e.getVersion())
        .build();
    return ProjectMilestone.reconstruct(params);
  }
}
