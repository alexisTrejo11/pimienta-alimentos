package io.github.alexistrejo11.pimienta.module.contract.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.contract.adapter.inbound.web.dto.request.ExtendContractRequest;
import io.github.alexistrejo11.pimienta.module.contract.adapter.inbound.web.dto.response.ContractResponse;
import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code PUT /api/v1/contracts/{id}/extend} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameter(
    name = "id",
    in = ParameterIn.PATH,
    required = true,
    description = "Contrato de plazo fijo cuya fecha de término se amplía manualmente.",
    example = "42")
@Operation(
    summary = "Extend contract end date",
    description =
        """
        Sets a new end date strictly after the current end (**FIXED_TERM** only). \
        **400** when validation fails.""")
@RequestBody(
    required = true,
    description = "Nueva fecha de fin (**ExtendContractRequest**).",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ExtendContractRequest.class),
            examples = {
              @ExampleObject(
                  name = "extendEnd",
                  summary = "Prorrogar al 2027",
                  value =
                      """
                      {
                        "newEnd": "2027-12-31"
                      }
                      """)
            }))
@ApiResponse(
    responseCode = "200",
    description = "Contract after extension.",
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
public @interface DocContractsExtend {}
