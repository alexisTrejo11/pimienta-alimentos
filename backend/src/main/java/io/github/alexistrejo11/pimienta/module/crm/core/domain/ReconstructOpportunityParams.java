package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Carga desde persistencia sin re-ejecutar reglas de alta. */
public record ReconstructOpportunityParams(
    Long id,
    String contactName,
    String contactEmail,
    String contactPhone,
    String companyName,
    String companyLocation,
    String industry,
    String title,
    String description,
    BigDecimal estimatedValue,
    int probabilityPercent,
    Opportunity.OpportunitySource source,
    Opportunity.OpportunityStatus status,
    LocalDate expectedCloseDate,
    LocalDate actualCloseDate,
    Long assignedSalesmanId,
    String lostReason,
    Long convertedProjectId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {}
