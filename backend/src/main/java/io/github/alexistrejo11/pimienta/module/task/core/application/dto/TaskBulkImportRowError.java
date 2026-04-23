package io.github.alexistrejo11.pimienta.module.task.core.application.dto;

public record TaskBulkImportRowError(int excelRowNumber, String message) {}
