package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocPublicEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code POST /api/v1/auth/logout} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocPublicEndpoint
@Operation(
    summary = "Sign out",
    description =
        """
        Revokes the refresh session (best-effort; idempotent). Optional body may carry the refresh \
        token to remove from the server store. Rate limit: **AUTH_SESSION**.""")
@ApiResponse(responseCode = "204", description = "Session revoked; no response body.")
public @interface DocAuthLogout {}
