package io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto;

import java.util.List;

public record EmployeeSummaryResponse(long totalNotDeleted, List<DepartmentHeadcountResponse> headcountByDepartment) {}
