package io.github.alexistrejo11.pimienta.module.crm.core.application.command;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import java.math.BigDecimal;
import java.time.LocalDate;

public record WinOpportunityParams(
    String projectCode,
    String projectName,
    String description,
    Long clientId,
    Project.ProjectType type,
    Project.ProjectPriority priority,
    Long projectManagerId,
    Long assignedSalesmanId,
    LocalDate plannedStartDate,
    LocalDate plannedEndDate,
    BigDecimal contractedValue,
    BigDecimal estimatedCost) {}
