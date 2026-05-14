package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import java.util.List;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.valueobject.DepartmentHeadcount;

/** Aggregated view for management dashboards (read model). */
public record EmployeeSummary(long totalNotDeleted, List<DepartmentHeadcount> headcountByDepartment) {
}
