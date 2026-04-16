package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateOpportunityRequest(
    String title,
    String description,
    String contactName,
    String contactEmail,
    String contactPhone,
    String companyName,
    String companyLocation,
    String industry,
    BigDecimal estimatedValue,
    Integer probabilityPercent,
    Opportunity.OpportunitySource source,
    LocalDate expectedCloseDate,
    Long assignedSalesmanId) {}
