package io.github.alexistrejo11.pimienta.module.crm.adapter.inbound.web.doc;

import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskRequest;
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

/** {@code POST /api/v1/opportunities/{id}/tasks} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Create task under opportunity",
    description =
        """
        Creates a task linked to the opportunity **id** after verifying the opportunity exists. The \
        request body is the same **TaskRequest** as `POST /api/v1/tasks`; the opportunity id on the \
        path takes precedence for linkage.""")
@Parameter(
    name = "id",
    in = ParameterIn.PATH,
    description = "Opportunity id.",
    example = "500",
    required = true,
    schema = @Schema(type = "integer", format = "int64"))
@RequestBody(
    required = true,
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TaskRequest.class),
            examples =
                @ExampleObject(
                    name = "taskUnderOpportunity",
                    value =
                        """
                        {
                          "title": "Send proposal",
                          "priority": "MEDIUM",
                          "dueDate": "2026-04-22T12:00:00"
                        }
                        """)))
@ApiResponse(
    responseCode = "201",
    description = "Created task.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TaskResponse.class)))
@ApiResponse(
    responseCode = "400",
    description = "Validation failed (e.g. blank title) or other bad request.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
@ApiResponse(
    responseCode = "404",
    description = "Opportunity not found.",
    content =
        @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class)))
public @interface DocOpportunityCreateTask {}
