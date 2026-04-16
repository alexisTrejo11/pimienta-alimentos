package io.github.alexistrejo11.pimienta.shared.web;

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
public class PageableRequest {

  @Min(0)
  private int page = 0;

  @Min(1)
  @Max(100)
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
