package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventorySearchCriteria;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

final class InventorySpecifications {

  private InventorySpecifications() {}

  static Specification<InventoryJpaEntity> fromCriteria(InventorySearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));
      if (criteria != null) {
        if (criteria.itemId() != null) {
          parts.add(cb.equal(root.get("itemId"), criteria.itemId()));
        }
        if (criteria.locationId() != null) {
          parts.add(cb.equal(root.get("locationId"), criteria.locationId()));
        }
        if (criteria.status() != null) {
          parts.add(cb.equal(root.get("status"), criteria.status()));
        }
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
