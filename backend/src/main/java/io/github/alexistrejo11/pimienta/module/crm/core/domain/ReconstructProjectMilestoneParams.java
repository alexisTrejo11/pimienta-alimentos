package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Carga desde persistencia sin re-ejecutar reglas de alta. */
public record ReconstructProjectMilestoneParams(
    Long id,
    Long projectId,
    String name,
    String description,
    ProjectMilestone.MilestoneStatus status,
    LocalDate plannedDate,
    LocalDate actualDate,
    BigDecimal billingAmount,
    boolean billed,
    int sortOrder,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private ProjectMilestone.MilestoneStatus status;
    private LocalDate plannedDate;
    private LocalDate actualDate;
    private BigDecimal billingAmount;
    private boolean billed;
    private int sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long version;

    private Builder() {}

    public Builder id(Long id) { this.id = id; return this; }

    public Builder projectId(Long projectId) { this.projectId = projectId; return this; }

    public Builder name(String name) { this.name = name; return this; }

    public Builder description(String description) { this.description = description; return this; }

    public Builder status(ProjectMilestone.MilestoneStatus status) { this.status = status; return this; }

    public Builder plannedDate(LocalDate plannedDate) { this.plannedDate = plannedDate; return this; }

    public Builder actualDate(LocalDate actualDate) { this.actualDate = actualDate; return this; }

    public Builder billingAmount(BigDecimal billingAmount) { this.billingAmount = billingAmount; return this; }

    public Builder billed(boolean billed) { this.billed = billed; return this; }

    public Builder sortOrder(int sortOrder) { this.sortOrder = sortOrder; return this; }

    public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

    public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

    public Builder deletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; return this; }

    public Builder version(Long version) { this.version = version; return this; }

    public ReconstructProjectMilestoneParams build() {
      return new ReconstructProjectMilestoneParams(
          id, projectId, name, description, status, plannedDate, actualDate,
          billingAmount, billed, sortOrder, createdAt, updatedAt, deletedAt, version);
    }
  }
}
