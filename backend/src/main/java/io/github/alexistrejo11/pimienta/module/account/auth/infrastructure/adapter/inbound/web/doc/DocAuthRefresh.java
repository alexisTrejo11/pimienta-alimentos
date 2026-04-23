package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.dto.TokenResponse;
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

/** {@code POST /api/v1/auth/refresh} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocPublicEndpoint
@Operation(
    summary = "Refresh access token",
    description =
        """
        Issues a new access token (and refresh token when rotated) using the **refresh token** in \
        the body. Fails with `INVALID_REFRESH_TOKEN` when the refresh token is unknown or expired. \
        Rate limit: **AUTH_SESSION**.""")
@ApiResponse(
    responseCode = "200",
    description = "Newly issued tokens.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TokenResponse.class)))
public @interface DocAuthRefresh {}
