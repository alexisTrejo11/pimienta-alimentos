package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Schema(name = "EmployeeWorkScheduleResponse", description = "Weekly work schedule slots for an employee.")
public record EmployeeWorkScheduleResponse(
    @Schema(description = "Weekly slots; empty when no schedule is configured.")
        List<WorkDayScheduleSlotResponse> slots) {

  @Schema(name = "WorkDayScheduleSlotResponse")
  public record WorkDayScheduleSlotResponse(
      @Schema(example = "MONDAY") DayOfWeek dayOfWeek,
      @Schema(type = "string", format = "time", example = "09:00") LocalTime startTime,
      @Schema(type = "string", format = "time", example = "18:00") LocalTime endTime) {}
}
