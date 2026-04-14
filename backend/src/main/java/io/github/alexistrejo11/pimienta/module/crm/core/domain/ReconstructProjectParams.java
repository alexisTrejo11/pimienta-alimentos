package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Carga desde persistencia sin re-ejecutar reglas de alta. */
public record ReconstructProjectParams(
    Long id,
    Long clientId,
    Long originOpportunityId,
    String projectCode,
    String projectName,
    String description,
    Project.ProjectType type,
    Project.ProjectStatus status,
    Project.ProjectPriority priority,
    Long projectManagerId,
    Long assignedSalesmanId,
    LocalDate plannedStartDate,
    LocalDate plannedEndDate,
    LocalDate actualStartDate,
    LocalDate actualEndDate,
    String onHoldReason,
    BigDecimal contractedValue,
    BigDecimal estimatedCost,
    BigDecimal actualCost,
    int progressPercent,
    String cancellationReason,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {}
