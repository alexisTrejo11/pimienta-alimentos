package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.specification;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryTransactionSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.entity.InventoryTransactionJpaEntity;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class TransactionSpecifications {

  private TransactionSpecifications() {
  }

  public static Specification<InventoryTransactionJpaEntity> fromCriteria(
      InventoryTransactionSearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));
      if (criteria != null) {
        if (criteria.type() != null) {
          parts.add(cb.equal(root.get("type"), criteria.type()));
        }
        if (criteria.status() != null) {
          parts.add(cb.equal(root.get("status"), criteria.status()));
        }
        if (criteria.fromDate() != null) {
          parts.add(cb.greaterThanOrEqualTo(root.get("createdAt"), criteria.fromDate()));
        }
        if (criteria.toDate() != null) {
          parts.add(cb.lessThanOrEqualTo(root.get("createdAt"), criteria.toDate()));
        }
        if (criteria.initiatedById() != null) {
          parts.add(cb.equal(root.get("initiatedById"), criteria.initiatedById()));
        }
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
