package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;

public class ProjectSearchRequest extends PageableRequest {

  private Project.ProjectStatus status;
  private Long clientId;
  private Long projectManagerId;
  private Long originOpportunityId;

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

}
