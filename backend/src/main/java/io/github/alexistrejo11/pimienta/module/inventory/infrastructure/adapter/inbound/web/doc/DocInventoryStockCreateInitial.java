package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.CreateInitialStockRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.InventoryStockResponse;
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

/** {@code POST /api/v1/inventory/stock} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Create initial stock",
    description =
        """
        Crea la primera existencia artículo+ubicación. **201** si ok. Puede fallar con \
        `INVENTORY_ALREADY_EXISTS` o referencias inválidas. Rate limit: **SENSITIVE_OPERATIONS**.""")
@RequestBody(
    required = true,
    description = "JSON (**CreateInitialStockRequest**): `itemId`, `locationId`, `initialQuantity`.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CreateInitialStockRequest.class)))
@ApiResponse(
    responseCode = "201",
    description = "Fila de inventario creada.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = InventoryStockResponse.class)))
public @interface DocInventoryStockCreateInitial {}
