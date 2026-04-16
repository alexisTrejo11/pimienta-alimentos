package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.WorkShift;

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

  public static Employment hired(EmploymentHiredParams params) {
    return new Employment(
        params.position(),
        params.department(),
        params.contractType(),
        params.workShift(),
        params.hireDate(),
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
