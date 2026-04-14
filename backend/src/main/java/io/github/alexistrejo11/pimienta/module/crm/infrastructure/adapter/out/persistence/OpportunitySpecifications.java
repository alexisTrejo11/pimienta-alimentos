package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.OpportunitySearchCriteria;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

final class OpportunitySpecifications {

  private OpportunitySpecifications() {}

  static Specification<OpportunityJpaEntity> fromCriteria(OpportunitySearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));
      if (criteria != null) {
        if (criteria.status() != null) {
          parts.add(cb.equal(root.get("status"), criteria.status()));
        }
        if (criteria.companyNameContains() != null && !criteria.companyNameContains().isBlank()) {
          parts.add(
              cb.like(
                  cb.lower(root.get("companyName")),
                  "%" + criteria.companyNameContains().toLowerCase() + "%"));
        }
        if (criteria.titleContains() != null && !criteria.titleContains().isBlank()) {
          parts.add(
              cb.like(
                  cb.lower(root.get("title")),
                  "%" + criteria.titleContains().toLowerCase() + "%"));
        }
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
