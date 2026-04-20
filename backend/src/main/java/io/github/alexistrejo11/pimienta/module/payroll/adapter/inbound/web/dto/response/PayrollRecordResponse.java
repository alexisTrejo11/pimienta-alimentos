package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response;

import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollRecordStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PayrollRecordResponse(
    Long id,
    Long employeeId,
    Long periodId,
    LocalDate workedDaysStart,
    LocalDate workedDaysEnd,
    BigDecimal grossAmount,
    BigDecimal totalDiscounts,
    BigDecimal totalBonuses,
    BigDecimal netAmount,
    PayrollRecordStatus status) {}
