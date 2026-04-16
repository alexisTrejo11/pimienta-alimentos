package io.github.alexistrejo11.pimienta.module.headquarter.core.domain;

import java.time.LocalDateTime;

public record HeadquarterReconstructParams(
    Long id,
    String name,
    String address,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    Long version) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Long id;
    private String name;
    private String address;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long version;

    private Builder() {}

    public Builder id(Long id) { this.id = id; return this; }
    public Builder name(String name) { this.name = name; return this; }
    public Builder address(String address) { this.address = address; return this; }
    public Builder description(String description) { this.description = description; return this; }
    public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
    public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
    public Builder deletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; return this; }
    public Builder version(Long version) { this.version = version; return this; }

    public HeadquarterReconstructParams build() {
      return new HeadquarterReconstructParams(
          id, name, address, description, createdAt, updatedAt, deletedAt, version);
    }
  }
}
