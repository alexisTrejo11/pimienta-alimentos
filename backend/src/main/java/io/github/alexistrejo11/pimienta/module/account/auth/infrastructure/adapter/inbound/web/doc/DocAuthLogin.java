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

/** {@code POST /api/v1/auth/login} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocPublicEndpoint
@Operation(
    summary = "Sign in",
    description =
        """
        Authenticates with email and password and returns **access** and **refresh** tokens. \
        Common `errorCode` values: `AUTHENTICATION_FAILED`, `USER_BANNED`, `ACCOUNT_PENDING_APPROVAL`. \
        Rate limit: **STRICT**.""")
@ApiResponse(
    responseCode = "200",
    description = "Issued access and refresh tokens.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TokenResponse.class)))
public @interface DocAuthLogin {}
