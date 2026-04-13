package io.github.alexistrejo11.pimienta.shared.web;

import org.springframework.data.domain.Page;

/** Pagination envelope for JSON APIs (Spring {@link Page} → stable wire shape). */
public record PageMetadata(
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages,
    boolean first,
    boolean last,
    boolean hasNext,
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
