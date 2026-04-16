package io.github.alexistrejo11.pimienta.module.crm.core.application.summary;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;

public record ProjectSummary(
    Project project,
    long milestoneCount,
    long milestoneCompletedCount,
    long taskCount,
    boolean overdue) {}
