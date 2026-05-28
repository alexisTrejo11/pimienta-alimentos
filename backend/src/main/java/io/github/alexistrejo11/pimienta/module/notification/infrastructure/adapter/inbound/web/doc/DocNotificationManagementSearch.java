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

/** {@code GET /api/v1/notifications/management} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Search notifications (admin)",
    description =
        """
        Paginated search over the notification audit log with optional filters (channel, type, \
        status, recipients, correlation id, created/sent intervals). **ROLE_ADMIN** only. \
        Rate limit: **READ_HEAVY**.""")
@ApiResponse(
    responseCode = "200",
    description = "Paged notifications.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PagedResponse.class)))
public @interface DocNotificationManagementSearch {}
