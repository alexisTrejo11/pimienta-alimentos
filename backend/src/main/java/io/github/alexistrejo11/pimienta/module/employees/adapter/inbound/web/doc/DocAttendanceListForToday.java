package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

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

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "List attendances for today",
    description =
        """
        Paginated attendances for the current calendar work date. Omit `headquarterId` to include \
        all headquarters; pass it to restrict to one site.""")
@ApiResponse(
    responseCode = "200",
    description = "Paged list (may be empty).",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PagedResponse.class)))
public @interface DocAttendanceListForToday {}
