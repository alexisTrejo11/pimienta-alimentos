package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {

  private EmployeeSpecifications() {}

  public static Specification<EmployeeJpaEntity> fromCriteria(EmployeeSearchCriteria criteria) {
    return (root, query, cb) -> {
      List<Predicate> parts = new ArrayList<>();
      parts.add(cb.isNull(root.get("deletedAt")));
      if (criteria != null) {
        if (criteria.status() != null) {
          parts.add(cb.equal(root.get("status"), criteria.status()));
        }
        if (criteria.department() != null && !criteria.department().isBlank()) {
          parts.add(
              cb.like(
                  cb.lower(root.get("employment").get("department")),
                  "%" + criteria.department().trim().toLowerCase() + "%"));
        }
        if (criteria.text() != null && !criteria.text().isBlank()) {
          String pattern = "%" + criteria.text().trim().toLowerCase() + "%";
          parts.add(
              cb.or(
                  cb.like(cb.lower(root.get("personal").get("fullName")), pattern),
                  cb.like(cb.lower(root.get("personal").get("email")), pattern),
                  cb.like(cb.lower(root.get("officialIds").get("employeeNumber")), pattern)));
        }
      }
      return cb.and(parts.toArray(Predicate[]::new));
    };
  }
}
