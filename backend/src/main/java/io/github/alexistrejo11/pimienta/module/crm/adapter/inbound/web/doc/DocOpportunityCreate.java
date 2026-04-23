package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.CreateOpportunityRequest;
import io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.dto.OpportunityResponse;
import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
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

/** {@code POST /api/v1/opportunities} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Create opportunity",
    description =
        """
        Registers a new opportunity from **CreateOpportunityRequest** (Jakarta validation on the \
        body).""")
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CreateOpportunityRequest.class),
            examples =
                @ExampleObject(
                    name = "minimal",
                    value =
                        """
                        {
                          "title": "Enterprise rollout",
                          "description": "Phased deployment",
                          "contactName": "Jane Doe",
                          "contactEmail": "jane@example.com",
                          "companyName": "Acme Corp",
                          "estimatedValue": 150000.00,
                          "probabilityPercent": 25,
                          "source": "INBOUND"
                        }
                        """)))
@ApiResponse(
    responseCode = "201",
    description = "Created opportunity.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = OpportunityResponse.class)))
@ApiResponse(
    responseCode = "400",
    description = "Validation failed or bad request.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocOpportunityCreate {}
