package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import java.util.Map;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;

public record Schedule(Map<DayOfWeek, ScheduleEntry> schedule) {
  public record ScheduleEntry(
      LocalTime startTime,
      LocalTime endTime,
      LocalTime lunchStartTime,
      LocalTime lunchEndTime,
      boolean isOvertimeAllowed) {
  }

  public void validate() {
    for (var entry : schedule.entrySet()) {
      var seenDays = new HashSet<DayOfWeek>();
      var dayOfWeek = entry.getKey();
      var scheduleEntry = entry.getValue();

      // Validate that the start time is before the end time
      if (scheduleEntry.startTime().isAfter(scheduleEntry.endTime())) {
        throw new IllegalArgumentException("Start time must be before end time");
      }
      if (scheduleEntry.lunchStartTime().isAfter(scheduleEntry.lunchEndTime())) {
        throw new IllegalArgumentException("Lunch start time must be before lunch end time");
      }
      if (scheduleEntry.startTime().isAfter(scheduleEntry.lunchStartTime())) {
        throw new IllegalArgumentException("Start time must be before lunch start time");
      }
      if (scheduleEntry.endTime().isBefore(scheduleEntry.lunchEndTime())) {
        throw new IllegalArgumentException("End time must be after lunch end time");
      }

      // Validate one entry per day of week
      if (seenDays.contains(dayOfWeek)) {
        throw new IllegalArgumentException("One entry per day of week is allowed");
      }
      seenDays.add(dayOfWeek);
    }

    // Validate that the schedule is not empty
    if (schedule.isEmpty()) {
      throw new IllegalArgumentException("Schedule cannot be empty");
    }
  }
}
