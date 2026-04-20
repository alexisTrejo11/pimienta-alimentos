package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request.RegisterPayrollAdjustmentRequest;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollPaymentResponse;
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

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameter(name = "recordId", in = ParameterIn.PATH, description = "Payroll record id.", example = "3001")
@Operation(summary = "Register payroll adjustment", description = "Applies bonus/discount to a payroll record.")
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RegisterPayrollAdjustmentRequest.class),
            examples = @ExampleObject(value = "{\"type\":\"DISCOUNT\",\"amount\":150.00,\"reason\":\"Late arrivals\"}")))
@ApiResponse(responseCode = "201", description = "Adjustment applied.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PayrollPaymentResponse.class)))
@ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocPayrollRegisterAdjustment {}
