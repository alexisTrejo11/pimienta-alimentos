package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

final class TaskSpecifications {

  private TaskSpecifications() {}

  static Specification<TaskJpaEntity> fromCriteria(TaskSearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));
      if (criteria != null) {
        if (criteria.headquarterId() != null) {
          parts.add(cb.equal(root.get("headquarterId"), criteria.headquarterId()));
        }
        if (criteria.projectId() != null) {
          parts.add(cb.equal(root.get("projectId"), criteria.projectId()));
        }
        if (criteria.opportunityId() != null) {
          parts.add(cb.equal(root.get("opportunityId"), criteria.opportunityId()));
        }
        if (criteria.assignedToId() != null) {
          parts.add(cb.equal(root.get("assignedToId"), criteria.assignedToId()));
        }
        if (criteria.status() != null) {
          parts.add(cb.equal(root.get("status"), criteria.status()));
        }
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
