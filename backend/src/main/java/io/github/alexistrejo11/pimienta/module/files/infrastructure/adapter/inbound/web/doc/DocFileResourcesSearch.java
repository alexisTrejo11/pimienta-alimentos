package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.doc;

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

/** {@code GET /api/v1/files/resources} */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DocJwtSecured
@Operation(
    summary = "Search module resource files",
    description =
        """
        Paginated search for RESOURCE-category files. `module` is required; optionally filter by \
        entityType, entityId, filename, contentType, date range. **ROLE_ADMIN** or **ROLE_MANAGER**. \
        Rate limit: **READ_HEAVY**.""")
@ApiResponse(
    responseCode = "200",
    description = "Paged resource files.",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResponse.class)))
public @interface DocFileResourcesSearch {}
