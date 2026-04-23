package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "ChecklistLineRequest", description = "One checklist line on create.")
public record ChecklistLineRequest(
    @NotBlank
        @Schema(description = "Line text.", example = "Order samples", requiredMode = Schema.RequiredMode.REQUIRED)
    String description,
    @Schema(description = "Stable order key for toggle API.", example = "0", minimum = "0")
    int displayOrder) {}
