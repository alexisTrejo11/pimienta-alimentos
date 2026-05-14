package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.AttendanceResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Same check-out semantics as multipart ({@link DocAttendanceEndWorkday}) but JSON body only; hidden
 * from Swagger (recommended client is {@code multipart/form-data} with optional evidence photo).
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Hidden
@DocJwtSecured
@ApiResponse(
    responseCode = "200",
    description = "Updated attendance (JSON body only).",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AttendanceResponse.class)))
public @interface DocAttendanceEndWorkdayJsonHidden {}
