package io.github.alexistrejo11.pimienta.module.crm.core.application;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import java.math.BigDecimal;

public record OpportunitySummary(
    Opportunity opportunity,
    long taskCount,
    long openTaskCount,
    BigDecimal weightedValue,
    boolean overdue) {}
