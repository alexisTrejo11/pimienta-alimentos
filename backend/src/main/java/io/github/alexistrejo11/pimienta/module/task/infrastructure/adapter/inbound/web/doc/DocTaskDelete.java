package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code DELETE /api/v1/tasks/{id}} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Soft-delete task",
    description =
        """
        Soft-deletes the task. **204** on success. Rate limit: **SENSITIVE_OPERATIONS**. **404** if \
        not found.""")
@Parameter(
    name = "id",
    in = ParameterIn.PATH,
    description = "Task id.",
    example = "101",
    required = true,
    schema = @Schema(type = "integer", format = "int64"))
@ApiResponse(responseCode = "204", description = "No content; task soft-deleted.")
@ApiResponse(
    responseCode = "404",
    description = "Not found (`TASK_NOT_FOUND`).",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocTaskDelete {}
