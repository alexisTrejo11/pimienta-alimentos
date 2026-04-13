package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import java.time.LocalDate;

/**
 * Datos laborales y ciclo de vida del vínculo laboral.
 */
public record Employment(
    String position,
    String department,
    ContractType contractType,
    WorkShift workShift,
    LocalDate hireDate,
    LocalDate terminationDate,
    EmployeeStatus status) {

  public Employment {
    position = position != null ? position.trim() : "";
    department = department != null ? department.trim() : "";
    contractType = contractType != null ? contractType : ContractType.INDEFINITE;
    workShift = workShift != null ? workShift : WorkShift.MORNING;
    status = status != null ? status : EmployeeStatus.ACTIVE;
  }

  public static Employment hired(
      String position,
      String department,
      ContractType contractType,
      WorkShift workShift,
      LocalDate hireDate) {
    return new Employment(
        position,
        department,
        contractType,
        workShift,
        hireDate,
        null,
        EmployeeStatus.ACTIVE);
  }

  public Employment withTermination(EmployeeStatus newStatus, LocalDate termination) {
    return new Employment(
        position, department, contractType, workShift, hireDate, termination, newStatus);
  }

  public Employment withStatus(EmployeeStatus newStatus) {
    return new Employment(
        position, department, contractType, workShift, hireDate, terminationDate, newStatus);
  }
}
