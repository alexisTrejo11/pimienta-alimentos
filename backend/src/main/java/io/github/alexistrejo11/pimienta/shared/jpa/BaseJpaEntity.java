package io.github.alexistrejo11.pimienta.shared.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

/**
 * Auditing fields shared by JPA entities. Domain aggregates guarantee non-null
 * timestamps; rows from legacy imports or partial writes may have nulls—use
 * the helpers before persist when needed.
 */
@MappedSuperclass
public abstract class BaseJpaEntity {

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(nullable = false)
  private Long version;

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  /** Sets {@code createdAt} and {@code updatedAt} to {@code now} when null (new row). */
  public void fillCreatedAndUpdatedIfNull() {
    LocalDateTime now = LocalDateTime.now();
    if (createdAt == null) {
      createdAt = now;
    }
    if (updatedAt == null) {
      updatedAt = now;
    }
  }

  /** Sets {@code updatedAt} to {@code now} when null (e.g. partial legacy row). */
  public void fillUpdatedIfNull() {
    if (updatedAt == null) {
      updatedAt = LocalDateTime.now();
    }
  }

  public void normalizeVersionIfNull() {
    if (version == null) {
      version = 0L;
    }
  }
}
