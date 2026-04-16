package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.WorkShift;
import java.time.LocalDate;

public record EmploymentHiredParams(
    String position,
    String department,
    ContractType contractType,
    WorkShift workShift,
    LocalDate hireDate) {}
