package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.out.persistence.jpa.HeadquarterJpaEntity;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * Maps between persistence {@link HeadquarterJpaEntity} and domain {@link Headquarter}. Null-safe on
 * read paths; {@link #toDomain} returns {@code null} when entity is {@code null}.
 */
@Component
public final class HeadquarterPersistenceMapper {

  private HeadquarterPersistenceMapper() {}

  public Headquarter toDomain(HeadquarterJpaEntity entity) {
    if (entity == null) {
      return null;
    }
    return Headquarter.reconstruct(
        entity.getId(),
        text(entity.getName()),
        text(entity.getAddress()),
        text(entity.getDescription()),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt(),
        entity.getVersion() != null ? entity.getVersion() : 0L);
  }

  public HeadquarterJpaEntity toEntity(Headquarter headquarter) {
    Objects.requireNonNull(headquarter, "headquarter");
    HeadquarterJpaEntity entity = new HeadquarterJpaEntity();
    Long domainId = headquarter.getId();
    if (domainId != null && domainId != 0L) {
      entity.setId(domainId);
    }
    entity.setName(text(headquarter.getName()));
    entity.setAddress(text(headquarter.getAddress()));
    entity.setDescription(text(headquarter.getDescription()));
    entity.setCreatedAt(headquarter.getCreatedAt() != null ? headquarter.getCreatedAt() : now());
    entity.setUpdatedAt(headquarter.getUpdatedAt() != null ? headquarter.getUpdatedAt() : now());
    entity.setDeletedAt(headquarter.getDeletedAt());
    entity.setVersion(headquarter.getVersion() != null ? headquarter.getVersion() : 0L);
    return entity;
  }

  private static String text(String s) {
    return s != null ? s : "";
  }

  private static java.time.LocalDateTime now() {
    return java.time.LocalDateTime.now();
  }
}
