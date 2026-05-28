package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto.NotificationResponse;
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

/** {@code GET /api/v1/notifications/logs} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Today's notification logs (manager)",
    description =
        """
        Returns **LOG-channel** notifications created **today** (server calendar day). \
        The channel and date window are enforced server-side regardless of any filter you pass. \
        Additional optional filters narrow within that window. \
        **ROLE_MANAGER** or **ROLE_ADMIN**. Rate limit: **READ_HEAVY**.""")
@Parameters({
  @Parameter(
      name = "id",
      in = ParameterIn.QUERY,
      description = "Filter by exact notification UUID.",
      schema = @Schema(type = "string", format = "uuid")),
  @Parameter(
      name = "type",
      in = ParameterIn.QUERY,
      description = "Filter by notification type.",
      schema = @Schema(type = "string", allowableValues = {"ACCOUNT_PENDING_APPROVAL", "UNDEFINED"}, example = "ACCOUNT_PENDING_APPROVAL")),
  @Parameter(
      name = "status",
      in = ParameterIn.QUERY,
      description = "Filter by delivery status.",
      schema = @Schema(type = "string", allowableValues = {"PENDING", "SENT", "FAILED", "SKIPPED"}, example = "SENT")),
  @Parameter(
      name = "recipientUserId",
      in = ParameterIn.QUERY,
      description = "Filter by recipient user id.",
      schema = @Schema(type = "integer", format = "int64", example = "1")),
  @Parameter(
      name = "relatedUserId",
      in = ParameterIn.QUERY,
      description = "Filter by related business user id.",
      schema = @Schema(type = "integer", format = "int64", example = "42")),
  @Parameter(
      name = "correlationId",
      in = ParameterIn.QUERY,
      description = "Correlation id contains (case-insensitive).",
      schema = @Schema(type = "string", example = "account-pending")),
  @Parameter(
      name = "subjectContains",
      in = ParameterIn.QUERY,
      description = "Subject contains (case-insensitive).",
      schema = @Schema(type = "string", example = "approval")),
  @Parameter(
      name = "bodyContains",
      in = ParameterIn.QUERY,
      description = "Body contains (case-insensitive).",
      schema = @Schema(type = "string", example = "registered")),
  @Parameter(
      name = "sentFrom",
      in = ParameterIn.QUERY,
      description = "Sent at from (inclusive), within today, ISO-8601.",
      schema = @Schema(type = "string", format = "date-time", example = "2026-05-28T08:00:00")),
  @Parameter(
      name = "sentTo",
      in = ParameterIn.QUERY,
      description = "Sent at to (inclusive), within today, ISO-8601.",
      schema = @Schema(type = "string", format = "date-time", example = "2026-05-28T18:00:00")),
  @Parameter(name = "page", in = ParameterIn.QUERY, description = "Zero-based page index.", schema = @Schema(type = "integer", defaultValue = "0")),
  @Parameter(name = "size", in = ParameterIn.QUERY, description = "Page size (max 100).", schema = @Schema(type = "integer", defaultValue = "20"))
})
@ApiResponse(
    responseCode = "200",
    description = "Paged LOG-channel notification entries for today.",
    content =
        @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = NotificationResponse.class)),
            schema = @Schema(implementation = PagedResponse.class)))
public @interface DocNotificationLogsSearch {}
