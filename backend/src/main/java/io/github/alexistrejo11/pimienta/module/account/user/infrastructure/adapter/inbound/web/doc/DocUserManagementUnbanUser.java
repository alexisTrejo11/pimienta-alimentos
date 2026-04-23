package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code POST /api/v1/users/management/{id}/unban} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Unban user",
    description =
        """
        Clears ban state. **204** on success. Rate limit: **SENSITIVE_OPERATIONS**. Typically \
        **ADMIN** / **MANAGER**.""")
@ApiResponse(responseCode = "204", description = "User unbanned; no response body.")
public @interface DocUserManagementUnbanUser {}
