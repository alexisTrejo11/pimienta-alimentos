package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import java.math.BigDecimal;

public record OpportunitySummaryResponse(
    OpportunityResponse opportunity,
    long taskCount,
    long openTaskCount,
    BigDecimal weightedValue,
    boolean overdue) {}
