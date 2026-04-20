package io.github.alexistrejo11.pimienta.module.payroll.core.application.command;

import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollFrequency;
import java.time.LocalDate;

public record RegisterPayrollPeriodCommand(
    PayrollFrequency frequency, LocalDate startDate, LocalDate endDate) {}
