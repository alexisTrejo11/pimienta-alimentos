package io.github.alexistrejo11.pimienta.module.task.core.application.dto;

import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;

/**
 * Fila para generar el Excel de exportación (plantilla o reporte).
 */
public record TaskExportRow(
    Long id,
    String title,
    Task.Status status,
    /** Texto multilínea para la celda Checklist (mismas líneas que ítems). */
    String checklistCellText,
    /** Nombre legible de sede; vacío si no aplica. */
    String headquarterName) {}
