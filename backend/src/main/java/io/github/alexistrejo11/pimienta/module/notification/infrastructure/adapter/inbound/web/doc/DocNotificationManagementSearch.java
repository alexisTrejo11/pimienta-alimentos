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

/** {@code GET /api/v1/notifications/management} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Search notifications (admin)",
    description =
        """
        Paginated search over the notification audit log. All filters are optional and combined \
        with AND logic. Date-time parameters use ISO-8601 format (`2026-05-28T10:15:00`). \
        **ROLE_ADMIN** only. Rate limit: **READ_HEAVY**.""")
@Parameters({
  @Parameter(
      name = "id",
      in = ParameterIn.QUERY,
      description = "Filter by exact notification UUID.",
      schema = @Schema(type = "string", format = "uuid", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")),
  @Parameter(
      name = "channel",
      in = ParameterIn.QUERY,
      description = "Filter by delivery channel.",
      schema = @Schema(type = "string", allowableValues = {"EMAIL", "SMS", "LOG"}, example = "LOG")),
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
      description = "Filter by related business user id (e.g. the registrant).",
      schema = @Schema(type = "integer", format = "int64", example = "42")),
  @Parameter(
      name = "correlationId",
      in = ParameterIn.QUERY,
      description = "Correlation id contains (case-insensitive).",
      schema = @Schema(type = "string", example = "account-pending")),
  @Parameter(
      name = "recipientEmailContains",
      in = ParameterIn.QUERY,
      description = "Recipient email contains (case-insensitive).",
      schema = @Schema(type = "string", example = "admin@")),
  @Parameter(
      name = "recipientPhoneContains",
      in = ParameterIn.QUERY,
      description = "Recipient phone contains.",
      schema = @Schema(type = "string", example = "+57300")),
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
      name = "createdFrom",
      in = ParameterIn.QUERY,
      description = "Created at from (inclusive), ISO-8601.",
      schema = @Schema(type = "string", format = "date-time", example = "2026-05-01T00:00:00")),
  @Parameter(
      name = "createdTo",
      in = ParameterIn.QUERY,
      description = "Created at to (inclusive), ISO-8601.",
      schema = @Schema(type = "string", format = "date-time", example = "2026-05-28T23:59:59")),
  @Parameter(
      name = "sentFrom",
      in = ParameterIn.QUERY,
      description = "Sent at from (inclusive), ISO-8601.",
      schema = @Schema(type = "string", format = "date-time", example = "2026-05-01T00:00:00")),
  @Parameter(
      name = "sentTo",
      in = ParameterIn.QUERY,
      description = "Sent at to (inclusive), ISO-8601.",
      schema = @Schema(type = "string", format = "date-time", example = "2026-05-28T23:59:59")),
  @Parameter(name = "page", in = ParameterIn.QUERY, description = "Zero-based page index.", schema = @Schema(type = "integer", defaultValue = "0")),
  @Parameter(name = "size", in = ParameterIn.QUERY, description = "Page size (max 100).", schema = @Schema(type = "integer", defaultValue = "20"))
})
@ApiResponse(
    responseCode = "200",
    description = "Paged notification records.",
    content =
        @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = NotificationResponse.class)),
            schema = @Schema(implementation = PagedResponse.class)))
public @interface DocNotificationManagementSearch {}
