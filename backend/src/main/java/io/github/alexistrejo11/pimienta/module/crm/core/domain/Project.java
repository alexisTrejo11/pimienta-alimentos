package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import io.github.alexistrejo11.pimienta.shared.BaseDomain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Proyecto formal generado al ganar una Opportunity.
 *
 * Ciclo de vida:
 * PLANNING → ACTIVE → ON_HOLD → ACTIVE (reactivar)
 * → COMPLETED
 * → CANCELLED
 * → ARCHIVED (solo desde COMPLETED o CANCELLED)
 */
public class Project extends BaseDomain<Long> {

  // ─────────────────────────────────────────────
  // ORIGEN
  // ─────────────────────────────────────────────
  /** ID del cliente (entidad externa al módulo CRM) */
  private Long clientId;
  /** Oportunidad que originó este proyecto */
  private Long originOpportunityId;

  // ─────────────────────────────────────────────
  // IDENTIFICACIÓN
  // ─────────────────────────────────────────────
  private String projectCode; // ej. "PROJ-2024-042"
  private String projectName;
  private String description;
  private ProjectType type;
  private ProjectStatus status;
  private ProjectPriority priority;

  // ─────────────────────────────────────────────
  // EQUIPO
  // ─────────────────────────────────────────────
  private Long projectManagerId;
  private Long assignedSalesmanId;

  // ─────────────────────────────────────────────
  // FECHAS
  // ─────────────────────────────────────────────
  private LocalDate plannedStartDate;
  private LocalDate plannedEndDate;
  private LocalDate actualStartDate;
  private LocalDate actualEndDate;
  private String onHoldReason;

  // ─────────────────────────────────────────────
  // FINANCIERO
  // ─────────────────────────────────────────────
  /** Valor contratado / presupuesto aprobado */
  private BigDecimal contractedValue;
  /** Costo estimado de ejecución */
  private BigDecimal estimatedCost;
  /** Costo real acumulado (se actualiza durante el proyecto) */
  private BigDecimal actualCost;

  // ─────────────────────────────────────────────
  // PROGRESO
  // ─────────────────────────────────────────────
  /** Porcentaje de avance: 0–100 */
  private int progressPercent;
  private String cancellationReason;

  // ─────────────────────────────────────────────
  // ENUMERACIONES
  // ─────────────────────────────────────────────

  public enum ProjectStatus {
    PLANNING, // Definiendo alcance, equipo y fechas
    ACTIVE, // En ejecución
    ON_HOLD, // Pausado (requiere razón)
    COMPLETED, // Entregado y cerrado exitosamente
    CANCELLED, // Cancelado (requiere razón)
    ARCHIVED // Solo lectura, histórico
  }

  public enum ProjectType {
    CONSULTING,
    SOFTWARE_DEVELOPMENT,
    IMPLEMENTATION,
    MAINTENANCE,
    TRAINING,
    RESEARCH,
    OTHER
  }

  public enum ProjectPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
  }

  // ─────────────────────────────────────────────
  // CONSTRUCTOR
  // ─────────────────────────────────────────────

  private Project() {
    this.id = 0L;
    this.progressPercent = 0;
    this.contractedValue = BigDecimal.ZERO;
    this.estimatedCost = BigDecimal.ZERO;
    this.actualCost = BigDecimal.ZERO;
    this.status = ProjectStatus.PLANNING;
    this.priority = ProjectPriority.MEDIUM;
    this.type = ProjectType.OTHER;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.version = 0L;
  }

  // ─────────────────────────────────────────────
  // FACTORY METHOD
  // ─────────────────────────────────────────────

  public static Project create(
      String projectCode, String projectName, String description,
      Long clientId, Long originOpportunityId,
      ProjectType type, ProjectPriority priority,
      Long projectManagerId, Long assignedSalesmanId,
      LocalDate plannedStartDate, LocalDate plannedEndDate,
      BigDecimal contractedValue, BigDecimal estimatedCost) {

    var now = LocalDateTime.now();
    var project = new Project();
    project.projectCode = projectCode;
    project.projectName = projectName;
    project.description = description;
    project.clientId = clientId;
    project.originOpportunityId = originOpportunityId;
    project.type = type;
    project.priority = priority;
    project.projectManagerId = projectManagerId;
    project.assignedSalesmanId = assignedSalesmanId;
    project.plannedStartDate = plannedStartDate;
    project.plannedEndDate = plannedEndDate;
    project.contractedValue = contractedValue;
    project.estimatedCost = estimatedCost;
    project.actualCost = BigDecimal.ZERO;
    project.status = ProjectStatus.PLANNING;
    project.progressPercent = 0;
    project.createdAt = now;
    project.updatedAt = now;
    return project;
  }

  public static Project reconstruct(ReconstructProjectParams p) {
    Project project = new Project();
    project.setId(p.id());
    project.setCreatedAt(p.createdAt());
    project.setUpdatedAt(p.updatedAt());
    project.setDeletedAt(p.deletedAt());
    project.setVersion(p.version() != null ? p.version() : 0L);
    project.clientId = p.clientId();
    project.originOpportunityId = p.originOpportunityId();
    project.projectCode = p.projectCode();
    project.projectName = p.projectName();
    project.description = p.description();
    project.type = p.type() != null ? p.type() : ProjectType.OTHER;
    project.status = p.status() != null ? p.status() : ProjectStatus.PLANNING;
    project.priority = p.priority() != null ? p.priority() : ProjectPriority.MEDIUM;
    project.projectManagerId = p.projectManagerId();
    project.assignedSalesmanId = p.assignedSalesmanId();
    project.plannedStartDate = p.plannedStartDate();
    project.plannedEndDate = p.plannedEndDate();
    project.actualStartDate = p.actualStartDate();
    project.actualEndDate = p.actualEndDate();
    project.onHoldReason = p.onHoldReason();
    project.contractedValue = p.contractedValue() != null ? p.contractedValue() : BigDecimal.ZERO;
    project.estimatedCost = p.estimatedCost() != null ? p.estimatedCost() : BigDecimal.ZERO;
    project.actualCost = p.actualCost() != null ? p.actualCost() : BigDecimal.ZERO;
    project.progressPercent = p.progressPercent();
    project.cancellationReason = p.cancellationReason();
    return project;
  }

  public void softDelete() {
    setDeletedAt(LocalDateTime.now());
    setUpdatedAt(LocalDateTime.now());
    Long v = getVersion();
    setVersion(v != null ? v + 1 : 1L);
  }

  // ─────────────────────────────────────────────
  // TRANSICIONES DE ESTADO
  // ─────────────────────────────────────────────

  public void activate() {
    if (status != ProjectStatus.PLANNING && status != ProjectStatus.ON_HOLD)
      throw new IllegalStateException("No se puede activar desde estado " + status);
    this.status = ProjectStatus.ACTIVE;
    if (this.actualStartDate == null)
      this.actualStartDate = LocalDate.now();
    this.onHoldReason = null;
    this.updatedAt = LocalDateTime.now();
  }

  public void putOnHold(String reason) {
    if (status != ProjectStatus.ACTIVE)
      throw new IllegalStateException("Solo se pueden pausar proyectos ACTIVE");
    if (reason == null || reason.isBlank())
      throw new IllegalArgumentException("Se requiere razón para pausar");
    this.status = ProjectStatus.ON_HOLD;
    this.onHoldReason = reason;
    this.updatedAt = LocalDateTime.now();
  }

  public void complete() {
    if (status != ProjectStatus.ACTIVE)
      throw new IllegalStateException("Solo se pueden completar proyectos ACTIVE");
    this.status = ProjectStatus.COMPLETED;
    this.progressPercent = 100;
    this.actualEndDate = LocalDate.now();
    this.updatedAt = LocalDateTime.now();
  }

  public void cancel(String reason) {
    if (reason == null || reason.isBlank())
      throw new IllegalArgumentException("Se requiere razón de cancelación");
    if (status == ProjectStatus.COMPLETED || status == ProjectStatus.ARCHIVED)
      throw new IllegalStateException("No se puede cancelar un proyecto en estado " + status);
    this.status = ProjectStatus.CANCELLED;
    this.cancellationReason = reason;
    this.updatedAt = LocalDateTime.now();
  }

  public void archive() {
    if (status != ProjectStatus.COMPLETED && status != ProjectStatus.CANCELLED)
      throw new IllegalStateException("Solo se pueden archivar proyectos COMPLETED o CANCELLED");
    this.status = ProjectStatus.ARCHIVED;
    this.updatedAt = LocalDateTime.now();
  }

  // ─────────────────────────────────────────────
  // LÓGICA DE DOMINIO
  // ─────────────────────────────────────────────

  public void updateProgress(int percent) {
    if (percent < 0 || percent > 100)
      throw new IllegalArgumentException("El progreso debe ser entre 0 y 100");
    this.progressPercent = percent;
    this.updatedAt = LocalDateTime.now();
  }

  public void addActualCost(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("El monto debe ser positivo");
    this.actualCost = this.actualCost.add(amount);
    this.updatedAt = LocalDateTime.now();
  }

  /** Margen bruto: contractedValue − actualCost */
  public BigDecimal getGrossMargin() {
    return contractedValue.subtract(actualCost);
  }

  /** Margen en porcentaje */
  public BigDecimal getMarginPercent() {
    if (contractedValue.compareTo(BigDecimal.ZERO) == 0)
      return BigDecimal.ZERO;
    return getGrossMargin()
        .divide(contractedValue, 4, RoundingMode.HALF_UP)
        .multiply(new BigDecimal("100"));
  }

  /** ¿El costo real ya superó el estimado? */
  public boolean isOverBudget() {
    return actualCost.compareTo(estimatedCost) > 0;
  }

  /** ¿Está retrasado respecto a la fecha planificada? */
  public boolean isOverdue() {
    return status == ProjectStatus.ACTIVE
        && plannedEndDate != null
        && LocalDate.now().isAfter(plannedEndDate);
  }

  public boolean isActive() {
    return status == ProjectStatus.ACTIVE || status == ProjectStatus.ON_HOLD;
  }

  // ─────────────────────────────────────────────
  // GETTERS & SETTERS
  // ─────────────────────────────────────────────

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Long getOriginOpportunityId() {
    return originOpportunityId;
  }

  public void setOriginOpportunityId(Long id) {
    this.originOpportunityId = id;
  }

  public String getProjectCode() {
    return projectCode;
  }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ProjectType getType() {
    return type;
  }

  public void setType(ProjectType type) {
    this.type = type;
  }

  public ProjectStatus getStatus() {
    return status;
  }

  public ProjectPriority getPriority() {
    return priority;
  }

  public void setPriority(ProjectPriority priority) {
    this.priority = priority;
  }

  public Long getProjectManagerId() {
    return projectManagerId;
  }

  public void setProjectManagerId(Long id) {
    this.projectManagerId = id;
  }

  public Long getAssignedSalesmanId() {
    return assignedSalesmanId;
  }

  public void setAssignedSalesmanId(Long id) {
    this.assignedSalesmanId = id;
  }

  public LocalDate getPlannedStartDate() {
    return plannedStartDate;
  }

  public void setPlannedStartDate(LocalDate d) {
    this.plannedStartDate = d;
  }

  public LocalDate getPlannedEndDate() {
    return plannedEndDate;
  }

  public void setPlannedEndDate(LocalDate d) {
    this.plannedEndDate = d;
  }

  public LocalDate getActualStartDate() {
    return actualStartDate;
  }

  public LocalDate getActualEndDate() {
    return actualEndDate;
  }

  public String getOnHoldReason() {
    return onHoldReason;
  }

  public String getCancellationReason() {
    return cancellationReason;
  }

  public BigDecimal getContractedValue() {
    return contractedValue;
  }

  public void setContractedValue(BigDecimal v) {
    this.contractedValue = v;
  }

  public BigDecimal getEstimatedCost() {
    return estimatedCost;
  }

  public void setEstimatedCost(BigDecimal v) {
    this.estimatedCost = v;
  }

  public BigDecimal getActualCost() {
    return actualCost;
  }

  public int getProgressPercent() {
    return progressPercent;
  }
}