package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code DELETE /api/v1/files/management/{id}} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Delete file asset (admin)",
    description =
        "Soft-deletes the catalog entry and removes the object from S3. **ROLE_ADMIN** only. Rate limit: **SENSITIVE_OPERATIONS**.")
@Parameter(name = "id", in = ParameterIn.PATH, description = "File asset UUID.", required = true)
@ApiResponse(responseCode = "204", description = "Deleted.")
@ApiResponse(
    responseCode = "404",
    description = "Asset not found.",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocFileManagementDelete {}
