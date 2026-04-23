package io.github.alexistrejo11.pimienta.module.contract.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.contract.adapter.inbound.web.dto.response.ContractResponse;
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

/** {@code GET /api/v1/contracts/{id}} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameter(
    name = "id",
    in = ParameterIn.PATH,
    required = true,
    description = "Identificador del contrato.",
    example = "42")
@Operation(
    summary = "Get contract by id",
    description = "Loads one contract by id. **404** when missing or soft-deleted.")
@ApiResponse(
    responseCode = "200",
    description = "Contract found.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ContractResponse.class)))
@ApiResponse(
    responseCode = "404",
    description = "Contract not found (`CONTRACT_NOT_FOUND`).",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocContractsGetById {}
