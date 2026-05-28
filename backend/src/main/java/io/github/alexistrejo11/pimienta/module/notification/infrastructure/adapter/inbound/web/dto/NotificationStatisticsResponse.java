package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Aggregated notification metrics for a time interval.")
public record NotificationStatisticsResponse(
    @Schema(
            description = "Interval start (inclusive).",
            example = "2026-05-01T00:00:00",
            type = "string",
            format = "date-time")
        LocalDateTime from,
    @Schema(
            description = "Interval end (inclusive).",
            example = "2026-05-28T23:59:59",
            type = "string",
            format = "date-time")
        LocalDateTime to,
    @Schema(description = "Total notifications in the interval.", example = "120") long total,
    @Schema(description = "Pending count.", example = "3") long pending,
    @Schema(description = "Successfully sent count.", example = "110") long sent,
    @Schema(description = "Failed count.", example = "5") long failed,
    @Schema(description = "Skipped count.", example = "2") long skipped,
    @Schema(description = "Counts grouped by channel name.", example = "{\"LOG\":80,\"EMAIL\":30}")
        Map<String, Long> countByChannel,
    @Schema(
            description = "Counts grouped by type name.",
            example = "{\"ACCOUNT_PENDING_APPROVAL\":120}")
        Map<String, Long> countByType,
    @Schema(description = "Counts grouped by status name.", example = "{\"SENT\":110,\"FAILED\":5}")
        Map<String, Long> countByStatus) {}
