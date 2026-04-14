package io.github.alexistrejo11.pimienta.module.crm.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Carga desde persistencia sin re-ejecutar reglas de alta. */
public record ReconstructProjectMilestoneParams(
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
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {}
