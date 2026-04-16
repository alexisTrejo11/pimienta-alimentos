package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;

public class OpportunitySearchRequest extends PageableRequest {

  private Opportunity.OpportunityStatus status;
  private String companyNameContains;
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
