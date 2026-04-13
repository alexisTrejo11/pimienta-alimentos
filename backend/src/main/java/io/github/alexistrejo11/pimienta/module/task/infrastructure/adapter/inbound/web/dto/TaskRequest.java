package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record TaskRequest(
    @NotBlank String title,
    String description,
    @NotNull Task.Priority priority,
    LocalDateTime dueDate,
    Long headquarterId,
    Long projectId,
    Long createdById,
    @Valid List<ChecklistLineRequest> checklist) {}
