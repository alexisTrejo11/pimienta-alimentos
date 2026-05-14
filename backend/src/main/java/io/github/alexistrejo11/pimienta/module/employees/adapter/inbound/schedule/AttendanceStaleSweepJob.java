package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.schedule;

import io.github.alexistrejo11.pimienta.module.employees.core.port.input.AttendanceAutoCloseUseCases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AttendanceStaleSweepJob {

  private static final Logger log = LoggerFactory.getLogger(AttendanceStaleSweepJob.class);

  private final AttendanceAutoCloseUseCases attendanceAutoCloseUseCases;

  public AttendanceStaleSweepJob(AttendanceAutoCloseUseCases attendanceAutoCloseUseCases) {
    this.attendanceAutoCloseUseCases = attendanceAutoCloseUseCases;
  }

  /** Runs at the start of every clock hour. */
  @Scheduled(cron = "0 0 * * * *")
  public void runHourly() {
    log.debug("attendance stale sweep job start");
    attendanceAutoCloseUseCases.runStaleOpenAttendanceSweep();
    log.debug("attendance stale sweep job end");
  }
}
