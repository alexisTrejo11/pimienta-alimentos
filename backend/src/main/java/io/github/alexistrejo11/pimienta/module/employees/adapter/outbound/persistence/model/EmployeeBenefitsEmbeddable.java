package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ImssSalaryType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ImssWorkerType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Embeddable
public class EmployeeBenefitsEmbeddable {

  @Column(name = "integration_factor", precision = 19, scale = 6)
  private BigDecimal integrationFactor;

  @Enumerated(EnumType.STRING)
  @Column(name = "imss_worker_type", length = 32)
  private ImssWorkerType imssWorkerType;

  @Enumerated(EnumType.STRING)
  @Column(name = "imss_salary_type", length = 32)
  private ImssSalaryType imssSalaryType;

  @Column(name = "christmas_bonus_days", nullable = false)
  private int christmasBonusDays;

  @Column(name = "vacation_days", nullable = false)
  private int vacationDays;

  @Column(name = "vacation_premium_percent", precision = 19, scale = 6)
  private BigDecimal vacationPremiumPercent;

  public BigDecimal getIntegrationFactor() {
    return integrationFactor;
  }

  public void setIntegrationFactor(BigDecimal integrationFactor) {
    this.integrationFactor = integrationFactor;
  }

  public ImssWorkerType getImssWorkerType() {
    return imssWorkerType;
  }

  public void setImssWorkerType(ImssWorkerType imssWorkerType) {
    this.imssWorkerType = imssWorkerType;
  }

  public ImssSalaryType getImssSalaryType() {
    return imssSalaryType;
  }

  public void setImssSalaryType(ImssSalaryType imssSalaryType) {
    this.imssSalaryType = imssSalaryType;
  }

  public int getChristmasBonusDays() {
    return christmasBonusDays;
  }

  public void setChristmasBonusDays(int christmasBonusDays) {
    this.christmasBonusDays = christmasBonusDays;
  }

  public int getVacationDays() {
    return vacationDays;
  }

  public void setVacationDays(int vacationDays) {
    this.vacationDays = vacationDays;
  }

  public BigDecimal getVacationPremiumPercent() {
    return vacationPremiumPercent;
  }

  public void setVacationPremiumPercent(BigDecimal vacationPremiumPercent) {
    this.vacationPremiumPercent = vacationPremiumPercent;
  }
}
