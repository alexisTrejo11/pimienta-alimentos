package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateProjectMilestoneRequest(
    @NotBlank String name,
    String description,
    LocalDate plannedDate,
    @NotNull BigDecimal billingAmount,
    int sortOrder) {}
