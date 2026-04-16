package io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.mapper;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.ReconstructOpportunityParams;
import io.github.alexistrejo11.pimienta.module.crm.adapter.out.persistence.model.OpportunityJpaEntity;

public class OpportunityPersistenceMapper {

  private OpportunityPersistenceMapper() {
  }

  public static OpportunityJpaEntity toJpa(Opportunity domain) {
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

  public static Opportunity toDomain(OpportunityJpaEntity e) {
    ReconstructOpportunityParams params = ReconstructOpportunityParams.builder()
        .id(e.getId())
        .contactName(e.getContactName())
        .contactEmail(e.getContactEmail())
        .contactPhone(e.getContactPhone())
        .companyName(e.getCompanyName())
        .companyLocation(e.getCompanyLocation())
        .industry(e.getIndustry())
        .title(e.getTitle())
        .description(e.getDescription())
        .estimatedValue(e.getEstimatedValue())
        .probabilityPercent(e.getProbabilityPercent())
        .source(e.getSource())
        .status(e.getStatus())
        .expectedCloseDate(e.getExpectedCloseDate())
        .actualCloseDate(e.getActualCloseDate())
        .assignedSalesmanId(e.getAssignedSalesmanId())
        .lostReason(e.getLostReason())
        .convertedProjectId(e.getConvertedProjectId())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt())
        .deletedAt(e.getDeletedAt())
        .version(e.getVersion())
        .build();
    return Opportunity.reconstruct(params);
  }
}
