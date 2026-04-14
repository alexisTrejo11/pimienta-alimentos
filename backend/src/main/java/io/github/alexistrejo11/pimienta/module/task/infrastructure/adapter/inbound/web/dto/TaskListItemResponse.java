package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import java.time.LocalDateTime;

public record TaskListItemResponse(
    Long id,
    String title,
    Task.Status status,
    Task.Priority priority,
    Long assignedToId,
    LocalDateTime dueDate,
    Long headquarterId,
    Long projectId,
    Long opportunityId,
    double progressPercent) {}
