package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import io.github.alexistrejo11.pimienta.shared.BaseDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa una oportunidad de negocio ANTES de convertirse en
 * cliente/proyecto.
 *
 * Ciclo de vida:
 * NEW → DISCOVERY → PROPOSAL → NEGOTIATION → WON (→ genera Project)
 * → LOST
 * → ABANDONED (sin respuesta)
 */
public class Opportunity extends BaseDomain<Long> {

  // ─────────────────────────────────────────────
  // CONTACTO / EMPRESA
  // ─────────────────────────────────────────────
  private String contactName;
  private String contactEmail;
  private String contactPhone;
  private String companyName;
  /** País / ciudad del prospecto */
  private String companyLocation;
  /** Industria o giro de la empresa */
  private String industry;

  // ─────────────────────────────────────────────
  // DATOS DE LA OPORTUNIDAD
  // ─────────────────────────────────────────────
  private String title;
  private String description;

  /** Valor económico estimado del deal */
  private BigDecimal estimatedValue;
  /** Probabilidad de cierre: 0–100 % */
  private int probabilityPercent;
  /** Valor ponderado = estimatedValue × probability */

  private OpportunitySource source;
  private OpportunityStatus status;

  // ─────────────────────────────────────────────
  // FECHAS CLAVE
  // ─────────────────────────────────────────────
  /** Fecha estimada de cierre */
  private LocalDate expectedCloseDate;
  /** Fecha real de cierre (WON o LOST) */
  private LocalDate actualCloseDate;

  // ─────────────────────────────────────────────
  // ASIGNACIÓN
  // ─────────────────────────────────────────────
  /** Vendedor responsable */
  private Long assignedSalesmanId;
  /** Motivo de pérdida (obligatorio al marcar LOST) */
  private String lostReason;
  /**
   * ID del Project generado al marcar WON.
   * null mientras la oportunidad no se ha ganado.
   */
  private Long convertedProjectId;

  // ─────────────────────────────────────────────
  // ENUMERACIONES
  // ─────────────────────────────────────────────

  public enum OpportunityStatus {
    NEW, // Recién registrada
    DISCOVERY, // Levantamiento de necesidades
    PROPOSAL, // Propuesta enviada
    NEGOTIATION, // En negociación
    WON, // Ganada → se convierte en Project
    LOST, // Perdida (requiere lostReason)
    ABANDONED // Sin respuesta del prospecto
  }

  public enum OpportunitySource {
    INBOUND, // El prospecto llegó solo (web, referido)
    OUTBOUND, // Prospección activa del equipo de ventas
    REFERRAL, // Referido por cliente existente
    SOCIAL_MEDIA,
    EVENT, // Feria, congreso, networking
    COLD_CALL,
    OTHER
  }

  // ─────────────────────────────────────────────
  // CONSTRUCTOR
  // ─────────────────────────────────────────────

  private Opportunity() {
    this.id = 0L;
    this.probabilityPercent = 10;
    this.estimatedValue = BigDecimal.ZERO;
    this.status = OpportunityStatus.NEW;
    this.source = OpportunitySource.OTHER;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.version = 0L;
  }

  // ─────────────────────────────────────────────
  // FACTORY METHODS
  // ─────────────────────────────────────────────

  public static Opportunity open(OpportunityOpenParams params) {
    var now = LocalDateTime.now();
    var opp = new Opportunity();
    opp.title = params.title();
    opp.description = params.description();
    opp.contactName = params.contactName();
    opp.contactEmail = params.contactEmail();
    opp.contactPhone = params.contactPhone();
    opp.companyName = params.companyName();
    opp.companyLocation = params.companyLocation();
    opp.industry = params.industry();
    opp.estimatedValue = params.estimatedValue();
    opp.probabilityPercent = params.probabilityPercent();
    opp.source = params.source();
    opp.expectedCloseDate = params.expectedCloseDate();
    opp.assignedSalesmanId = params.assignedSalesmanId();
    opp.status = OpportunityStatus.NEW;
    opp.createdAt = now;
    opp.updatedAt = now;
    return opp;
  }

  public static Opportunity reconstruct(ReconstructOpportunityParams p) {
    Opportunity o = new Opportunity();
    o.setId(p.id());
    o.setCreatedAt(p.createdAt());
    o.setUpdatedAt(p.updatedAt());
    o.setDeletedAt(p.deletedAt());
    o.setVersion(p.version() != null ? p.version() : 0L);
    o.contactName = p.contactName();
    o.contactEmail = p.contactEmail();
    o.contactPhone = p.contactPhone();
    o.companyName = p.companyName();
    o.companyLocation = p.companyLocation();
    o.industry = p.industry();
    o.title = p.title();
    o.description = p.description();
    o.estimatedValue = p.estimatedValue() != null ? p.estimatedValue() : BigDecimal.ZERO;
    o.probabilityPercent = p.probabilityPercent();
    o.source = p.source() != null ? p.source() : OpportunitySource.OTHER;
    o.status = p.status() != null ? p.status() : OpportunityStatus.NEW;
    o.expectedCloseDate = p.expectedCloseDate();
    o.actualCloseDate = p.actualCloseDate();
    o.assignedSalesmanId = p.assignedSalesmanId();
    o.lostReason = p.lostReason();
    o.convertedProjectId = p.convertedProjectId();
    return o;
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

  public void moveToDiscovery() {
    assertStatus(OpportunityStatus.NEW);
    this.status = OpportunityStatus.DISCOVERY;
    this.updatedAt = LocalDateTime.now();
  }

  public void sendProposal() {
    assertStatus(OpportunityStatus.DISCOVERY);
    this.status = OpportunityStatus.PROPOSAL;
    this.probabilityPercent = Math.max(this.probabilityPercent, 40);
    this.updatedAt = LocalDateTime.now();
  }

  public void startNegotiation() {
    assertStatus(OpportunityStatus.PROPOSAL);
    this.status = OpportunityStatus.NEGOTIATION;
    this.probabilityPercent = Math.max(this.probabilityPercent, 70);
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Marca la oportunidad como ganada.
   * 
   * @return el ID del proyecto creado debe asignarse después con
   *         setConvertedProjectId()
   */
  public void win() {
    if (status == OpportunityStatus.LOST || status == OpportunityStatus.ABANDONED)
      throw new IllegalStateException("No se puede ganar una oportunidad en estado " + status);
    this.status = OpportunityStatus.WON;
    this.probabilityPercent = 100;
    this.actualCloseDate = LocalDate.now();
    this.updatedAt = LocalDateTime.now();
  }

  public void lose(String reason) {
    if (reason == null || reason.isBlank())
      throw new IllegalArgumentException("Se requiere motivo de pérdida");
    if (status == OpportunityStatus.WON)
      throw new IllegalStateException("No se puede perder una oportunidad ya ganada");
    this.status = OpportunityStatus.LOST;
    this.lostReason = reason;
    this.probabilityPercent = 0;
    this.actualCloseDate = LocalDate.now();
    this.updatedAt = LocalDateTime.now();
  }

  public void abandon() {
    this.status = OpportunityStatus.ABANDONED;
    this.probabilityPercent = 0;
    this.updatedAt = LocalDateTime.now();
  }

  /** Reabrir una oportunidad perdida o abandonada */
  public void reopen() {
    if (status != OpportunityStatus.LOST && status != OpportunityStatus.ABANDONED)
      throw new IllegalStateException("Solo se pueden reabrir oportunidades LOST o ABANDONED");
    this.status = OpportunityStatus.DISCOVERY;
    this.lostReason = null;
    this.actualCloseDate = null;
    this.updatedAt = LocalDateTime.now();
  }

  // ─────────────────────────────────────────────
  // LÓGICA DE DOMINIO
  // ─────────────────────────────────────────────

  /** Valor ponderado por probabilidad */
  public BigDecimal getWeightedValue() {
    return estimatedValue.multiply(
        new BigDecimal(probabilityPercent)
            .divide(new BigDecimal("100"), 4, java.math.RoundingMode.HALF_UP));
  }

  public boolean isOverdue() {
    return expectedCloseDate != null
        && LocalDate.now().isAfter(expectedCloseDate)
        && (status != OpportunityStatus.WON && status != OpportunityStatus.LOST);
  }

  public boolean isActive() {
    return status != OpportunityStatus.WON
        && status != OpportunityStatus.LOST
        && status != OpportunityStatus.ABANDONED;
  }

  private void assertStatus(OpportunityStatus expected) {
    if (this.status != expected)
      throw new IllegalStateException(
          "Se esperaba estado " + expected + " pero es " + this.status);
  }

  // ─────────────────────────────────────────────
  // GETTERS & SETTERS
  // ─────────────────────────────────────────────

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
    if (probabilityPercent < 0 || probabilityPercent > 100)
      throw new IllegalArgumentException("La probabilidad debe ser entre 0 y 100");
    this.probabilityPercent = probabilityPercent;
  }

  public OpportunitySource getSource() {
    return source;
  }

  public void setSource(OpportunitySource source) {
    this.source = source;
  }

  public OpportunityStatus getStatus() {
    return status;
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

  public Long getAssignedSalesmanId() {
    return assignedSalesmanId;
  }

  public void setAssignedSalesmanId(Long assignedSalesmanId) {
    this.assignedSalesmanId = assignedSalesmanId;
  }

  public String getLostReason() {
    return lostReason;
  }

  public Long getConvertedProjectId() {
    return convertedProjectId;
  }

  public void setConvertedProjectId(Long convertedProjectId) {
    this.convertedProjectId = convertedProjectId;
  }
}