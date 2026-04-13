package io.github.alexistrejo11.pimienta.module.employees.core.domain;

/** Counts by coarse lifecycle buckets for HR overview (non-deleted rows only). */
public record EmployeeStatistics(long total, long active, long notActive) {}
