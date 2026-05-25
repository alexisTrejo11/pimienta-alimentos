package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
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

/** {@code GET /api/v1/opportunities/export} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Parameters({
  @Parameter(
      name = "page",
      in = ParameterIn.QUERY,
      description = "Zero-based page index.",
      example = "0",
      schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")),
  @Parameter(
      name = "size",
      in = ParameterIn.QUERY,
      description = "Page size (1–100).",
      example = "20",
      schema = @Schema(type = "integer", defaultValue = "20", minimum = "1", maximum = "100")),
  @Parameter(
      name = "status",
      in = ParameterIn.QUERY,
      description = "Filter by pipeline status.",
      example = "NEGOTIATION",
      schema = @Schema(implementation = Opportunity.OpportunityStatus.class)),
  @Parameter(
      name = "companyNameContains",
      in = ParameterIn.QUERY,
      description = "Case-insensitive company name substring.",
      example = "Acme"),
  @Parameter(
      name = "titleContains",
      in = ParameterIn.QUERY,
      description = "Case-insensitive title substring.",
      example = "rollout")
})
@Operation(
    summary = "Export opportunities to Excel",
    description =
        """
        Streams an `.xlsx` report using the same optional filters and pagination as search. \
        `Content-Disposition` suggests a filename. Rate limit: **READ_HEAVY**.""")
@ApiResponse(
    responseCode = "200",
    description = "Excel workbook bytes.",
    content =
        @Content(
            mediaType =
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
public @interface DocOpportunityExport {}
