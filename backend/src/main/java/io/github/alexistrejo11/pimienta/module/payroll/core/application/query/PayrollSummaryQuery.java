package io.github.alexistrejo11.pimienta.module.payroll.core.application.query;

import java.time.LocalDate;

public record PayrollSummaryQuery(LocalDate from, LocalDate to) {}
