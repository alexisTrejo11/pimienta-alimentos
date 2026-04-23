package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Query parameters for GET /api/v1/opportunities (extends pagination).")
public class OpportunitySearchRequest extends PageableRequest {

  @Schema(description = "Filter by pipeline status.", example = "PROPOSAL")
  private Opportunity.OpportunityStatus status;

  @Schema(description = "Substring match on company name (repository-defined semantics).", example = "Acme")
  private String companyNameContains;

  @Schema(description = "Substring match on title.", example = "rollout")
  private String titleContains;

  public Opportunity.OpportunityStatus getStatus() {
    return status;
  }

  public void setStatus(Opportunity.OpportunityStatus status) {
    this.status = status;
  }

  public String getCompanyNameContains() {
    return companyNameContains;
  }

  public void setCompanyNameContains(String companyNameContains) {
    this.companyNameContains = companyNameContains;
  }

  public String getTitleContains() {
    return titleContains;
  }

  public void setTitleContains(String titleContains) {
    this.titleContains = titleContains;
  }
}
