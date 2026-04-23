package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.AssignTaskRequest;
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

/** {@code PATCH /api/v1/tasks/{id}/assign} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Assign task to employee",
    description =
        """
        Sets assignee from **AssignTaskRequest**. **404** if task not found (`TASK_NOT_FOUND`).""")
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
            schema = @Schema(implementation = AssignTaskRequest.class),
            examples =
                @ExampleObject(
                    name = "assign",
                    value =
                        """
                        { "employeeId": 42 }
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
    description = "Validation failed (e.g. null employeeId).",
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
public @interface DocTaskAssign {}
