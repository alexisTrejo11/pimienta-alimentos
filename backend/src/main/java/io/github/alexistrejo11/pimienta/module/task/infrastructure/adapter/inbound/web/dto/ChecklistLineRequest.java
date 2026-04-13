package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ChecklistLineRequest(@NotBlank String description, int displayOrder) {}
