package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import java.time.LocalDateTime;

public record ChecklistItemResponse(
    String description,
    boolean completed,
    LocalDateTime completedAt,
    int displayOrder) {}
