package io.github.alexistrejo11.pimienta.module.files.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.files.core.application.query.FileAssetSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class FileAssetSpecifications {

  private FileAssetSpecifications() {}

  public static Specification<FileAssetJpaEntity> fromCriteria(FileAssetSearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));

      if (criteria == null) {
        return cb.and(parts.toArray(Predicate[]::new));
      }
      if (criteria.category() != null) {
        parts.add(cb.equal(root.get("category"), criteria.category()));
      }
      if (criteria.module() != null && !criteria.module().isBlank()) {
        parts.add(cb.equal(root.get("module"), criteria.module().trim()));
      }
      if (criteria.entityType() != null && !criteria.entityType().isBlank()) {
        parts.add(cb.equal(root.get("entityType"), criteria.entityType().trim()));
      }
      if (criteria.entityId() != null) {
        parts.add(cb.equal(root.get("entityId"), criteria.entityId()));
      }
      if (criteria.originalNameContains() != null && !criteria.originalNameContains().isBlank()) {
        parts.add(
            cb.like(
                cb.lower(root.get("originalName")),
                "%" + criteria.originalNameContains().trim().toLowerCase() + "%"));
      }
      if (criteria.contentTypeContains() != null && !criteria.contentTypeContains().isBlank()) {
        parts.add(
            cb.like(
                cb.lower(root.get("contentType")),
                "%" + criteria.contentTypeContains().trim().toLowerCase() + "%"));
      }
      if (criteria.uploadedByUserId() != null) {
        parts.add(cb.equal(root.get("uploadedByUserId"), criteria.uploadedByUserId()));
      }
      if (criteria.createdFrom() != null) {
        parts.add(cb.greaterThanOrEqualTo(root.get("createdAt"), criteria.createdFrom()));
      }
      if (criteria.createdTo() != null) {
        parts.add(cb.lessThanOrEqualTo(root.get("createdAt"), criteria.createdTo()));
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
