package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;

/** Query parameters for global task search (Spring binds {@code ?headquarterId=} and related keys). */
public class TaskSearchRequest extends PageableRequest {

  private Long headquarterId;
  private Long projectId;
  private Long opportunityId;
  private Long employeeId;
  private Task.Status status;

  public Long getHeadquarterId() {
    return headquarterId;
  }

  public void setHeadquarterId(Long headquarterId) {
    this.headquarterId = headquarterId;
  }

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public Long getOpportunityId() {
    return opportunityId;
  }

  public void setOpportunityId(Long opportunityId) {
    this.opportunityId = opportunityId;
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public Task.Status getStatus() {
    return status;
  }

  public void setStatus(Task.Status status) {
    this.status = status;
  }

}
