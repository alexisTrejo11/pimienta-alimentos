package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Pre-signed 24-hour download URL for a file asset.")
public record FileDownloadUrlResponse(
    @Schema(description = "Asset id.") UUID id,
    @Schema(
            description = "Pre-signed S3 download URL (valid 24 h).",
            example = "https://bucket.s3.us-east-1.amazonaws.com/pimienta/sources/...")
        String url) {}
