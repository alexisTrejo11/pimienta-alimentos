package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request.RegisterPayrollRecordRequest;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollRecordResponse;
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
@Operation(summary = "Create payroll record", description = "Creates payroll record for an employee and worked range.")
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RegisterPayrollRecordRequest.class),
            examples = @ExampleObject(value = "{\"employeeId\":101,\"periodId\":22,\"workedDaysStart\":\"2026-04-01\",\"workedDaysEnd\":\"2026-04-07\",\"grossAmount\":3500.00}")))
@ApiResponse(responseCode = "201", description = "Record created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PayrollRecordResponse.class)))
@ApiResponse(responseCode = "400", description = "Validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocPayrollCreateRecord {}
