package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "AssignTaskRequest", description = "Assignee for PATCH assign.")
public record AssignTaskRequest(
    @NotNull
        @Schema(description = "Employee id to assign the task to.", example = "42", requiredMode = Schema.RequiredMode.REQUIRED)
    Long employeeId) {}
