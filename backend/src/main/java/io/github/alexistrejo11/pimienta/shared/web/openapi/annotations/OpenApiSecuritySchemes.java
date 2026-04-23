package io.github.alexistrejo11.pimienta.shared.web.openapi.annotations;

/**
 * OpenAPI security scheme names registered in {@code OpenApiConfig}. Must match {@code
 * Components.addSecuritySchemes} keys.
 */
public final class OpenApiSecuritySchemes {

  public static final String BEARER_JWT = "bearer-jwt";

  private OpenApiSecuritySchemes() {}
}
