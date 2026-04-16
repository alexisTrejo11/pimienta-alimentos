package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Carga desde persistencia sin re-ejecutar reglas de alta. */
public record ReconstructOpportunityParams(
    Long id,
    String contactName,
    String contactEmail,
    String contactPhone,
    String companyName,
    String companyLocation,
    String industry,
    String title,
    String description,
    BigDecimal estimatedValue,
    int probabilityPercent,
    Opportunity.OpportunitySource source,
    Opportunity.OpportunityStatus status,
    LocalDate expectedCloseDate,
    LocalDate actualCloseDate,
    Long assignedSalesmanId,
    String lostReason,
    Long convertedProjectId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private Long id;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String companyName;
    private String companyLocation;
    private String industry;
    private String title;
    private String description;
    private BigDecimal estimatedValue;
    private int probabilityPercent;
    private Opportunity.OpportunitySource source;
    private Opportunity.OpportunityStatus status;
    private LocalDate expectedCloseDate;
    private LocalDate actualCloseDate;
    private Long assignedSalesmanId;
    private String lostReason;
    private Long convertedProjectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long version;

    private Builder() {}

    public Builder id(Long id) { this.id = id; return this; }

    public Builder contactName(String contactName) { this.contactName = contactName; return this; }

    public Builder contactEmail(String contactEmail) { this.contactEmail = contactEmail; return this; }

    public Builder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }

    public Builder companyName(String companyName) { this.companyName = companyName; return this; }

    public Builder companyLocation(String companyLocation) { this.companyLocation = companyLocation; return this; }

    public Builder industry(String industry) { this.industry = industry; return this; }

    public Builder title(String title) { this.title = title; return this; }

    public Builder description(String description) { this.description = description; return this; }

    public Builder estimatedValue(BigDecimal estimatedValue) { this.estimatedValue = estimatedValue; return this; }

    public Builder probabilityPercent(int probabilityPercent) { this.probabilityPercent = probabilityPercent; return this; }

    public Builder source(Opportunity.OpportunitySource source) { this.source = source; return this; }

    public Builder status(Opportunity.OpportunityStatus status) { this.status = status; return this; }

    public Builder expectedCloseDate(LocalDate expectedCloseDate) { this.expectedCloseDate = expectedCloseDate; return this; }

    public Builder actualCloseDate(LocalDate actualCloseDate) { this.actualCloseDate = actualCloseDate; return this; }

    public Builder assignedSalesmanId(Long assignedSalesmanId) { this.assignedSalesmanId = assignedSalesmanId; return this; }

    public Builder lostReason(String lostReason) { this.lostReason = lostReason; return this; }

    public Builder convertedProjectId(Long convertedProjectId) { this.convertedProjectId = convertedProjectId; return this; }

    public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

    public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

    public Builder deletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; return this; }

    public Builder version(Long version) { this.version = version; return this; }

    public ReconstructOpportunityParams build() {
      return new ReconstructOpportunityParams(
          id, contactName, contactEmail, contactPhone, companyName, companyLocation,
          industry, title, description, estimatedValue, probabilityPercent,
          source, status, expectedCloseDate, actualCloseDate, assignedSalesmanId,
          lostReason, convertedProjectId, createdAt, updatedAt, deletedAt, version);
    }
  }
}
