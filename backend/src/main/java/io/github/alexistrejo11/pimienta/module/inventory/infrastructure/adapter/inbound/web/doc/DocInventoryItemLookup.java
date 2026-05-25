package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.ItemResponse;
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

/** {@code GET /api/v1/inventory/items/lookup} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameter(
    name = "q",
    in = ParameterIn.QUERY,
    required = true,
    description = "SKU o código de barras a resolver (no vacío).",
    example = "SKU-001")
@Operation(
    summary = "Lookup item by SKU or barcode",
    description = "Devuelve un artículo por SKU o código de barras. **404** si no existe (`ITEM_NOT_FOUND`). Rate limit: **READ_HEAVY**.")
@ApiResponse(
    responseCode = "200",
    description = "Artículo encontrado.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ItemResponse.class)))
@ApiResponse(
    responseCode = "404",
    description = "No encontrado.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocInventoryItemLookup {}
