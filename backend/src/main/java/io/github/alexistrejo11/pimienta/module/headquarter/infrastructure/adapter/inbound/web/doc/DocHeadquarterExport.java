package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.doc;

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

/** {@code GET /api/v1/headquarters/export} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameters({
  @Parameter(
      name = "page",
      in = ParameterIn.QUERY,
      description = "Page index for the export batch (same binding model as list).",
      example = "0",
      schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")),
  @Parameter(
      name = "size",
      in = ParameterIn.QUERY,
      description = "Page size (1–100); controls how many rows each export chunk processes.",
      example = "100",
      schema = @Schema(type = "integer", defaultValue = "20", minimum = "1", maximum = "100"))
})
@Operation(
    summary = "Export headquarters to Excel",
    description =
        """
        Streams an `.xlsx` report for the requested page. `Content-Disposition` suggests a filename. \
        Rate limit: **READ_HEAVY**.""")
@ApiResponse(
    responseCode = "200",
    description = "Excel workbook bytes.",
    content =
        @Content(
            mediaType =
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
public @interface DocHeadquarterExport {}
