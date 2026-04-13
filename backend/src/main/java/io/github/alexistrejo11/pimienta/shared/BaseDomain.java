package io.github.alexistrejo11.pimienta.shared;

import java.time.LocalDateTime;

public abstract class BaseDomain<ID> {
  protected ID id;
  protected LocalDateTime createdAt;
  protected LocalDateTime updatedAt;
  protected LocalDateTime deletedAt;
  protected Long version;

  protected BaseDomain() {
  }

  protected BaseDomain(ID id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
      Long version) {
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
    this.version = version;
  }

  public ID getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public Long getVersion() {
    return version;
  }

  public void setId(ID id) {
    this.id = id;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
