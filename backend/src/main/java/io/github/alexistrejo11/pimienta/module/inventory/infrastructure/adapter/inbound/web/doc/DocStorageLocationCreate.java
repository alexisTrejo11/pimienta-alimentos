package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.StorageLocationCreateRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.StorageLocationResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code POST /api/v1/inventory/locations} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(summary = "Create storage location", description = "JSON **StorageLocationCreateRequest**. **201**. Rate limit: **SENSITIVE_OPERATIONS**.")
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = StorageLocationCreateRequest.class)))
@ApiResponse(
    responseCode = "201",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = StorageLocationResponse.class)))
public @interface DocStorageLocationCreate {}
