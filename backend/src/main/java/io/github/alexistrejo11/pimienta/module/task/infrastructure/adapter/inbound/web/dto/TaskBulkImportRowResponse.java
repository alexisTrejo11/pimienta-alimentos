package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TaskBulkImportRowResponse", description = "One import error row.")
public record TaskBulkImportRowResponse(
    @Schema(description = "1-based Excel row number.", example = "12")
    int excelRowNumber,
    @Schema(description = "Error message.", example = "Estado no válido: X")
    String message) {}
