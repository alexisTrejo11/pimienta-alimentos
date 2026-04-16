package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Carga desde persistencia sin re-ejecutar reglas de alta. */
public record ReconstructProjectParams(
    Long id,
    Long clientId,
    Long originOpportunityId,
    String projectCode,
    String projectName,
    String description,
    Project.ProjectType type,
    Project.ProjectStatus status,
    Project.ProjectPriority priority,
    Long projectManagerId,
    Long assignedSalesmanId,
    LocalDate plannedStartDate,
    LocalDate plannedEndDate,
    LocalDate actualStartDate,
    LocalDate actualEndDate,
    String onHoldReason,
    BigDecimal contractedValue,
    BigDecimal estimatedCost,
    BigDecimal actualCost,
    int progressPercent,
    String cancellationReason,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private Long id;
    private Long clientId;
    private Long originOpportunityId;
    private String projectCode;
    private String projectName;
    private String description;
    private Project.ProjectType type;
    private Project.ProjectStatus status;
    private Project.ProjectPriority priority;
    private Long projectManagerId;
    private Long assignedSalesmanId;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private String onHoldReason;
    private BigDecimal contractedValue;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private int progressPercent;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long version;

    private Builder() {}

    public Builder id(Long id) { this.id = id; return this; }

    public Builder clientId(Long clientId) { this.clientId = clientId; return this; }

    public Builder originOpportunityId(Long originOpportunityId) { this.originOpportunityId = originOpportunityId; return this; }

    public Builder projectCode(String projectCode) { this.projectCode = projectCode; return this; }

    public Builder projectName(String projectName) { this.projectName = projectName; return this; }

    public Builder description(String description) { this.description = description; return this; }

    public Builder type(Project.ProjectType type) { this.type = type; return this; }

    public Builder status(Project.ProjectStatus status) { this.status = status; return this; }

    public Builder priority(Project.ProjectPriority priority) { this.priority = priority; return this; }

    public Builder projectManagerId(Long projectManagerId) { this.projectManagerId = projectManagerId; return this; }

    public Builder assignedSalesmanId(Long assignedSalesmanId) { this.assignedSalesmanId = assignedSalesmanId; return this; }

    public Builder plannedStartDate(LocalDate plannedStartDate) { this.plannedStartDate = plannedStartDate; return this; }

    public Builder plannedEndDate(LocalDate plannedEndDate) { this.plannedEndDate = plannedEndDate; return this; }

    public Builder actualStartDate(LocalDate actualStartDate) { this.actualStartDate = actualStartDate; return this; }

    public Builder actualEndDate(LocalDate actualEndDate) { this.actualEndDate = actualEndDate; return this; }

    public Builder onHoldReason(String onHoldReason) { this.onHoldReason = onHoldReason; return this; }

    public Builder contractedValue(BigDecimal contractedValue) { this.contractedValue = contractedValue; return this; }

    public Builder estimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; return this; }

    public Builder actualCost(BigDecimal actualCost) { this.actualCost = actualCost; return this; }

    public Builder progressPercent(int progressPercent) { this.progressPercent = progressPercent; return this; }

    public Builder cancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; return this; }

    public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

    public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

    public Builder deletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; return this; }

    public Builder version(Long version) { this.version = version; return this; }

    public ReconstructProjectParams build() {
      return new ReconstructProjectParams(
          id, clientId, originOpportunityId, projectCode, projectName, description,
          type, status, priority, projectManagerId, assignedSalesmanId,
          plannedStartDate, plannedEndDate, actualStartDate, actualEndDate, onHoldReason,
          contractedValue, estimatedCost, actualCost, progressPercent, cancellationReason,
          createdAt, updatedAt, deletedAt, version);
    }
  }
}
