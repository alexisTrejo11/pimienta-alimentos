package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation;

final class StorageLocationPersistenceMapper {

  private StorageLocationPersistenceMapper() {}

  static StorageLocationJpaEntity toJpa(StorageLocation domain) {
    StorageLocationJpaEntity e = new StorageLocationJpaEntity();
    if (domain.getId() != null && domain.getId() > 0) {
      e.setId(domain.getId());
    }
    e.setCode(domain.getCode());
    e.setName(domain.getName());
    e.setDescription(domain.getDescription());
    e.setType(domain.getType());
    e.setParentId(domain.getParentId());
    e.setMaxCapacity(domain.getMaxCapacity());
    e.setOccupiedCapacity(domain.getOccupiedCapacity());
    e.setStatus(domain.getStatus());
    e.setCreatedAt(domain.getCreatedAt());
    e.setUpdatedAt(domain.getUpdatedAt());
    e.setDeletedAt(domain.getDeletedAt());
    e.setVersion(domain.getVersion() != null ? domain.getVersion() : 0L);
    return e;
  }

  static StorageLocation toDomain(StorageLocationJpaEntity e) {
    StorageLocation loc = new StorageLocation();
    loc.setId(e.getId());
    loc.setCode(e.getCode());
    loc.setName(e.getName());
    loc.setDescription(e.getDescription());
    loc.setType(e.getType());
    loc.setParentId(e.getParentId());
    loc.setMaxCapacity(e.getMaxCapacity());
    loc.setOccupiedCapacity(e.getOccupiedCapacity());
    loc.setStatus(e.getStatus());
    loc.setCreatedAt(e.getCreatedAt());
    loc.setUpdatedAt(e.getUpdatedAt());
    loc.setDeletedAt(e.getDeletedAt());
    loc.setVersion(e.getVersion());
    return loc;
  }
}
