package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Paquete salarial: base semanal, bonos y vales; cálculos de bruto.
 */
public record Compensation(
    BigDecimal salaryPerWeek,
    BigDecimal bonuses,
    BigDecimal foodVouchers) {

  public Compensation {
    salaryPerWeek = salaryPerWeek != null ? salaryPerWeek : BigDecimal.ZERO;
    bonuses = bonuses != null ? bonuses : BigDecimal.ZERO;
    foodVouchers = foodVouchers != null ? foodVouchers : BigDecimal.ZERO;
  }

  public static Compensation baseline(BigDecimal salaryPerWeek) {
    return new Compensation(salaryPerWeek, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  /** Salario diario base (contrato / 7). */
  public BigDecimal dailyBaseSalary() {
    return salaryPerWeek.divide(new BigDecimal("7"), 6, RoundingMode.HALF_UP);
  }

  /** Salario bruto semanal (base + bonos + vales). */
  public BigDecimal grossWeekly() {
    return salaryPerWeek.add(bonuses).add(foodVouchers);
  }

  public BigDecimal grossPer15Days() {
    return grossWeekly().divide(new BigDecimal("2"), 6, RoundingMode.HALF_UP);
  }

  public BigDecimal grossMonthly() {
    return grossWeekly().multiply(new BigDecimal("4"));
  }
}
