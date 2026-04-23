package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.spreadsheet.SpreadsheetBulkImportResult;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code POST /api/v1/opportunities/import} (multipart form) */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Import opportunities from spreadsheet",
    description =
        """
        Upload `multipart/form-data` with field `file` (`.xlsx`). Empty file yields **400**. \
        Rate limit: **SENSITIVE_OPERATIONS**.""")
@ApiResponse(
    responseCode = "200",
    description = "Row-level import outcome.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = SpreadsheetBulkImportResult.class)))
public @interface DocOpportunityImport {}
