package io.github.alexistrejo11.pimienta.module.crm.core.application;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateProjectMilestoneParams(
    String name, String description, LocalDate plannedDate, BigDecimal billingAmount, Integer sortOrder) {}
