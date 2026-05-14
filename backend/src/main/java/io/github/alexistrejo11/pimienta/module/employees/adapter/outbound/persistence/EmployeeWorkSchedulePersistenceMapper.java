package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence;

import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.EmployeeWorkScheduleJson;
import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.WorkDayScheduleSlotJson;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.EmployeeWorkSchedule;
import io.github.alexistrejo11.pimienta.shared.WorkDayScheduleSlot;

import java.util.List;

public final class EmployeeWorkSchedulePersistenceMapper {

  private EmployeeWorkSchedulePersistenceMapper() {
  }

  public static EmployeeWorkScheduleJson toJson(EmployeeWorkSchedule domain) {
    if (domain == null || domain.slots().isEmpty()) {
      return new EmployeeWorkScheduleJson(List.of());
    }
    List<WorkDayScheduleSlotJson> slots = domain.slots().stream()
        .map(s -> new WorkDayScheduleSlotJson(s.dayOfWeek(), s.startTime(), s.endTime()))
        .toList();
    return new EmployeeWorkScheduleJson(slots);
  }

  public static EmployeeWorkSchedule toDomain(EmployeeWorkScheduleJson json) {
    if (json == null || json.slots().isEmpty()) {
      return EmployeeWorkSchedule.empty();
    }
    List<WorkDayScheduleSlot> slots = json.slots().stream()
        .map(s -> new WorkDayScheduleSlot(s.dayOfWeek(), s.startTime(), s.endTime()))
        .toList();
    return new EmployeeWorkSchedule(slots);
  }
}
