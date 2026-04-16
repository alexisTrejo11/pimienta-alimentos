package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record WinOpportunityRequest(
    @NotBlank String projectCode,
    @NotBlank String projectName,
    String description,
    @NotNull Long clientId,
    @NotNull Project.ProjectType type,
    @NotNull Project.ProjectPriority priority,
    Long projectManagerId,
    Long assignedSalesmanId,
    LocalDate plannedStartDate,
    LocalDate plannedEndDate,
    @NotNull BigDecimal contractedValue,
    @NotNull BigDecimal estimatedCost) {}
