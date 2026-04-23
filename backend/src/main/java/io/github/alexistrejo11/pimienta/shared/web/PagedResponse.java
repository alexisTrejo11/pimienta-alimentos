package io.github.alexistrejo11.pimienta.shared.web;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;

/**
 * Generic list payload: {@code items} plus {@link PageMetadata}; use for any endpoint returning a
 * page of entities.
 */
@Schema(
    description =
        """
        Respuesta paginada genérica: lista `items` y objeto `metadata` con totales y banderas de \
        navegación. El esquema de cada elemento depende del endpoint (p. ej. ContractResponse).""")
public record PagedResponse<T>(
    @Schema(description = "Elementos de la página actual.") List<T> items,
    @Schema(description = "Metadatos de paginación.") PageMetadata metadata) {

  public static <S, T> PagedResponse<T> map(Page<S> page, Function<S, T> mapper) {
    List<T> items = page.getContent().stream().map(mapper).toList();
    return new PagedResponse<>(items, PageMetadata.fromPage(page));
  }

  public static <T> PagedResponse<T> of(Page<T> page) {
    return new PagedResponse<>(page.getContent(), PageMetadata.fromPage(page));
  }
}
