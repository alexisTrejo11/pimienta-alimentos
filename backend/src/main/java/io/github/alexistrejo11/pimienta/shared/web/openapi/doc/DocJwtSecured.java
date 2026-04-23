package io.github.alexistrejo11.pimienta.shared.web.openapi.doc;

import io.github.alexistrejo11.pimienta.shared.web.openapi.annotations.OpenApiSecuritySchemes;
import io.github.alexistrejo11.pimienta.shared.web.openapi.annotations.StandardErrorResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Composes Bearer JWT security with {@link StandardErrorResponses}. Use as a meta-annotation on
 * per-endpoint {@code Doc*} types (not on controllers directly).
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SecurityRequirement(name = OpenApiSecuritySchemes.BEARER_JWT)
@StandardErrorResponses
public @interface DocJwtSecured {}
