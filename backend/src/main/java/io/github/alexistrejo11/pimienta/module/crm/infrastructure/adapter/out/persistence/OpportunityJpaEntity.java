package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "crm_opportunities")
public class OpportunityJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "contact_name", length = 255)
  private String contactName;

  @Column(name = "contact_email", length = 320)
  private String contactEmail;

  @Column(name = "contact_phone", length = 64)
  private String contactPhone;

  @Column(name = "company_name", length = 255)
  private String companyName;

  @Column(name = "company_location", length = 255)
  private String companyLocation;

  @Column(length = 255)
  private String industry;

  @Column(nullable = false, length = 500)
  private String title;

  @Column(length = 4000)
  private String description;

  @Column(name = "estimated_value", precision = 19, scale = 4)
  private BigDecimal estimatedValue;

  @Column(name = "probability_percent", nullable = false)
  private int probabilityPercent;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private Opportunity.OpportunitySource source;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private Opportunity.OpportunityStatus status;

  @Column(name = "expected_close_date")
  private LocalDate expectedCloseDate;

  @Column(name = "actual_close_date")
  private LocalDate actualCloseDate;

  @Column(name = "assigned_salesman_id")
  private Long assignedSalesmanId;

  @Column(name = "lost_reason", length = 2000)
  private String lostReason;

  @Column(name = "converted_project_id")
  private Long convertedProjectId;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(nullable = false)
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyLocation() {
    return companyLocation;
  }

  public void setCompanyLocation(String companyLocation) {
    this.companyLocation = companyLocation;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getEstimatedValue() {
    return estimatedValue;
  }

  public void setEstimatedValue(BigDecimal estimatedValue) {
    this.estimatedValue = estimatedValue;
  }

  public int getProbabilityPercent() {
    return probabilityPercent;
  }

  public void setProbabilityPercent(int probabilityPercent) {
    this.probabilityPercent = probabilityPercent;
  }

  public Opportunity.OpportunitySource getSource() {
    return source;
  }

  public void setSource(Opportunity.OpportunitySource source) {
    this.source = source;
  }

  public Opportunity.OpportunityStatus getStatus() {
    return status;
  }

  public void setStatus(Opportunity.OpportunityStatus status) {
    this.status = status;
  }

  public LocalDate getExpectedCloseDate() {
    return expectedCloseDate;
  }

  public void setExpectedCloseDate(LocalDate expectedCloseDate) {
    this.expectedCloseDate = expectedCloseDate;
  }

  public LocalDate getActualCloseDate() {
    return actualCloseDate;
  }

  public void setActualCloseDate(LocalDate actualCloseDate) {
    this.actualCloseDate = actualCloseDate;
  }

  public Long getAssignedSalesmanId() {
    return assignedSalesmanId;
  }

  public void setAssignedSalesmanId(Long assignedSalesmanId) {
    this.assignedSalesmanId = assignedSalesmanId;
  }

  public String getLostReason() {
    return lostReason;
  }

  public void setLostReason(String lostReason) {
    this.lostReason = lostReason;
  }

  public Long getConvertedProjectId() {
    return convertedProjectId;
  }

  public void setConvertedProjectId(Long convertedProjectId) {
    this.convertedProjectId = convertedProjectId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
