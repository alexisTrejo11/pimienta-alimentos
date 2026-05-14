package io.github.alexistrejo11.pimienta.module.employees.core.application.query;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.AttendanceStatus;
import java.time.LocalDate;

/**
 * Filters for attendance search; all fields are optional — omitted means no constraint on that
 * dimension.
 */
public record AttendanceSearchCriteria(
    Long employeeId,
    Long headquarterId,
    LocalDate workDate,
    LocalDate workDateFrom,
    LocalDate workDateTo,
    AttendanceStatus status,
    Boolean onlyOpen) {

  public static AttendanceSearchCriteria empty() {
    return new AttendanceSearchCriteria(null, null, null, null, null, null, null);
  }
}
