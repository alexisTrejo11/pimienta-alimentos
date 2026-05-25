package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.ItemCreateRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.ItemResponse;
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

/** {@code POST /api/v1/inventory/items} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Create inventory item",
    description = "Registra un artículo. **201** con cuerpo JSON. Rate limit: **SENSITIVE_OPERATIONS**.")
@RequestBody(
    required = true,
    description = "Cuerpo JSON (**ItemCreateRequest**).",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ItemCreateRequest.class)))
@ApiResponse(
    responseCode = "201",
    description = "Artículo creado.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ItemResponse.class)))
public @interface DocInventoryItemCreate {}
