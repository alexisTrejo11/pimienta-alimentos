package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@Operation(
    summary = "Export opportunities to Excel",
    description =
        """
        Streams an `.xlsx` report using the same filters as search. `Content-Disposition` suggests a \
        filename. Rate limit: **READ_HEAVY**.""")
@ApiResponse(
    responseCode = "200",
    description = "Excel workbook bytes.",
    content =
        @Content(
            mediaType =
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
public @interface DocOpportunityExport {}
