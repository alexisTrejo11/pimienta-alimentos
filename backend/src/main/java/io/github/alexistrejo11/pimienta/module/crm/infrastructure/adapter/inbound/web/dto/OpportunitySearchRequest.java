package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;

public class OpportunitySearchRequest {

  private Opportunity.OpportunityStatus status;
  private String companyNameContains;
  private String titleContains;
  private int page = 0;
  private int size = 20;

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

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
