package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectMilestoneResponse(
    Long id,
    Long projectId,
    String name,
    String description,
    ProjectMilestone.MilestoneStatus status,
    LocalDate plannedDate,
    LocalDate actualDate,
    BigDecimal billingAmount,
    boolean billed,
    int sortOrder,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
