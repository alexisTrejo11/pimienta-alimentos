package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import java.time.LocalDateTime;
import java.util.List;

public record TaskResponse(
    Long id,
    String title,
    String description,
    Task.Status status,
    Task.Priority priority,
    Long assignedToId,
    Long assignedById,
    Long createdById,
    LocalDateTime assignedAt,
    LocalDateTime completedAt,
    LocalDateTime dueDate,
    Long headquarterId,
    Long projectId,
    Long opportunityId,
    List<ChecklistItemResponse> checklist,
    double progressPercent,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
