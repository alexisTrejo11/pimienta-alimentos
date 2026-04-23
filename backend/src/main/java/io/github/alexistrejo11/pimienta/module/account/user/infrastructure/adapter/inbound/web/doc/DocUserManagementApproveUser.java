package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code POST /api/v1/users/management/{id}/approve} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Approve pending user",
    description =
        """
        Activates a previously pending account. **204** on success. Rate limit: **SENSITIVE_OPERATIONS**. \
        Typically **ADMIN** / **MANAGER**.""")
@ApiResponse(responseCode = "204", description = "User approved; no response body.")
public @interface DocUserManagementApproveUser {}
