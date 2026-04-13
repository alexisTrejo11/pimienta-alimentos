package io.github.alexistrejo11.pimienta.module.task.core.domain;

import java.time.LocalDateTime;
import java.util.List;

/** Carga desde persistencia sin re-ejecutar reglas de alta. */
public record ReconstructTaskParams(
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
    List<Task.ChecklistItem> checklist,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {}
