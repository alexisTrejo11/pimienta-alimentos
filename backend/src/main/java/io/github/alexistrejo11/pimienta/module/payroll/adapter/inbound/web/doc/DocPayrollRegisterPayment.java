package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request.RegisterPayrollPaymentRequest;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollPaymentResponse;
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

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(summary = "Register payroll payment", description = "Registers a payroll payment and optional debt if payment is partial.")
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RegisterPayrollPaymentRequest.class),
            examples = @ExampleObject(value = "{\"employeeId\":101,\"frequency\":\"WEEKLY\",\"workedDaysStart\":\"2026-04-01\",\"workedDaysEnd\":\"2026-04-07\",\"grossAmount\":3500.00,\"netAmount\":3350.00,\"destinationAccount\":\"012180001234567890\",\"transactionId\":\"TX-2026-000134\"}")))
@ApiResponse(responseCode = "201", description = "Payment registered.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PayrollPaymentResponse.class)))
@ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocPayrollRegisterPayment {}
