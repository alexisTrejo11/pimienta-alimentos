package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.AttendanceSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.AttendanceStatus;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class AttendanceSpecifications {

  private AttendanceSpecifications() {}

  public static Specification<AttendanceJpaEntity> fromCriteria(AttendanceSearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));
      if (criteria != null) {
        if (criteria.employeeId() != null) {
          parts.add(cb.equal(root.get("employeeId"), criteria.employeeId()));
        }
        if (criteria.headquarterId() != null) {
          parts.add(cb.equal(root.get("headquarterId"), criteria.headquarterId()));
        }
        if (criteria.workDate() != null) {
          parts.add(cb.equal(root.get("workDate"), criteria.workDate()));
        }
        if (criteria.workDateFrom() != null) {
          parts.add(cb.greaterThanOrEqualTo(root.get("workDate"), criteria.workDateFrom()));
        }
        if (criteria.workDateTo() != null) {
          parts.add(cb.lessThanOrEqualTo(root.get("workDate"), criteria.workDateTo()));
        }
        if (criteria.status() != null) {
          parts.add(cb.equal(root.get("status"), criteria.status()));
        }
        if (criteria.onlyOpen() != null && criteria.onlyOpen()) {
          parts.add(cb.isNull(root.get("checkOutTime")));
          parts.add(cb.equal(root.get("status"), AttendanceStatus.CHECKED_IN));
        }
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
