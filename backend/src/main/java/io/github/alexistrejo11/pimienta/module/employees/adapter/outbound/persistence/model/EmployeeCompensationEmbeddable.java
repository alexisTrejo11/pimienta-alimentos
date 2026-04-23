package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class EmployeeCompensationEmbeddable {

  @Column(name = "salary_per_week", precision = 19, scale = 6)
  private BigDecimal salaryPerWeek;

  @Column(precision = 19, scale = 6)
  private BigDecimal bonuses;

  @Column(name = "food_vouchers", precision = 19, scale = 6)
  private BigDecimal foodVouchers;

  public BigDecimal getSalaryPerWeek() {
    return salaryPerWeek;
  }

  public void setSalaryPerWeek(BigDecimal salaryPerWeek) {
    this.salaryPerWeek = salaryPerWeek;
  }

  public BigDecimal getBonuses() {
    return bonuses;
  }

  public void setBonuses(BigDecimal bonuses) {
    this.bonuses = bonuses;
  }

  public BigDecimal getFoodVouchers() {
    return foodVouchers;
  }

  public void setFoodVouchers(BigDecimal foodVouchers) {
    this.foodVouchers = foodVouchers;
  }
}
