package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.inbound.web.dto;

public record ProjectSummaryResponse(
    ProjectResponse project,
    long milestoneCount,
    long milestoneCompletedCount,
    long taskCount,
    boolean overdue) {}
