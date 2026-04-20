package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request;

import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollAdjustmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(name = "RegisterPayrollAdjustmentRequest", description = "Discount or bonus to apply to a payroll record.")
public record RegisterPayrollAdjustmentRequest(
    @NotNull @Schema(description = "Adjustment type.", example = "DISCOUNT")
    PayrollAdjustmentType type,
    @NotNull @Positive @Schema(description = "Adjustment amount.", example = "150.00")
    BigDecimal amount,
    @NotBlank @Schema(description = "Reason.", example = "Attendance bonus")
    String reason) {}
