package io.github.alexistrejo11.pimienta.module.employees.core.domain;

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
