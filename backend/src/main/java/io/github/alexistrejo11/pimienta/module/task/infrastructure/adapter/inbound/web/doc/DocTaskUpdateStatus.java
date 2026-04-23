package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.StatusUpdateRequest;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskResponse;
import io.github.alexistrejo11.pimienta.shared.exception.api.ApiErrorResponse;
import io.github.alexistrejo11.pimienta.shared.web.openapi.doc.DocJwtSecured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** {@code PATCH /api/v1/tasks/{id}/status} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Update task status",
    description =
        """
        Applies **StatusUpdateRequest**. When status becomes COMPLETED, completion timestamp is \
        set in the use case layer. **404** if task not found (`TASK_NOT_FOUND`).""")
@Parameter(
    name = "id",
    in = ParameterIn.PATH,
    description = "Task id.",
    example = "101",
    required = true,
    schema = @Schema(type = "integer", format = "int64"))
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = StatusUpdateRequest.class),
            examples =
                @ExampleObject(
                    name = "complete",
                    value =
                        """
                        { "status": "COMPLETED" }
                        """)))
@ApiResponse(
    responseCode = "200",
    description = "Updated task.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TaskResponse.class)))
@ApiResponse(
    responseCode = "400",
    description = "Validation failed (e.g. null status).",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
@ApiResponse(
    responseCode = "404",
    description = "Not found.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocTaskUpdateStatus {}
