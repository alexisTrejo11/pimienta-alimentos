package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.InventoryStockResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code GET /api/v1/inventory/stock/item/{itemId}} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameter(
    name = "itemId",
    in = ParameterIn.PATH,
    required = true,
    description = "Identificador del artículo.",
    example = "10")
@Operation(
    summary = "List stock by item",
    description = "Todas las filas de existencia para un artículo. Rate limit: **READ_HEAVY**.")
@ApiResponse(
    responseCode = "200",
    description = "Lista de existencias.",
    content =
        @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = InventoryStockResponse.class))))
public @interface DocInventoryStockByItem {}
