package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateProjectRequest(
    String projectName,
    String description,
    Project.ProjectType type,
    Project.ProjectPriority priority,
    Long projectManagerId,
    Long assignedSalesmanId,
    LocalDate plannedStartDate,
    LocalDate plannedEndDate,
    BigDecimal contractedValue,
    BigDecimal estimatedCost,
    Integer progressPercent) {}
