package io.github.alexistrejo11.pimienta.config.health.doc;

import io.github.alexistrejo11.pimienta.config.health.dto.HealthResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocPublicEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code GET /api/v2/health} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocPublicEndpoint
@Operation(
    summary = "Liveness check",
    description =
        "Returns HTTP 200 when the API process is running. Does not validate downstream "
            + "dependencies (database, Redis). No Bearer token required.")
@ApiResponse(
    responseCode = "200",
    description = "Service is up.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = HealthResponse.class)))
public @interface DocHealthGet {}
