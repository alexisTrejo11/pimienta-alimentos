package io.github.alexistrejo11.pimienta.shared.web;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

/** Pagination envelope for JSON APIs (Spring {@link Page} → stable wire shape). */
@Schema(
    name = "PageMetadata",
    description = "Metadatos de paginación (número de página, totales, banderas first/last).")
public record PageMetadata(
    @Schema(description = "Índice de página actual (0-based).", example = "0", minimum = "0")
    int pageNumber,
    @Schema(description = "Tamaño de página solicitado.", example = "20", minimum = "1")
    int pageSize,
    @Schema(description = "Total de elementos en todas las páginas.", example = "150", minimum = "0")
    long totalElements,
    @Schema(description = "Total de páginas.", example = "8", minimum = "0")
    int totalPages,
    @Schema(description = "Si es la primera página.", example = "true")
    boolean first,
    @Schema(description = "Si es la última página.", example = "false")
    boolean last,
    @Schema(description = "Si existe página siguiente.", example = "true")
    boolean hasNext,
    @Schema(description = "Si existe página anterior.", example = "false")
    boolean hasPrevious) {

  public static PageMetadata fromPage(Page<?> page) {
    return new PageMetadata(
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isFirst(),
        page.isLast(),
        page.hasNext(),
        page.hasPrevious());
  }
}
