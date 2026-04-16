package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateOpportunityRequest(
    @NotBlank String title,
    String description,
    @NotBlank String contactName,
    @NotBlank String contactEmail,
    String contactPhone,
    @NotBlank String companyName,
    String companyLocation,
    String industry,
    @NotNull BigDecimal estimatedValue,
    int probabilityPercent,
    @NotNull Opportunity.OpportunitySource source,
    LocalDate expectedCloseDate,
    Long assignedSalesmanId) {}
