package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto.FileDownloadUrlResponse;
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

/** {@code GET /api/v1/files/resources/{id}/download-url} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Generate resource download URL",
    description = "Returns a 24-hour pre-signed URL. **ROLE_ADMIN** or **ROLE_MANAGER**.")
@Parameter(name = "id", in = ParameterIn.PATH, description = "File asset UUID.", required = true)
@ApiResponse(
    responseCode = "200",
    description = "Pre-signed download URL.",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileDownloadUrlResponse.class)))
@ApiResponse(
    responseCode = "404",
    description = "Asset not found.",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocFileResourcesDownload {}
