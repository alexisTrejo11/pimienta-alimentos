package io.github.alexistrejo11.pimienta.module.employees.core.domain.model;

import java.util.List;

import io.github.alexistrejo11.pimienta.shared.WorkDayScheduleSlot;

/**
 * Weekly work schedule for an employee
 */
public record EmployeeWorkSchedule(List<WorkDayScheduleSlot> slots) {

  public EmployeeWorkSchedule {
    slots = slots == null ? List.of() : List.copyOf(slots);
  }

  public static EmployeeWorkSchedule empty() {
    return new EmployeeWorkSchedule(List.of());
  }
}
