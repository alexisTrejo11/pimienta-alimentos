package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ImssSalaryType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ImssWorkerType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Factor de integración, clasificación IMSS y prestaciones legales proporcionales (aguinaldo,
 * vacaciones, prima).
 */
public record BenefitsProfile(
    BigDecimal integrationFactor,
    ImssWorkerType imssWorkerType,
    ImssSalaryType imssSalaryType,
    int christmasBonusDays,
    int vacationDays,
    BigDecimal vacationPremiumPercent) {

  public static final BigDecimal DEFAULT_INTEGRATION = new BigDecimal("1.0452");
  public static final BigDecimal DEFAULT_VACATION_PREMIUM_PERCENT = new BigDecimal("25.00");

  public BenefitsProfile {
    integrationFactor = integrationFactor != null ? integrationFactor : DEFAULT_INTEGRATION;
    imssWorkerType = imssWorkerType != null ? imssWorkerType : ImssWorkerType.PERMANENT_URBAN;
    imssSalaryType = imssSalaryType != null ? imssSalaryType : ImssSalaryType.FIXED;
    if (christmasBonusDays <= 0) {
      christmasBonusDays = 15;
    }
    if (vacationDays <= 0) {
      vacationDays = 12;
    }
    vacationPremiumPercent =
        vacationPremiumPercent != null ? vacationPremiumPercent : DEFAULT_VACATION_PREMIUM_PERCENT;
  }

  public static BenefitsProfile legalDefaults() {
    return new BenefitsProfile(
        DEFAULT_INTEGRATION,
        ImssWorkerType.PERMANENT_URBAN,
        ImssSalaryType.FIXED,
        15,
        12,
        DEFAULT_VACATION_PREMIUM_PERCENT);
  }

  /**
   * Salario Base de Cotización (SBC) diario = salario diario × factor de integración.
   */
  public BigDecimal dailySbc(Compensation compensation) {
    return compensation
        .dailyBaseSalary()
        .multiply(integrationFactor)
        .setScale(2, RoundingMode.HALF_UP);
  }

  public BigDecimal annualChristmasBonus(Compensation compensation) {
    return compensation
        .dailyBaseSalary()
        .multiply(new BigDecimal(christmasBonusDays))
        .setScale(2, RoundingMode.HALF_UP);
  }

  public BigDecimal vacationPremium(Compensation compensation) {
    return compensation
        .dailyBaseSalary()
        .multiply(new BigDecimal(vacationDays))
        .multiply(vacationPremiumPercent.divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP))
        .setScale(2, RoundingMode.HALF_UP);
  }

  /** Cuota obrera IMSS (aprox. 2.375 % del SBC mensual). */
  public BigDecimal imssEmployeeContribution(Compensation compensation) {
    BigDecimal sbcMensual = dailySbc(compensation).multiply(new BigDecimal("30"));
    return sbcMensual.multiply(new BigDecimal("0.02375")).setScale(2, RoundingMode.HALF_UP);
  }

  /** Cuota patronal IMSS (aprox. 17.58 % del SBC mensual). */
  public BigDecimal imssEmployerContribution(Compensation compensation) {
    BigDecimal sbcMensual = dailySbc(compensation).multiply(new BigDecimal("30"));
    return sbcMensual.multiply(new BigDecimal("0.1758")).setScale(2, RoundingMode.HALF_UP);
  }

  /** INFONAVIT patronal: 5 % del SBC mensual. */
  public BigDecimal infonavitEmployerContribution(Compensation compensation) {
    BigDecimal sbcMensual = dailySbc(compensation).multiply(new BigDecimal("30"));
    return sbcMensual.multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.HALF_UP);
  }

  /** SAR/AFORE patronal: 2 % del SBC mensual. */
  public BigDecimal sarEmployerContribution(Compensation compensation) {
    BigDecimal sbcMensual = dailySbc(compensation).multiply(new BigDecimal("30"));
    return sbcMensual.multiply(new BigDecimal("0.02")).setScale(2, RoundingMode.HALF_UP);
  }

  /** Costo mensual total patrón: bruto + IMSS + INFONAVIT + SAR. */
  public BigDecimal totalMonthlyEmployerCost(Compensation compensation) {
    return compensation
        .grossMonthly()
        .add(imssEmployerContribution(compensation))
        .add(infonavitEmployerContribution(compensation))
        .add(sarEmployerContribution(compensation));
  }
}
