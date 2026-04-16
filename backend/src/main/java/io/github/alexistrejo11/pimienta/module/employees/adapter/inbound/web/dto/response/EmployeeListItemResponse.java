package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import java.time.LocalDate;

/** Paginated listing projection (lighter than {@link EmployeeResponse}). */
public record EmployeeListItemResponse(
    Long id,
    String name,
    String email,
    String department,
    String position,
    EmployeeStatus status,
    LocalDate hireDate) {}
