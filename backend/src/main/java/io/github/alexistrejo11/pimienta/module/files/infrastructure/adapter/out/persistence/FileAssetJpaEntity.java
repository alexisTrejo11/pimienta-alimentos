package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.files.core.domain.enums.FileCategory;
import io.github.alexistrejo11.pimienta.shared.jpa.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(
    name = "file_assets",
    indexes = {
      @Index(name = "idx_file_assets_category", columnList = "category"),
      @Index(name = "idx_file_assets_module", columnList = "module"),
      @Index(name = "idx_file_assets_deleted_at", columnList = "deleted_at"),
      @Index(name = "idx_file_assets_created_at", columnList = "created_at")
    })
public class FileAssetJpaEntity extends BaseJpaEntity {

  @Id
  @Column(nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private FileCategory category;

  @Column(length = 64)
  private String module;

  @Column(name = "entity_type", length = 64)
  private String entityType;

  @Column(name = "entity_id")
  private Long entityId;

  @Column(name = "s3_key", nullable = false, length = 1000)
  private String s3Key;

  @Column(name = "original_name", nullable = false, length = 500)
  private String originalName;

  @Column(name = "content_type", nullable = false, length = 120)
  private String contentType;

  @Column(name = "file_size_bytes")
  private Long fileSizeBytes;

  @Column(length = 1000)
  private String description;

  @Column(name = "uploaded_by_user_id")
  private Long uploadedByUserId;

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public FileCategory getCategory() { return category; }
  public void setCategory(FileCategory category) { this.category = category; }

  public String getModule() { return module; }
  public void setModule(String module) { this.module = module; }

  public String getEntityType() { return entityType; }
  public void setEntityType(String entityType) { this.entityType = entityType; }

  public Long getEntityId() { return entityId; }
  public void setEntityId(Long entityId) { this.entityId = entityId; }

  public String getS3Key() { return s3Key; }
  public void setS3Key(String s3Key) { this.s3Key = s3Key; }

  public String getOriginalName() { return originalName; }
  public void setOriginalName(String originalName) { this.originalName = originalName; }

  public String getContentType() { return contentType; }
  public void setContentType(String contentType) { this.contentType = contentType; }

  public Long getFileSizeBytes() { return fileSizeBytes; }
  public void setFileSizeBytes(Long fileSizeBytes) { this.fileSizeBytes = fileSizeBytes; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public Long getUploadedByUserId() { return uploadedByUserId; }
  public void setUploadedByUserId(Long uploadedByUserId) { this.uploadedByUserId = uploadedByUserId; }
}
