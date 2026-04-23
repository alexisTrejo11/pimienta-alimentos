package io.github.alexistrejo11.pimienta.module.employees.core.application.dto;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.WorkShift;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Parámetros para alta de empleado ({@link Employee#hire(HireEmployeeParams)}).
 */
public record HireEmployeeParams(
    String name,
    String email,
    String phone,
    String address,
    String curp,
    String rfc,
    String nss,
    String clabe,
    String employeeNumber,
    String position,
    String department,
    ContractType contractType,
    WorkShift workShift,
    BigDecimal salaryPerWeek,
    LocalDate birthDate) {
}
