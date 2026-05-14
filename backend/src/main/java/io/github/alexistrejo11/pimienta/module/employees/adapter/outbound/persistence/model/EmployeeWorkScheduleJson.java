package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import java.util.List;

public record EmployeeWorkScheduleJson(List<WorkDayScheduleSlotJson> slots) {

  public EmployeeWorkScheduleJson {
    slots = slots == null ? List.of() : List.copyOf(slots);
  }
}
