package io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatus;
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
