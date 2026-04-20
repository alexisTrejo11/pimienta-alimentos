package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response;

import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollFrequency;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PayrollPeriodResponse(
    Long id, PayrollFrequency frequency, LocalDate startDate, LocalDate endDate, String status) {}
