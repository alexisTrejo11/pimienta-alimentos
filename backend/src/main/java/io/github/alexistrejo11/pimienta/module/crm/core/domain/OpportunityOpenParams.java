package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Parameter object for {@link Opportunity#open(OpportunityOpenParams)}. */
public record OpportunityOpenParams(
    String title,
    String description,
    String contactName,
    String contactEmail,
    String contactPhone,
    String companyName,
    String companyLocation,
    String industry,
    BigDecimal estimatedValue,
    int probabilityPercent,
    Opportunity.OpportunitySource source,
    LocalDate expectedCloseDate,
    Long assignedSalesmanId) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String title;
    private String description;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String companyName;
    private String companyLocation;
    private String industry;
    private BigDecimal estimatedValue;
    private int probabilityPercent;
    private Opportunity.OpportunitySource source;
    private LocalDate expectedCloseDate;
    private Long assignedSalesmanId;

    private Builder() {}

    public Builder title(String title) { this.title = title; return this; }

    public Builder description(String description) { this.description = description; return this; }

    public Builder contactName(String contactName) { this.contactName = contactName; return this; }

    public Builder contactEmail(String contactEmail) { this.contactEmail = contactEmail; return this; }

    public Builder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }

    public Builder companyName(String companyName) { this.companyName = companyName; return this; }

    public Builder companyLocation(String companyLocation) { this.companyLocation = companyLocation; return this; }

    public Builder industry(String industry) { this.industry = industry; return this; }

    public Builder estimatedValue(BigDecimal estimatedValue) { this.estimatedValue = estimatedValue; return this; }

    public Builder probabilityPercent(int probabilityPercent) { this.probabilityPercent = probabilityPercent; return this; }

    public Builder source(Opportunity.OpportunitySource source) { this.source = source; return this; }

    public Builder expectedCloseDate(LocalDate expectedCloseDate) { this.expectedCloseDate = expectedCloseDate; return this; }

    public Builder assignedSalesmanId(Long assignedSalesmanId) { this.assignedSalesmanId = assignedSalesmanId; return this; }

    public OpportunityOpenParams build() {
      return new OpportunityOpenParams(
          title, description, contactName, contactEmail, contactPhone,
          companyName, companyLocation, industry, estimatedValue,
          probabilityPercent, source, expectedCloseDate, assignedSalesmanId);
    }
  }
}
