package io.github.alexistrejo11.pimienta.module.headquarter.core.domain;

import java.time.LocalDateTime;

import io.github.alexistrejo11.pimienta.shared.BaseDomain;

public class Headquarter extends BaseDomain<Long> {
  private String name;
  private String address;
  private String description;

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getDescription() {
    return description;
  }

  private Headquarter(Long id, String name, String address, String description, LocalDateTime createdAt,
      LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(id, createdAt, updatedAt, deletedAt, 0L);
    this.name = name;
    this.address = address;
    this.description = description;
  }

  public Headquarter() {
    super();
    this.id = null;
    this.name = "";
    this.address = "";
    this.description = "";
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.deletedAt = null;
    this.version = 0L;
  }

  public static Headquarter create(String name, String address, String description) {
    var now = LocalDateTime.now();
    return new Headquarter(0L, name, address, description, now, now, null);
  }

  /**
   * Load from persistence: no business validation, null-safe field assignment.
   */
  public static Headquarter reconstruct(
      Long id,
      String name,
      String address,
      String description,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt,
      Long version) {
    var h = new Headquarter();
    h.id = id != null ? id : 0L;
    h.name = name != null ? name : "";
    h.address = address != null ? address : "";
    h.description = description != null ? description : "";
    h.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    h.updatedAt = updatedAt != null ? updatedAt : h.createdAt;
    h.deletedAt = deletedAt;
    h.version = version != null ? version : 0L;
    return h;
  }

  /**
   * New aggregate state for an update: preserves identity, created/deleted
   * markers, and bumps version.
   */
  public static Headquarter revise(Headquarter existing, String name, String address, String description) {
    var h = new Headquarter();
    h.id = existing.id;
    h.createdAt = existing.createdAt;
    h.deletedAt = existing.deletedAt;
    h.name = name;
    h.address = address;
    h.description = description;
    h.updatedAt = LocalDateTime.now();
    h.version = existing.version + 1;
    return h;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
    this.version += 1;
  }
}
