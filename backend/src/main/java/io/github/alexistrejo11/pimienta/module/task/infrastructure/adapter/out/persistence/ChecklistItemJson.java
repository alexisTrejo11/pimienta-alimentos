package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.persistence;

import java.time.LocalDateTime;

/** Forma serializable del checklist para columna JSONB (misma forma que el dominio). */
public record ChecklistItemJson(
    String description,
    boolean isCompleted,
    LocalDateTime completedAt,
    int displayOrder) {}
