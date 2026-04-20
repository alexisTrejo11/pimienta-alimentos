package io.github.alexistrejo11.pimienta.module.payroll.core.application.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RegisterPayrollRecordCommand(
    Long employeeId, Long periodId, LocalDate workedDaysStart, LocalDate workedDaysEnd, BigDecimal grossAmount) {}
