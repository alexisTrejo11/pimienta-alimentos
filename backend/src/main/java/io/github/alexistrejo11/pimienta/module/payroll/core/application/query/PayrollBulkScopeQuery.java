package io.github.alexistrejo11.pimienta.module.payroll.core.application.query;

import java.time.LocalDate;

public record PayrollBulkScopeQuery(
    Long employeeId,
    Long periodId,
    LocalDate workedFrom,
    LocalDate workedTo) {}
