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

/** {@code POST /api/v1/contracts/{id}/renew} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameter(
    name = "id",
    in = ParameterIn.PATH,
    required = true,
    description = "Contrato de plazo fijo a renovar (suma `renewalCycleMonths` al fin actual).",
    example = "42")
@Operation(
    summary = "Renew fixed-term contract",
    description =
        """
        Advances `effectiveEnd` by `renewalCycleMonths` for **FIXED_TERM** contracts. \
        **400** if term kind, cycle, or end date is invalid (`ContractRuleViolationException`).""")
@ApiResponse(
    responseCode = "200",
    description = "Contract after renewal.",
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
public @interface DocContractsRenew {}
