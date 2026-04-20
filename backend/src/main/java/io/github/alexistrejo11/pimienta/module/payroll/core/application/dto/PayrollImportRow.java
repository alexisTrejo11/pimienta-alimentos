package io.github.alexistrejo11.pimienta.module.payroll.core.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayrollImportRow(
    int excelRowNumber,
    Long id,
    Long employeeId,
    Long periodId,
    LocalDate workedDaysStart,
    LocalDate workedDaysEnd,
    BigDecimal grossAmount,
    BigDecimal totalDiscounts,
    BigDecimal totalBonuses,
    BigDecimal netAmount,
    String statusRaw) {}
