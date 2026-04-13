package io.github.alexistrejo11.pimienta.module.task.core.domain;

import java.time.LocalDateTime;
import java.util.List;

public record CreateTaskParams(
    String title,
    String description,
    Task.Priority priority,
    LocalDateTime dueDate,
    Long headquarterId,
    Long projectId,
    Long createdById,
    List<CreateChecklistLine> checklistLines) {}
