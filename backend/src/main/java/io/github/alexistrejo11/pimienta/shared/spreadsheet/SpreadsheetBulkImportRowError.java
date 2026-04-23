package io.github.alexistrejo11.pimienta.shared.spreadsheet;

import io.swagger.v3.oas.annotations.media.Schema;

/** Error de fila en importación masiva (compartido entre módulos). */
@Schema(
    name = "SpreadsheetBulkImportRowError",
    description = "Detalle de error por fila del Excel (número de fila y mensaje legible).")
public record SpreadsheetBulkImportRowError(
    @Schema(description = "Número de fila en la hoja (1-based, típicamente como en Excel).", example = "7")
    int excelRowNumber,
    @Schema(description = "Motivo del fallo.", example = "El nombre es obligatorio")
    String message) {}
