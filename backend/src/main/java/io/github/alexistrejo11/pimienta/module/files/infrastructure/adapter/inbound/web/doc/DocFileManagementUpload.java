package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto.FileAssetResponse;
import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.http.MediaType;

/** {@code POST /api/v1/files/management/upload} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Upload file (admin)",
    description =
        """
        Upload a file to the company file store. Supply the `file` part and query params: \
        `category` (TEMPLATE | COMPANY | EXTRAS | RESOURCE), `module` (required for RESOURCE), \
        optional `entityType`, `entityId`, `description`. \
        **ROLE_ADMIN** only. Rate limit: **SENSITIVE_OPERATIONS**. Max size: 50 MB.""")
@RequestBody(
    required = true,
    description = "Form part **file**: the binary to store.",
    content =
        @Content(
            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
            schema =
                @Schema(
                    type = "object",
                    description = "Must include part `file`.",
                    requiredProperties = {"file"}),
            encoding = @Encoding(name = "file", contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE)))
@ApiResponse(
    responseCode = "201",
    description = "File stored and catalog entry created.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = FileAssetResponse.class)))
@ApiResponse(
    responseCode = "400",
    description = "Missing file, unsupported size, or invalid parameters.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocFileManagementUpload {}
