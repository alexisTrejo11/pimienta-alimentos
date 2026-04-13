package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotNull;

public record AssignTaskRequest(@NotNull Long employeeId) {}
