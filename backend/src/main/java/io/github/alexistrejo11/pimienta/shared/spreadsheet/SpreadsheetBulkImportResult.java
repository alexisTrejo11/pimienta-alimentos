package io.github.alexistrejo11.pimienta.shared.spreadsheet;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(
    name = "SpreadsheetBulkImportResult",
    description =
        """
        Resultado de importación masiva: contadores de filas actualizadas, creadas, omitidas y \
        lista de errores por fila.""")
public record SpreadsheetBulkImportResult(
    @Schema(description = "Filas actualizadas (match por id u otra regla del módulo).", example = "5")
    int updated,
    @Schema(description = "Filas insertadas como nuevos registros.", example = "12")
    int created,
    @Schema(description = "Filas ignoradas (sin efecto, p. ej. sin datos mínimos).", example = "1")
    int skipped,
    @Schema(description = "Errores por fila; vacío si todo OK.")
    List<SpreadsheetBulkImportRowError> errors) {}
