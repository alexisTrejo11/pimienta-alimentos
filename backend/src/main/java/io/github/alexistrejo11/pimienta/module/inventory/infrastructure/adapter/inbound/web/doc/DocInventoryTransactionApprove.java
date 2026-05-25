package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.ApproveTransactionRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.InventoryTransactionResponse;
import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code POST /api/v1/inventory/transactions/{id}/approve} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "ID de transacción.", example = "100")
@Operation(summary = "Approve transaction", description = "Cuerpo JSON **ApproveTransactionRequest** (`approvedById`). Rate limit: **SENSITIVE_OPERATIONS**.")
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApproveTransactionRequest.class)))
@ApiResponse(
    responseCode = "200",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = InventoryTransactionResponse.class)))
@ApiResponse(
    responseCode = "404",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocInventoryTransactionApprove {}
