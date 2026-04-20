package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
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
  @Parameter(name = "employeeId", in = ParameterIn.QUERY, description = "Filter by employee id.", example = "101"),
  @Parameter(name = "periodId", in = ParameterIn.QUERY, description = "Filter by period id.", example = "22"),
  @Parameter(name = "from", in = ParameterIn.QUERY, description = "Worked start date (inclusive).", example = "2026-04-01"),
  @Parameter(name = "to", in = ParameterIn.QUERY, description = "Worked end date (inclusive).", example = "2026-04-30"),
  @Parameter(name = "page", in = ParameterIn.QUERY, description = "Page index.", example = "0"),
  @Parameter(name = "size", in = ParameterIn.QUERY, description = "Page size.", example = "20")
})
@Operation(summary = "Export payroll records", description = "Exports payroll records to .xlsx using scope filters.")
@ApiResponse(responseCode = "200", description = "Excel file.", content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
public @interface DocPayrollExport {}
