package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;

public class ProjectSearchRequest {

  private Project.ProjectStatus status;
  private Long clientId;
  private Long projectManagerId;
  private Long originOpportunityId;
  private int page = 0;
  private int size = 20;

  public Project.ProjectStatus getStatus() {
    return status;
  }

  public void setStatus(Project.ProjectStatus status) {
    this.status = status;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Long getProjectManagerId() {
    return projectManagerId;
  }

  public void setProjectManagerId(Long projectManagerId) {
    this.projectManagerId = projectManagerId;
  }

  public Long getOriginOpportunityId() {
    return originOpportunityId;
  }

  public void setOriginOpportunityId(Long originOpportunityId) {
    this.originOpportunityId = originOpportunityId;
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
