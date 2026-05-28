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

/** {@code POST /api/v1/files/resources/upload} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Upload module resource file",
    description =
        """
        Upload a file linked to a specific module and optional entity. Supply `module` (required), \
        `entityType` and `entityId` (optional), `description` (optional) as query params. \
        **ROLE_ADMIN** or **ROLE_MANAGER**. Rate limit: **SENSITIVE_OPERATIONS**. Max size: 50 MB.""")
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
            schema =
                @Schema(
                    type = "object",
                    requiredProperties = {"file"}),
            encoding = @Encoding(name = "file", contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE)))
@ApiResponse(
    responseCode = "201",
    description = "Resource file stored.",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileAssetResponse.class)))
@ApiResponse(
    responseCode = "400",
    description = "Validation error.",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocFileResourcesUpload {}
