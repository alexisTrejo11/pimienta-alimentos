package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
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

/** {@code GET /api/v1/employees/export} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameters({
  @Parameter(
      name = "page",
      in = ParameterIn.QUERY,
      description = "Índice de página (base 0).",
      example = "0",
      schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")),
  @Parameter(
      name = "size",
      in = ParameterIn.QUERY,
      description = "Tamaño de página (1–100).",
      example = "20",
      schema = @Schema(type = "integer", defaultValue = "20", minimum = "1", maximum = "100")),
  @Parameter(
      name = "status",
      in = ParameterIn.QUERY,
      description = "Filtro por estado.",
      schema = @Schema(implementation = EmployeeStatus.class)),
  @Parameter(name = "department", in = ParameterIn.QUERY, description = "Filtro por departamento."),
  @Parameter(name = "q", in = ParameterIn.QUERY, description = "Búsqueda de texto.")
})
@Operation(
    summary = "Export employees to Excel",
    description =
        """
        Streams an `.xlsx` report using the same filters as search (`status`, `department`, `q`) \
        and pagination (`page`, `size`). `Content-Disposition` suggests a filename. Rate limit: **READ_HEAVY**.""")
@ApiResponse(
    responseCode = "200",
    description = "Excel workbook bytes.",
    content =
        @Content(
            mediaType =
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
public @interface DocEmployeeExport {}
