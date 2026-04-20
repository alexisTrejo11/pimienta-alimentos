package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollSummaryResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Parameters({
  @Parameter(name = "from", in = ParameterIn.QUERY, description = "Summary start date.", example = "2026-04-01"),
  @Parameter(name = "to", in = ParameterIn.QUERY, description = "Summary end date.", example = "2026-04-30")
})
@Operation(summary = "Payroll summary", description = "Returns aggregated payroll totals for a date range.")
@ApiResponse(responseCode = "200", description = "Summary payload.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PayrollSummaryResponse.class)))
public @interface DocPayrollSummary {}
