package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.files.core.application.query.FileAssetSearchCriteria;
import io.github.alexistrejo11.pimienta.module.files.core.domain.FileAsset;
import io.github.alexistrejo11.pimienta.module.files.core.domain.enums.FileCategory;
import io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto.FileAssetResponse;
import io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto.FileAssetSearchRequest;
import io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto.FileResourceSearchRequest;

public final class FileAssetWebMapper {

  private FileAssetWebMapper() {}

  public static FileAssetResponse toResponse(FileAsset domain) {
    return new FileAssetResponse(
        domain.getId(),
        domain.getCategory(),
        domain.getModule(),
        domain.getEntityType(),
        domain.getEntityId(),
        domain.getS3Key(),
        domain.getOriginalName(),
        domain.getContentType(),
        domain.getFileSizeBytes(),
        domain.getDescription(),
        domain.getUploadedByUserId(),
        domain.getCreatedAt());
  }

  public static FileAssetSearchCriteria toAdminCriteria(FileAssetSearchRequest req) {
    return new FileAssetSearchCriteria(
        req.getCategory(),
        req.getModule(),
        req.getEntityType(),
        req.getEntityId(),
        req.getOriginalNameContains(),
        req.getContentTypeContains(),
        req.getUploadedByUserId(),
        req.getCreatedFrom(),
        req.getCreatedTo());
  }

  public static FileAssetSearchCriteria toResourceCriteria(FileResourceSearchRequest req) {
    return new FileAssetSearchCriteria(
        FileCategory.RESOURCE,
        req.getModule(),
        req.getEntityType(),
        req.getEntityId(),
        req.getOriginalNameContains(),
        req.getContentTypeContains(),
        null,
        req.getCreatedFrom(),
        req.getCreatedTo());
  }
}
