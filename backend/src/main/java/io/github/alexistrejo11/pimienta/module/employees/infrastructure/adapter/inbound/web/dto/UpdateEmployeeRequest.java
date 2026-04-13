package io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.WorkShift;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UpdateEmployeeRequest(
    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank String phone,
    @NotBlank String address,
    @NotBlank String curp,
    @NotBlank String rfc,
    @NotBlank String nss,
    @NotBlank String clabe,
    @NotBlank String position,
    @NotBlank String department,
    @NotNull ContractType contractType,
    @NotNull WorkShift workShift,
    @NotNull BigDecimal salaryPerWeek,
    @NotNull BigDecimal bonuses,
    @NotNull BigDecimal foodVouchers,
    @NotNull BigDecimal integrationFactor) {}
