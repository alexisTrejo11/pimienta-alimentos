package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeOnboardingPhase;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.WorkShift;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/** POST /api/v1/employees — alta en onboarding (borrador o pendiente de contrato). */
@Schema(
    name = "RegisterEmployeeRequest",
    description =
        "Payload to register a new employee in onboarding (draft or pending employment contract).")
public record RegisterEmployeeRequest(
    @NotBlank @Schema(description = "Full legal name.", example = "María López García")
    String name,
    @NotBlank
        @Email
        @Schema(description = "Work email address.", example = "maria.lopez@empresa.example")
    String email,
    @NotBlank @Schema(description = "Contact phone number.", example = "+52 55 1234 5678")
    String phone,
    @NotBlank @Schema(description = "Street address.", example = "Av. Reforma 123, CDMX")
    String address,
    @NotBlank
        @Schema(
            description = "CURP (18 characters).",
            example = "XXXX850101HDFXXX09",
            minLength = 18,
            maxLength = 18)
    String curp,
    @NotBlank
        @Schema(
            description = "RFC (12 or 13 characters).",
            example = "XAXX010101000",
            minLength = 12,
            maxLength = 13)
    String rfc,
    @NotBlank @Schema(description = "IMSS NSS (11 digits).", example = "12345678901")
    String nss,
    @NotBlank
        @Schema(
            description = "18-digit CLABE for payroll deposits.",
            example = "012180001234567890")
    String clabe,
    @NotBlank @Schema(description = "Internal employee number.", example = "EMP-1042")
    String employeeNumber,
    @NotBlank @Schema(description = "Job title or position.", example = "Operador de línea")
    String position,
    @NotBlank @Schema(description = "Department or area.", example = "Producción")
    String department,
    @NotNull @Schema(description = "Employment contract type.") ContractType contractType,
    @NotNull @Schema(description = "Work shift.") WorkShift workShift,
    @NotNull
        @Positive
        @Schema(
            description = "Gross weekly salary in MXN.",
            example = "3500.00",
            minimum = "0",
            exclusiveMinimum = true)
    BigDecimal salaryPerWeek,
    @NotNull @Schema(description = "Date of birth (ISO-8601 date).", example = "1985-01-01", type = "string", format = "date")
    LocalDate birthDate,
    @NotNull
        @Schema(
            description =
                "Onboarding phase: DRAFT (still being captured) or PENDING_CONTRACT (awaiting contract).")
    EmployeeOnboardingPhase onboardingPhase) {}
