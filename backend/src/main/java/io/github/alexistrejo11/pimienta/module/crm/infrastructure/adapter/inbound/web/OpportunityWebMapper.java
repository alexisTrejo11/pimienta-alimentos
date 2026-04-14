package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.crm.core.application.CreateOpportunityParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.OpportunitySummary;
import io.github.alexistrejo11.pimienta.module.crm.core.application.UpdateOpportunityParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.WinOpportunityParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.query.OpportunitySearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.CreateOpportunityRequest;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.OpportunityResponse;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.OpportunitySearchRequest;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.OpportunitySummaryResponse;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.UpdateOpportunityRequest;
import io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto.WinOpportunityRequest;

public final class OpportunityWebMapper {

  private OpportunityWebMapper() {}

  public static OpportunitySearchCriteria toCriteria(OpportunitySearchRequest request) {
    return new OpportunitySearchCriteria(
        request.getStatus(), request.getCompanyNameContains(), request.getTitleContains());
  }

  public static CreateOpportunityParams toCreateParams(CreateOpportunityRequest request) {
    return new CreateOpportunityParams(
        request.title(),
        request.description(),
        request.contactName(),
        request.contactEmail(),
        request.contactPhone(),
        request.companyName(),
        request.companyLocation(),
        request.industry(),
        request.estimatedValue(),
        request.probabilityPercent(),
        request.source(),
        request.expectedCloseDate(),
        request.assignedSalesmanId());
  }

  public static UpdateOpportunityParams toUpdateParams(UpdateOpportunityRequest request) {
    return new UpdateOpportunityParams(
        request.title(),
        request.description(),
        request.contactName(),
        request.contactEmail(),
        request.contactPhone(),
        request.companyName(),
        request.companyLocation(),
        request.industry(),
        request.estimatedValue(),
        request.probabilityPercent(),
        request.source(),
        request.expectedCloseDate(),
        request.assignedSalesmanId());
  }

  public static WinOpportunityParams toWinParams(WinOpportunityRequest request) {
    return new WinOpportunityParams(
        request.projectCode(),
        request.projectName(),
        request.description(),
        request.clientId(),
        request.type(),
        request.priority(),
        request.projectManagerId(),
        request.assignedSalesmanId(),
        request.plannedStartDate(),
        request.plannedEndDate(),
        request.contractedValue(),
        request.estimatedCost());
  }

  public static OpportunityResponse toResponse(Opportunity o) {
    return new OpportunityResponse(
        o.getId(),
        o.getContactName(),
        o.getContactEmail(),
        o.getContactPhone(),
        o.getCompanyName(),
        o.getCompanyLocation(),
        o.getIndustry(),
        o.getTitle(),
        o.getDescription(),
        o.getEstimatedValue(),
        o.getProbabilityPercent(),
        o.getSource(),
        o.getStatus(),
        o.getExpectedCloseDate(),
        o.getActualCloseDate(),
        o.getAssignedSalesmanId(),
        o.getLostReason(),
        o.getConvertedProjectId(),
        o.getCreatedAt(),
        o.getUpdatedAt());
  }

  public static OpportunitySummaryResponse toSummaryResponse(OpportunitySummary s) {
    return new OpportunitySummaryResponse(
        toResponse(s.opportunity()),
        s.taskCount(),
        s.openTaskCount(),
        s.weightedValue(),
        s.overdue());
  }
}
