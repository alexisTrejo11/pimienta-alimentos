package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/** Headquarter as returned by the API. */
@Schema(
    name = "HeadQuarterResponse",
    description =
        """
        Headquarter representation. Text fields may be empty strings when absent in storage; \
        **deletedAt** indicates soft delete.""")
public record HeadQuarterResponse(
    @Schema(description = "Persisted id.", example = "1")
    Long id,
    @Schema(description = "Site name.", example = "North Plant — Monterrey")
    String name,
    @Schema(description = "Address.", example = "1200 Industrial Ave")
    String address,
    @Schema(description = "Description.", example = "Raw materials receiving.")
    String description,
    @Schema(description = "Created at (audit).", type = "string", format = "date-time")
    LocalDateTime createdAt,
    @Schema(description = "Last update (audit).", type = "string", format = "date-time")
    LocalDateTime updatedAt,
    @Schema(
        description = "Soft-delete timestamp; null when active.",
        type = "string",
        format = "date-time")
    LocalDateTime deletedAt,
    @Schema(description = "Optimistic lock version (JPA @Version).", example = "3")
    Long version) {}
