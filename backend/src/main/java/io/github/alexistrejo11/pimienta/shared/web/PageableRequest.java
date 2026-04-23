package io.github.alexistrejo11.pimienta.shared.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Reusable query-parameter carrier for paginated endpoints.
 *
 * <p>Bind via {@code @ModelAttribute} on any controller method. Search-specific filter classes
 * extend this class to inherit pagination without repeating the fields.
 *
 * <pre>
 *   &#64;GetMapping
 *   public PagedResponse&lt;FooResponse&gt; list(&#64;ModelAttribute PageableRequest pageable) { ... }
 * </pre>
 */
@Schema(description = "Parámetros de paginación estándar (`page`, `size`).")
public class PageableRequest {

  @Min(0)
  @Schema(
      description = "Índice de página basado en cero.",
      example = "0",
      defaultValue = "0",
      minimum = "0")
  private int page = 0;

  @Min(1)
  @Max(100)
  @Schema(
      description = "Tamaño de página (máximo 100).",
      example = "20",
      defaultValue = "20",
      minimum = "1",
      maximum = "100")
  private int size = 20;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Pageable toPageable() {
    return PageRequest.of(page, size);
  }
}
