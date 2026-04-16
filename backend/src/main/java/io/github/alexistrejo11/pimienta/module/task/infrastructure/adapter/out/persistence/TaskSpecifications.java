package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

final class TaskSpecifications {

  private static final List<Task.Status> CLOSED_STATUSES =
      List.of(Task.Status.COMPLETED, Task.Status.CANCELLED);

  private TaskSpecifications() {}

  /** Sin proyecto ni oportunidad CRM (tarea personal). */
  static Specification<TaskJpaEntity> personalNotDeleted() {
    return (root, query, cb) ->
        cb.and(
            cb.isNull(root.get("deletedAt")),
            cb.isNull(root.get("projectId")),
            cb.isNull(root.get("opportunityId")));
  }

  /** Ligada a proyecto u oportunidad. */
  static Specification<TaskJpaEntity> workNotDeleted() {
    return (root, query, cb) ->
        cb.and(
            cb.isNull(root.get("deletedAt")),
            cb.or(
                cb.isNotNull(root.get("projectId")), cb.isNotNull(root.get("opportunityId"))));
  }

  /** Excluye COMPLETED y CANCELLED (alineado con {@code countOpenByProjectId}). */
  static Specification<TaskJpaEntity> statusOpen() {
    return (root, query, cb) -> cb.not(root.get("status").in(CLOSED_STATUSES));
  }

  static Specification<TaskJpaEntity> statusPending() {
    return (root, query, cb) -> cb.equal(root.get("status"), Task.Status.PENDING);
  }

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
