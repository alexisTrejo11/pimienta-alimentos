package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto.FileAssetResponse;
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

/** {@code GET /api/v1/files/management/{id}} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Get file asset by id (admin)",
    description =
        "Returns the full catalog record for a single file asset. Does not return the file bytes; "
            + "use the download-url endpoint to obtain a pre-signed S3 link. **ROLE_ADMIN** only.")
@Parameter(name = "id", in = ParameterIn.PATH, description = "File asset UUID.", required = true)
@ApiResponse(
    responseCode = "200",
    description = "File asset.",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileAssetResponse.class)))
@ApiResponse(
    responseCode = "404",
    description = "Asset not found.",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocFileManagementGetById {}
