package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
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

/** {@code GET /api/v1/notifications/logs} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Today's notification logs (manager)",
    description =
        """
        Returns LOG-channel notifications created **today** only, with optional narrowing filters \
        (type, status, correlation id, subject/body contains). **ROLE_MANAGER** or **ROLE_ADMIN**. \
        Rate limit: **READ_HEAVY**.""")
@ApiResponse(
    responseCode = "200",
    description = "Paged notification log entries for today.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PagedResponse.class)))
public @interface DocNotificationLogsSearch {}
