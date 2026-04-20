package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request;

import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollFrequency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(name = "RegisterPayrollPeriodRequest", description = "Payload to create a payroll period.")
public record RegisterPayrollPeriodRequest(
    @NotNull @Schema(description = "Payroll frequency.", example = "WEEKLY")
    PayrollFrequency frequency,
    @NotNull
        @Schema(description = "Period start date.", type = "string", format = "date", example = "2026-04-01")
    LocalDate startDate,
    @NotNull
        @Schema(description = "Period end date.", type = "string", format = "date", example = "2026-04-07")
    LocalDate endDate) {}
