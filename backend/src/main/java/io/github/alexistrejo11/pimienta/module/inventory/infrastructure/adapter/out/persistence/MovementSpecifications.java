package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryMovementSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

final class MovementSpecifications {

  private MovementSpecifications() {}

  static Specification<InventoryMovementJpaEntity> fromCriteria(InventoryMovementSearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      if (criteria != null) {
        if (criteria.type() != null) {
          parts.add(cb.equal(root.get("type"), criteria.type()));
        }
        if (criteria.direction() != null) {
          parts.add(cb.equal(root.get("direction"), criteria.direction()));
        }
        if (criteria.itemId() != null) {
          parts.add(cb.equal(root.get("itemId"), criteria.itemId()));
        }
        if (criteria.locationId() != null) {
          Long id = criteria.locationId();
          parts.add(
              cb.or(
                  cb.equal(root.get("sourceLocationId"), id),
                  cb.equal(root.get("destinationLocationId"), id)));
        }
        if (criteria.fromDate() != null) {
          parts.add(cb.greaterThanOrEqualTo(root.get("createdAt"), criteria.fromDate()));
        }
        if (criteria.toDate() != null) {
          parts.add(cb.lessThanOrEqualTo(root.get("createdAt"), criteria.toDate()));
        }
      }
      if (parts.isEmpty()) {
        return cb.conjunction();
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
