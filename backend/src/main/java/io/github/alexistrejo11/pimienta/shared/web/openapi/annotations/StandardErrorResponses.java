package io.github.alexistrejo11.pimienta.shared.web.openapi.annotations;

import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Common error shapes for HTTP APIs: {@link ApiErrorResponse} with {@code errorCode}, {@code
 * message}, {@code traceId}, optional {@code context} / {@code fieldErrors}.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponses({
  @ApiResponse(
      responseCode = "400",
      description =
          "Bad request: validation, malformed JSON, or constraint violation. "
              + "See `errorCode` (e.g. VALIDATION_FAILED, CONSTRAINT_VIOLATION).",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiErrorResponse.class))),
  @ApiResponse(
      responseCode = "401",
      description =
          "Unauthorized: missing/invalid Bearer token, bad credentials, or expired access token.",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiErrorResponse.class))),
  @ApiResponse(
      responseCode = "403",
      description = "Forbidden: authenticated but not allowed for this resource.",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiErrorResponse.class))),
  @ApiResponse(
      responseCode = "429",
      description =
          "Too many requests (rate limit). Check `Retry-After` and `X-RateLimit-*` response headers.",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiErrorResponse.class))),
  @ApiResponse(
      responseCode = "500",
      description = "Unexpected server error.",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiErrorResponse.class)))
})
public @interface StandardErrorResponses {}
