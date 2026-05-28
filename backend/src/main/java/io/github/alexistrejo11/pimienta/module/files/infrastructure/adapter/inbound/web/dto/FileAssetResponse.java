package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.files.core.domain.enums.FileCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Stored company file asset.")
public record FileAssetResponse(
    @Schema(description = "Asset id.", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") UUID id,
    @Schema(description = "Storage category.", example = "TEMPLATE") FileCategory category,
    @Schema(description = "Owning module (RESOURCE only).", example = "inventory") String module,
    @Schema(description = "Entity type tag (RESOURCE only).", example = "inventory-item") String entityType,
    @Schema(description = "Entity id (RESOURCE only).", example = "42") Long entityId,
    @Schema(description = "S3 object key.", example = "pimienta/sources/templates/invoice_template_1716916800000_3742.xlsx")
        String s3Key,
    @Schema(description = "Original filename.", example = "invoice_template.xlsx") String originalName,
    @Schema(description = "MIME type.", example = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        String contentType,
    @Schema(description = "File size in bytes.", example = "204800") Long fileSizeBytes,
    @Schema(description = "Optional description.") String description,
    @Schema(description = "User id who uploaded the file.", example = "1") Long uploadedByUserId,
    @Schema(
            description = "Creation timestamp.",
            example = "2026-05-28T10:15:00",
            type = "string",
            format = "date-time")
        LocalDateTime createdAt) {}
