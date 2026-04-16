package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import io.github.alexistrejo11.pimienta.shared.BaseDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Hito (milestone) dentro de un proyecto.
 * Permite dividir un Project en entregables medibles con fechas y valor
 * parcial.
 *
 * Relación: Project 1 ──── N ProjectMilestone
 */
public class ProjectMilestone extends BaseDomain<Long> {

  private Long projectId;

  private String name;
  private String description;
  private MilestoneStatus status;

  // ─────────────────────────────────────────────
  // FECHAS
  // ─────────────────────────────────────────────
  private LocalDate plannedDate;
  private LocalDate actualDate;

  // ─────────────────────────────────────────────
  // FINANCIERO
  // ─────────────────────────────────────────────
  /**
   * Valor monetario asociado al hito.
   * Útil si el contrato es por entregables (ej. "pago al entregar fase 2").
   */
  private BigDecimal billingAmount;
  private boolean billed;

  /** Orden de presentación dentro del proyecto */
  private int sortOrder;

  // ─────────────────────────────────────────────
  // ENUMERACIONES
  // ─────────────────────────────────────────────

  public enum MilestoneStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    DELAYED,
    CANCELLED
  }

  // ─────────────────────────────────────────────
  // CONSTRUCTOR
  // ─────────────────────────────────────────────

  private ProjectMilestone() {
    this.id = 0L;
    this.status = MilestoneStatus.PENDING;
    this.billingAmount = BigDecimal.ZERO;
    this.billed = false;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.version = 0L;
  }

  // ─────────────────────────────────────────────
  // FACTORY METHOD
  // ─────────────────────────────────────────────

  public static ProjectMilestone create(ProjectMilestoneCreateParams params) {
    var milestone = new ProjectMilestone();
    milestone.projectId = params.projectId();
    milestone.name = params.name();
    milestone.description = params.description();
    milestone.plannedDate = params.plannedDate();
    milestone.billingAmount = params.billingAmount();
    milestone.sortOrder = params.sortOrder();
    return milestone;
  }

  public static ProjectMilestone reconstruct(ReconstructProjectMilestoneParams p) {
    ProjectMilestone m = new ProjectMilestone();
    m.setId(p.id());
    m.setCreatedAt(p.createdAt());
    m.setUpdatedAt(p.updatedAt());
    m.setDeletedAt(p.deletedAt());
    m.setVersion(p.version() != null ? p.version() : 0L);
    m.projectId = p.projectId();
    m.name = p.name();
    m.description = p.description();
    m.status = p.status() != null ? p.status() : MilestoneStatus.PENDING;
    m.plannedDate = p.plannedDate();
    m.actualDate = p.actualDate();
    m.billingAmount = p.billingAmount() != null ? p.billingAmount() : BigDecimal.ZERO;
    m.billed = p.billed();
    m.sortOrder = p.sortOrder();
    return m;
  }

  public void softDelete() {
    setDeletedAt(LocalDateTime.now());
    setUpdatedAt(LocalDateTime.now());
    Long v = getVersion();
    setVersion(v != null ? v + 1 : 1L);
  }

  // ─────────────────────────────────────────────
  // TRANSICIONES
  // ─────────────────────────────────────────────

  public void start() {
    this.status = MilestoneStatus.IN_PROGRESS;
    this.updatedAt = LocalDateTime.now();
  }

  public void complete() {
    this.status = MilestoneStatus.COMPLETED;
    this.actualDate = LocalDate.now();
    this.updatedAt = LocalDateTime.now();
  }

  public void markDelayed() {
    this.status = MilestoneStatus.DELAYED;
    this.updatedAt = LocalDateTime.now();
  }

  public void cancel() {
    this.status = MilestoneStatus.CANCELLED;
    this.updatedAt = LocalDateTime.now();
  }

  public void markBilled() {
    if (status != MilestoneStatus.COMPLETED)
      throw new IllegalStateException("Solo se puede facturar un hito COMPLETED");
    this.billed = true;
    this.updatedAt = LocalDateTime.now();
  }

  // ─────────────────────────────────────────────
  // LÓGICA
  // ─────────────────────────────────────────────

  public boolean isOverdue() {
    return status != MilestoneStatus.COMPLETED
        && status != MilestoneStatus.CANCELLED
        && plannedDate != null
        && LocalDate.now().isAfter(plannedDate);
  }

  // ─────────────────────────────────────────────
  // GETTERS & SETTERS
  // ─────────────────────────────────────────────

  public Long getProjectId() {
    return projectId;
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

  public MilestoneStatus getStatus() {
    return status;
  }

  public LocalDate getPlannedDate() {
    return plannedDate;
  }

  public void setPlannedDate(LocalDate d) {
    this.plannedDate = d;
  }

  public LocalDate getActualDate() {
    return actualDate;
  }

  public BigDecimal getBillingAmount() {
    return billingAmount;
  }

  public void setBillingAmount(BigDecimal v) {
    this.billingAmount = v;
  }

  public boolean isBilled() {
    return billed;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }
}
