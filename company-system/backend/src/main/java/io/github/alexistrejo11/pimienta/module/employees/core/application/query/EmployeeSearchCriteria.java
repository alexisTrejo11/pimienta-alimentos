package io.github.alexistrejo11.pimienta.module.employees.core.application.query;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatus;

/**
 * Filters for listing employees; all fields are optional — omitted means no constraint on that
 * dimension.
 */
public record EmployeeSearchCriteria(EmployeeStatus status, String department, String text) {

  public static EmployeeSearchCriteria empty() {
    return new EmployeeSearchCriteria(null, null, null);
  }

  public static EmployeeSearchCriteria onlyActive() {
    return new EmployeeSearchCriteria(EmployeeStatus.ACTIVE, null, null);
  }
}
