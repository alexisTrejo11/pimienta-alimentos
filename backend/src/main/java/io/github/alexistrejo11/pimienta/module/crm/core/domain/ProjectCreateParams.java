package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Parameter object for {@link Project#create(ProjectCreateParams)}. */
public record ProjectCreateParams(
    String projectCode,
    String projectName,
    String description,
    Long clientId,
    Long originOpportunityId,
    Project.ProjectType type,
    Project.ProjectPriority priority,
    Long projectManagerId,
    Long assignedSalesmanId,
    LocalDate plannedStartDate,
    LocalDate plannedEndDate,
    BigDecimal contractedValue,
    BigDecimal estimatedCost) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String projectCode;
    private String projectName;
    private String description;
    private Long clientId;
    private Long originOpportunityId;
    private Project.ProjectType type;
    private Project.ProjectPriority priority;
    private Long projectManagerId;
    private Long assignedSalesmanId;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private BigDecimal contractedValue;
    private BigDecimal estimatedCost;

    private Builder() {}

    public Builder projectCode(String projectCode) { this.projectCode = projectCode; return this; }

    public Builder projectName(String projectName) { this.projectName = projectName; return this; }

    public Builder description(String description) { this.description = description; return this; }

    public Builder clientId(Long clientId) { this.clientId = clientId; return this; }

    public Builder originOpportunityId(Long originOpportunityId) { this.originOpportunityId = originOpportunityId; return this; }

    public Builder type(Project.ProjectType type) { this.type = type; return this; }

    public Builder priority(Project.ProjectPriority priority) { this.priority = priority; return this; }

    public Builder projectManagerId(Long projectManagerId) { this.projectManagerId = projectManagerId; return this; }

    public Builder assignedSalesmanId(Long assignedSalesmanId) { this.assignedSalesmanId = assignedSalesmanId; return this; }

    public Builder plannedStartDate(LocalDate plannedStartDate) { this.plannedStartDate = plannedStartDate; return this; }

    public Builder plannedEndDate(LocalDate plannedEndDate) { this.plannedEndDate = plannedEndDate; return this; }

    public Builder contractedValue(BigDecimal contractedValue) { this.contractedValue = contractedValue; return this; }

    public Builder estimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; return this; }

    public ProjectCreateParams build() {
      return new ProjectCreateParams(
          projectCode, projectName, description, clientId, originOpportunityId,
          type, priority, projectManagerId, assignedSalesmanId,
          plannedStartDate, plannedEndDate, contractedValue, estimatedCost);
    }
  }
}
