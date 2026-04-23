package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "ChecklistItemResponse", description = "Checklist line in task responses.")
public record ChecklistItemResponse(
    @Schema(example = "Order samples")
    String description,
    @Schema(example = "false")
    boolean completed,
    @Schema(type = "string", format = "date-time")
    LocalDateTime completedAt,
    @Schema(example = "0")
    int displayOrder) {}
