package io.github.alexistrejo11.pimienta.module.employees.core.domain.dto;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.WorkShift;

import java.math.BigDecimal;

/** Parámetros para actualización parcial tipo ficha ({@link Employee#update(UpdateEmployeeParams)}). */
public record UpdateEmployeeParams(
    Long id,
    String name,
    String email,
    String phone,
    String address,
    String curp,
    String rfc,
    String nss,
    String clabe,
    String position,
    String department,
    ContractType contractType,
    WorkShift workShift,
    BigDecimal salaryPerWeek,
    BigDecimal bonuses,
    BigDecimal foodVouchers,
    BigDecimal integrationFactor) {}
