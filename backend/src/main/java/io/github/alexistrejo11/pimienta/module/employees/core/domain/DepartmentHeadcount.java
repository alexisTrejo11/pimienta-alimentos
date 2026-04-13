package io.github.alexistrejo11.pimienta.module.employees.core.domain;

/** Headcount grouped by department (read model for summaries). */
public record DepartmentHeadcount(String department, long headcount) {}
