package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.ReconstructOpportunityParams;

final class OpportunityPersistenceMapper {

  private OpportunityPersistenceMapper() {}

  static OpportunityJpaEntity toJpa(Opportunity domain) {
    OpportunityJpaEntity e = new OpportunityJpaEntity();
    if (domain.getId() != null && domain.getId() > 0) {
      e.setId(domain.getId());
    }
    e.setContactName(domain.getContactName());
    e.setContactEmail(domain.getContactEmail());
    e.setContactPhone(domain.getContactPhone());
    e.setCompanyName(domain.getCompanyName());
    e.setCompanyLocation(domain.getCompanyLocation());
    e.setIndustry(domain.getIndustry());
    e.setTitle(domain.getTitle());
    e.setDescription(domain.getDescription());
    e.setEstimatedValue(domain.getEstimatedValue());
    e.setProbabilityPercent(domain.getProbabilityPercent());
    e.setSource(domain.getSource());
    e.setStatus(domain.getStatus());
    e.setExpectedCloseDate(domain.getExpectedCloseDate());
    e.setActualCloseDate(domain.getActualCloseDate());
    e.setAssignedSalesmanId(domain.getAssignedSalesmanId());
    e.setLostReason(domain.getLostReason());
    e.setConvertedProjectId(domain.getConvertedProjectId());
    e.setCreatedAt(domain.getCreatedAt());
    e.setUpdatedAt(domain.getUpdatedAt());
    e.setDeletedAt(domain.getDeletedAt());
    e.setVersion(domain.getVersion() != null ? domain.getVersion() : 0L);
    return e;
  }

  static Opportunity toDomain(OpportunityJpaEntity e) {
    return Opportunity.reconstruct(
        new ReconstructOpportunityParams(
            e.getId(),
            e.getContactName(),
            e.getContactEmail(),
            e.getContactPhone(),
            e.getCompanyName(),
            e.getCompanyLocation(),
            e.getIndustry(),
            e.getTitle(),
            e.getDescription(),
            e.getEstimatedValue(),
            e.getProbabilityPercent(),
            e.getSource(),
            e.getStatus(),
            e.getExpectedCloseDate(),
            e.getActualCloseDate(),
            e.getAssignedSalesmanId(),
            e.getLostReason(),
            e.getConvertedProjectId(),
            e.getCreatedAt(),
            e.getUpdatedAt(),
            e.getDeletedAt(),
            e.getVersion()));
  }
}
