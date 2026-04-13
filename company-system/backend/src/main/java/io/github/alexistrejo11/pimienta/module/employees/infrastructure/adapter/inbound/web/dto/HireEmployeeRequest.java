package io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.WorkShift;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record HireEmployeeRequest(
    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank String phone,
    @NotBlank String address,
    @NotBlank String curp,
    @NotBlank String rfc,
    @NotBlank String nss,
    @NotBlank String clabe,
    @NotBlank String employeeNumber,
    @NotBlank String position,
    @NotBlank String department,
    @NotNull ContractType contractType,
    @NotNull WorkShift workShift,
    @NotNull @Positive BigDecimal salaryPerWeek,
    @NotNull LocalDate birthDate) {}
