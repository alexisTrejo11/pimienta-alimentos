package io.github.alexistrejo11.pimienta.module.payroll.core.application.command;

import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollAdjustmentType;
import java.math.BigDecimal;

public record RegisterPayrollAdjustmentCommand(
    Long payrollRecordId, PayrollAdjustmentType type, BigDecimal amount, String reason) {}
