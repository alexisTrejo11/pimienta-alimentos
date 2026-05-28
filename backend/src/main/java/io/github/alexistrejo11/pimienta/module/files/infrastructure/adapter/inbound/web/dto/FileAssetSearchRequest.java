package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.inbound.web.dto;

import io.github.alexistrejo11.pimienta.module.files.core.domain.enums.FileCategory;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "Filters and pagination for GET /api/v1/files/management.")
public class FileAssetSearchRequest extends PageableRequest {

  @Schema(description = "Filter by category.", example = "TEMPLATE")
  private FileCategory category;

  @Schema(description = "Filter by owning module (RESOURCE category).", example = "inventory")
  private String module;

  @Schema(description = "Filter by entity type.", example = "inventory-item")
  private String entityType;

  @Schema(description = "Filter by entity id.", example = "42")
  private Long entityId;

  @Schema(description = "Original filename contains (case-insensitive).", example = "invoice")
  private String originalNameContains;

  @Schema(description = "Content type contains.", example = "spreadsheet")
  private String contentTypeContains;

  @Schema(description = "Filter by uploader user id.", example = "1")
  private Long uploadedByUserId;

  @Schema(description = "Created from (inclusive).", example = "2026-05-01T00:00:00")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdFrom;

  @Schema(description = "Created to (inclusive).", example = "2026-05-31T23:59:59")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdTo;

  public FileCategory getCategory() { return category; }
  public void setCategory(FileCategory category) { this.category = category; }

  public String getModule() { return module; }
  public void setModule(String module) { this.module = module; }

  public String getEntityType() { return entityType; }
  public void setEntityType(String entityType) { this.entityType = entityType; }

  public Long getEntityId() { return entityId; }
  public void setEntityId(Long entityId) { this.entityId = entityId; }

  public String getOriginalNameContains() { return originalNameContains; }
  public void setOriginalNameContains(String originalNameContains) { this.originalNameContains = originalNameContains; }

  public String getContentTypeContains() { return contentTypeContains; }
  public void setContentTypeContains(String contentTypeContains) { this.contentTypeContains = contentTypeContains; }

  public Long getUploadedByUserId() { return uploadedByUserId; }
  public void setUploadedByUserId(Long uploadedByUserId) { this.uploadedByUserId = uploadedByUserId; }

  public LocalDateTime getCreatedFrom() { return createdFrom; }
  public void setCreatedFrom(LocalDateTime createdFrom) { this.createdFrom = createdFrom; }

  public LocalDateTime getCreatedTo() { return createdTo; }
  public void setCreatedTo(LocalDateTime createdTo) { this.createdTo = createdTo; }
}
