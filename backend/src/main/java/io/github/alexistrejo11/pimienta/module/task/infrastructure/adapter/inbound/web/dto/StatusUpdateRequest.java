package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "StatusUpdateRequest", description = "New status for PATCH status.")
public record StatusUpdateRequest(
    @NotNull
        @Schema(description = "Target workflow status.", example = "COMPLETED", requiredMode = Schema.RequiredMode.REQUIRED)
    Task.Status status) {}
