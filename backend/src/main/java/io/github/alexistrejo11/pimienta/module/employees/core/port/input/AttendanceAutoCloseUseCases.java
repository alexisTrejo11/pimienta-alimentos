package io.github.alexistrejo11.pimienta.module.employees.core.port.input;

public interface AttendanceAutoCloseUseCases {

  /**
   * Closes attendances that were never checked out: caps at 12h with a dedicated status, or after
   * rolling past the work date applies an 8h assumed contract day when eligible.
   */
  void runStaleOpenAttendanceSweep();
}
