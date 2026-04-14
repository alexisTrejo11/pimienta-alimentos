package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectResponse(
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
    LocalDateTime updatedAt) {}
