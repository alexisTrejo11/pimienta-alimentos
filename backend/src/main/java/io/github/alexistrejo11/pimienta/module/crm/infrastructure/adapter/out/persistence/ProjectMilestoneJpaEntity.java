package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
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
@Table(name = "crm_project_milestones")
public class ProjectMilestoneJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "project_id", nullable = false)
  private Long projectId;

  @Column(nullable = false, length = 500)
  private String name;

  @Column(length = 4000)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private ProjectMilestone.MilestoneStatus status;

  @Column(name = "planned_date")
  private LocalDate plannedDate;

  @Column(name = "actual_date")
  private LocalDate actualDate;

  @Column(name = "billing_amount", precision = 19, scale = 4)
  private BigDecimal billingAmount;

  @Column(nullable = false)
  private boolean billed;

  @Column(name = "sort_order", nullable = false)
  private int sortOrder;

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

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ProjectMilestone.MilestoneStatus getStatus() {
    return status;
  }

  public void setStatus(ProjectMilestone.MilestoneStatus status) {
    this.status = status;
  }

  public LocalDate getPlannedDate() {
    return plannedDate;
  }

  public void setPlannedDate(LocalDate plannedDate) {
    this.plannedDate = plannedDate;
  }

  public LocalDate getActualDate() {
    return actualDate;
  }

  public void setActualDate(LocalDate actualDate) {
    this.actualDate = actualDate;
  }

  public BigDecimal getBillingAmount() {
    return billingAmount;
  }

  public void setBillingAmount(BigDecimal billingAmount) {
    this.billingAmount = billingAmount;
  }

  public boolean isBilled() {
    return billed;
  }

  public void setBilled(boolean billed) {
    this.billed = billed;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
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
