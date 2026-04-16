package io.github.alexistrejo11.pimienta.module.crm.core.application.command;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateOpportunityParams(
    String title,
    String description,
    String contactName,
    String contactEmail,
    String contactPhone,
    String companyName,
    String companyLocation,
    String industry,
    BigDecimal estimatedValue,
    int probabilityPercent,
    Opportunity.OpportunitySource source,
    LocalDate expectedCloseDate,
    Long assignedSalesmanId) {}
