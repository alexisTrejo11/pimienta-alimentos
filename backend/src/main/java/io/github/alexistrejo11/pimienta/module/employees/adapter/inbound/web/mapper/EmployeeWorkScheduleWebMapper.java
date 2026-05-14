package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.ReplaceEmployeeWorkScheduleRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.ReplaceEmployeeWorkScheduleRequest.WorkDayScheduleSlotRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeWorkScheduleResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeWorkScheduleResponse.WorkDayScheduleSlotResponse;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.EmployeeWorkSchedule;
import io.github.alexistrejo11.pimienta.shared.WorkDayScheduleSlot;

import java.util.List;

public final class EmployeeWorkScheduleWebMapper {

  private EmployeeWorkScheduleWebMapper() {
  }

  public static EmployeeWorkSchedule toDomain(ReplaceEmployeeWorkScheduleRequest request) {
    if (request == null || request.slots() == null) {
      return EmployeeWorkSchedule.empty();
    }
    List<WorkDayScheduleSlot> slots = request.slots().stream()
        .map(EmployeeWorkScheduleWebMapper::toDomainSlot)
        .toList();
    return new EmployeeWorkSchedule(slots);
  }

  public static EmployeeWorkScheduleResponse toResponse(EmployeeWorkSchedule schedule) {
    if (schedule == null) {
      return new EmployeeWorkScheduleResponse(List.of());
    }
    List<WorkDayScheduleSlotResponse> slots = schedule.slots().stream()
        .map(
            s -> new WorkDayScheduleSlotResponse(
                s.dayOfWeek(), s.startTime(), s.endTime()))
        .toList();
    return new EmployeeWorkScheduleResponse(slots);
  }

  private static WorkDayScheduleSlot toDomainSlot(WorkDayScheduleSlotRequest r) {
    return new WorkDayScheduleSlot(r.dayOfWeek(), r.startTime(), r.endTime());
  }
}
