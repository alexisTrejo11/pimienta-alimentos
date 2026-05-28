package io.github.alexistrejo11.pimienta.module.files.core.application.query;

import io.github.alexistrejo11.pimienta.module.files.core.domain.enums.FileCategory;
import java.time.LocalDateTime;

public record FileAssetSearchCriteria(
    FileCategory category,
    String module,
    String entityType,
    Long entityId,
    String originalNameContains,
    String contentTypeContains,
    Long uploadedByUserId,
    LocalDateTime createdFrom,
    LocalDateTime createdTo) {}
