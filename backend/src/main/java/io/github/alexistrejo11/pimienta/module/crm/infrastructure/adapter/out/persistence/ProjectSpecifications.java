package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.ProjectSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

final class ProjectSpecifications {

  private ProjectSpecifications() {}

  static Specification<ProjectJpaEntity> fromCriteria(ProjectSearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));
      if (criteria != null) {
        if (criteria.status() != null) {
          parts.add(cb.equal(root.get("status"), criteria.status()));
        }
        if (criteria.clientId() != null) {
          parts.add(cb.equal(root.get("clientId"), criteria.clientId()));
        }
        if (criteria.projectManagerId() != null) {
          parts.add(cb.equal(root.get("projectManagerId"), criteria.projectManagerId()));
        }
        if (criteria.originOpportunityId() != null) {
          parts.add(cb.equal(root.get("originOpportunityId"), criteria.originOpportunityId()));
        }
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
