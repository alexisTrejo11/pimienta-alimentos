package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeResponse;
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
 * Same update semantics as multipart ({@link DocEmployeeUpdate}) but accepts a JSON body only; hidden
 * from Swagger (recommended client is {@code multipart/form-data} with optional photo).
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Hidden
@DocJwtSecured
@ApiResponse(
    responseCode = "200",
    description = "Updated employee (JSON body only).",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = EmployeeResponse.class)))
public @interface DocEmployeeUpdateJsonHidden {}
