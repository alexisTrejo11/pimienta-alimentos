package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateProjectMilestoneRequest(
    String name,
    String description,
    LocalDate plannedDate,
    BigDecimal billingAmount,
    Integer sortOrder) {}
