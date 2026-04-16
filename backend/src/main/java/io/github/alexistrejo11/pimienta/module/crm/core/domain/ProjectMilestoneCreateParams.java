package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Parameter object for {@link ProjectMilestone#create(ProjectMilestoneCreateParams)}. */
public record ProjectMilestoneCreateParams(
    Long projectId,
    String name,
    String description,
    LocalDate plannedDate,
    BigDecimal billingAmount,
    int sortOrder) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private Long projectId;
    private String name;
    private String description;
    private LocalDate plannedDate;
    private BigDecimal billingAmount;
    private int sortOrder;

    private Builder() {}

    public Builder projectId(Long projectId) { this.projectId = projectId; return this; }

    public Builder name(String name) { this.name = name; return this; }

    public Builder description(String description) { this.description = description; return this; }

    public Builder plannedDate(LocalDate plannedDate) { this.plannedDate = plannedDate; return this; }

    public Builder billingAmount(BigDecimal billingAmount) { this.billingAmount = billingAmount; return this; }

    public Builder sortOrder(int sortOrder) { this.sortOrder = sortOrder; return this; }

    public ProjectMilestoneCreateParams build() {
      return new ProjectMilestoneCreateParams(
          projectId, name, description, plannedDate, billingAmount, sortOrder);
    }
  }
}
