package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ImssSalaryType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ImssWorkerType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.WorkShift;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeResponse(
    Long id,
    String name,
    String email,
    String phone,
    String address,
    LocalDate birthDate,
    String nationality,
    String curp,
    String rfc,
    String nss,
    String clabe,
    String employeeNumber,
    String position,
    String department,
    ContractType contractType,
    WorkShift workShift,
    LocalDate hireDate,
    LocalDate terminationDate,
    EmployeeStatus status,
    BigDecimal salaryPerWeek,
    BigDecimal bonuses,
    BigDecimal foodVouchers,
    BigDecimal integrationFactor,
    ImssWorkerType imssWorkerType,
    ImssSalaryType imssSalaryType,
    int christmasBonusDays,
    int vacationDays,
    BigDecimal vacationPremiumPercent,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
