package io.github.alexistrejo11.pimienta.module.employees.core.domain.enums;

/**
 * Lifecycle of a single workday attendance row. Auto-closed states are set by the hourly job when
 * the employee never checked out.
 */
public enum AttendanceStatus {
  UNDEFINED,
  CHECKED_IN,
  CHECKED_OUT,
  /** Open attendance exceeded the maximum allowed shift length; checkout capped at check-in + 12h. */
  AUTO_CLOSED_EXCEEDED_MAX_SHIFT_HOURS,
  /** Rolled past the work date with no checkout; recorded duration uses the default contract day (8h). */
  AUTO_CLOSED_ASSUMED_CONTRACT_DAY
}
