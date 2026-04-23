package io.github.alexistrejo11.pimienta.module.employees.core.domain.valueobject;

import java.math.BigDecimal;

public record Salary(
    BigDecimal hourlyRate,
    BigDecimal overtimeRate,
    BigDecimal lunchRate,
    BigDecimal bonusRate,
    BigDecimal deductionRate,
    BigDecimal taxRate,
    BigDecimal socialSecurityRate,
    BigDecimal pensionRate,
    BigDecimal healthInsuranceRate) {
  public static final BigDecimal OVERTIME_RATE = new BigDecimal(1.5);
  public static final BigDecimal LUNCH_RATE = new BigDecimal(1.0);
  public static final BigDecimal BONUS_RATE = new BigDecimal(1.0);
  public static final BigDecimal DEDUCTION_RATE = new BigDecimal(1.0);
  public static final BigDecimal TAX_RATE = new BigDecimal(1.0);
  public static final BigDecimal SOCIAL_SECURITY_RATE = new BigDecimal(1.0);
  public static final BigDecimal PENSION_RATE = new BigDecimal(1.0);
  public static final BigDecimal HEALTH_INSURANCE_RATE = new BigDecimal(1.0);

  public static Salary create(BigDecimal hourlyRate) {
    return new Salary(hourlyRate, OVERTIME_RATE, LUNCH_RATE, BONUS_RATE, DEDUCTION_RATE, TAX_RATE, SOCIAL_SECURITY_RATE,
        PENSION_RATE, HEALTH_INSURANCE_RATE);
  }

  public static Salary update(BigDecimal hourlyRate) {
    return new Salary(hourlyRate, OVERTIME_RATE, LUNCH_RATE, BONUS_RATE, DEDUCTION_RATE, TAX_RATE, SOCIAL_SECURITY_RATE,
        PENSION_RATE, HEALTH_INSURANCE_RATE);
  }

  public BigDecimal getSalaryPer15Days(BigDecimal hours) {
    return hourlyRate.multiply(hours).multiply(new BigDecimal(15));
  }

  public BigDecimal getSalaryPerMonth(BigDecimal hours) {
    return hourlyRate.multiply(hours).multiply(new BigDecimal(30));
  }

  public BigDecimal getSalaryPerYear(BigDecimal hours) {
    return hourlyRate.multiply(hours).multiply(new BigDecimal(365));
  }

  public BigDecimal getSalaryPerHour(BigDecimal hours) {
    return hourlyRate.multiply(hours);
  }
}
