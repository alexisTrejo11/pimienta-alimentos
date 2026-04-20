package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
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
  @Parameter(name = "page", in = ParameterIn.QUERY, description = "Page index.", example = "0"),
  @Parameter(name = "size", in = ParameterIn.QUERY, description = "Page size.", example = "20")
})
@Operation(summary = "List payroll periods", description = "Returns payroll periods with pagination.")
@ApiResponse(responseCode = "200", description = "Paged payroll periods.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResponse.class)))
public @interface DocPayrollListPeriods {}
