package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import java.util.List;

/** Aggregated view for management dashboards (read model). */
public record EmployeeSummary(long totalNotDeleted, List<DepartmentHeadcount> headcountByDepartment) {}
