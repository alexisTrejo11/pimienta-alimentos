package io.github.alexistrejo11.pimienta.module.files.core.application.command;

import io.github.alexistrejo11.pimienta.module.files.core.domain.enums.FileCategory;
import org.springframework.web.multipart.MultipartFile;

public record UploadFileCommand(
    MultipartFile file,
    FileCategory category,
    /** Required when category is RESOURCE. */
    String module,
    /** Optional. Used only when category is RESOURCE. */
    String entityType,
    /** Optional. Used only when category is RESOURCE. */
    Long entityId,
    String description,
    Long uploadedByUserId) {}
