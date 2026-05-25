package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.InventoryMovementResponse;
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

/** {@code GET /api/v1/inventory/movements/by-reference} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameter(
    name = "referenceNumber",
    in = ParameterIn.QUERY,
    required = true,
    description = "Número de referencia de la transacción u operación relacionada.",
    example = "TX-2025-00042")
@Operation(summary = "List movements by reference number", description = "Rate limit: **READ_HEAVY**.")
@ApiResponse(
    responseCode = "200",
    content =
        @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = InventoryMovementResponse.class))))
public @interface DocInventoryMovementByReference {}
