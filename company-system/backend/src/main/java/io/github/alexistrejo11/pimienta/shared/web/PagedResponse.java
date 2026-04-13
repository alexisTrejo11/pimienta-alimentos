package io.github.alexistrejo11.pimienta.shared.web;

import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;

/**
 * Generic list payload: {@code items} plus {@link PageMetadata}; use for any endpoint returning a
 * page of entities.
 */
public record PagedResponse<T>(List<T> items, PageMetadata metadata) {

  public static <S, T> PagedResponse<T> map(Page<S> page, Function<S, T> mapper) {
    List<T> items = page.getContent().stream().map(mapper).toList();
    return new PagedResponse<>(items, PageMetadata.fromPage(page));
  }

  public static <T> PagedResponse<T> of(Page<T> page) {
    return new PagedResponse<>(page.getContent(), PageMetadata.fromPage(page));
  }
}
