package io.github.alexistrejo11.pimienta.shared.web.openapi.doc;

import io.github.alexistrejo11.pimienta.shared.web.openapi.annotations.StandardErrorResponses;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Standard error responses for unauthenticated HTTP APIs (no Bearer scheme). Compose into per-endpoint
 * {@code Doc*} types for auth/session routes.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@StandardErrorResponses
public @interface DocPublicEndpoint {}
