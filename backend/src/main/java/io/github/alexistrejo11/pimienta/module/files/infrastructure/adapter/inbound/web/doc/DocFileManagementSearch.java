package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto.FileAssetResponse;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code GET /api/v1/files/management} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Search file assets (admin)",
    description =
        """
        Paginated search across all file categories with optional filters combined with AND logic. \
        Date-time parameters use ISO-8601 format (`2026-05-28T10:15:00`). \
        **ROLE_ADMIN** only. Rate limit: **READ_HEAVY**.""")
@Parameters({
  @Parameter(
      name = "category",
      in = ParameterIn.QUERY,
      description = "Filter by storage category.",
      schema = @Schema(type = "string", allowableValues = {"TEMPLATE", "COMPANY", "EXTRAS", "RESOURCE"}, example = "TEMPLATE")),
  @Parameter(
      name = "module",
      in = ParameterIn.QUERY,
      description = "Filter by module slug (RESOURCE category).",
      schema = @Schema(type = "string", example = "inventory")),
  @Parameter(
      name = "entityType",
      in = ParameterIn.QUERY,
      description = "Filter by entity-type tag.",
      schema = @Schema(type = "string", example = "inventory-item")),
  @Parameter(
      name = "entityId",
      in = ParameterIn.QUERY,
      description = "Filter by entity id.",
      schema = @Schema(type = "integer", format = "int64", example = "42")),
  @Parameter(
      name = "originalNameContains",
      in = ParameterIn.QUERY,
      description = "Original filename contains (case-insensitive).",
      schema = @Schema(type = "string", example = "invoice")),
  @Parameter(
      name = "contentTypeContains",
      in = ParameterIn.QUERY,
      description = "MIME type contains (case-insensitive).",
      schema = @Schema(type = "string", example = "spreadsheet")),
  @Parameter(
      name = "uploadedByUserId",
      in = ParameterIn.QUERY,
      description = "Filter by uploader user id.",
      schema = @Schema(type = "integer", format = "int64", example = "1")),
  @Parameter(
      name = "createdFrom",
      in = ParameterIn.QUERY,
      description = "Created from (inclusive), ISO-8601.",
      schema = @Schema(type = "string", format = "date-time", example = "2026-05-01T00:00:00")),
  @Parameter(
      name = "createdTo",
      in = ParameterIn.QUERY,
      description = "Created to (inclusive), ISO-8601.",
      schema = @Schema(type = "string", format = "date-time", example = "2026-05-31T23:59:59")),
  @Parameter(name = "page", in = ParameterIn.QUERY, description = "Zero-based page index.", schema = @Schema(type = "integer", defaultValue = "0")),
  @Parameter(name = "size", in = ParameterIn.QUERY, description = "Page size (max 100).", schema = @Schema(type = "integer", defaultValue = "20"))
})
@ApiResponse(
    responseCode = "200",
    description = "Paged list of file assets.",
    content =
        @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FileAssetResponse.class)),
            schema = @Schema(implementation = PagedResponse.class)))
public @interface DocFileManagementSearch {}
