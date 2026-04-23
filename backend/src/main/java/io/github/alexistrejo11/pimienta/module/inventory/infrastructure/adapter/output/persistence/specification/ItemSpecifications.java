package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.specification;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.ItemSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.output.persistence.entity.ItemJpaEntity;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class ItemSpecifications {

  private ItemSpecifications() {
  }

  public static Specification<ItemJpaEntity> fromCriteria(ItemSearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));
      if (criteria != null) {
        if (criteria.name() != null && !criteria.name().isBlank()) {
          parts.add(
              cb.like(cb.lower(root.get("name")), "%" + criteria.name().trim().toLowerCase() + "%"));
        }
        if (criteria.sku() != null && !criteria.sku().isBlank()) {
          parts.add(
              cb.like(cb.lower(root.get("sku")), "%" + criteria.sku().trim().toLowerCase() + "%"));
        }
        if (criteria.category() != null) {
          parts.add(cb.equal(root.get("category"), criteria.category()));
        }
        if (criteria.status() != null) {
          parts.add(cb.equal(root.get("status"), criteria.status()));
        }
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
