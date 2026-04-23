package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** Aggregated headquarter counts for dashboards. */
@Schema(
    name = "HeadquarterStatisticsResponse",
    description =
        "Aggregated counts: total rows in the table, active (no `deletedAt`), and soft-deleted.")
public record HeadquarterStatisticsResponse(
    @Schema(description = "Total rows including soft-deleted.", example = "42")
    long total,
    @Schema(description = "Active sites (`deletedAt` is null).", example = "40")
    long active,
    @Schema(description = "Soft-deleted sites.", example = "2")
    long softDeleted) {}
