package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.WorkShift;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Embeddable
public class EmployeeEmploymentEmbeddable {

  @Column(length = 120)
  private String position;

  @Column(length = 120)
  private String department;

  @Enumerated(EnumType.STRING)
  @Column(name = "contract_type", length = 32)
  private ContractType contractType;

  @Enumerated(EnumType.STRING)
  @Column(name = "work_shift", length = 32)
  private WorkShift workShift;

  @Column(name = "hire_date")
  private LocalDate hireDate;

  @Column(name = "termination_date")
  private LocalDate terminationDate;

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public ContractType getContractType() {
    return contractType;
  }

  public void setContractType(ContractType contractType) {
    this.contractType = contractType;
  }

  public WorkShift getWorkShift() {
    return workShift;
  }

  public void setWorkShift(WorkShift workShift) {
    this.workShift = workShift;
  }

  public LocalDate getHireDate() {
    return hireDate;
  }

  public void setHireDate(LocalDate hireDate) {
    this.hireDate = hireDate;
  }

  public LocalDate getTerminationDate() {
    return terminationDate;
  }

  public void setTerminationDate(LocalDate terminationDate) {
    this.terminationDate = terminationDate;
  }
}
