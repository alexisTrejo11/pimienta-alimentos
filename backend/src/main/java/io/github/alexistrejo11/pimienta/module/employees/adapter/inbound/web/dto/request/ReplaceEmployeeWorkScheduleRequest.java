package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Schema(name = "ReplaceEmployeeWorkScheduleRequest", description = "Full replacement of an employee weekly work schedule.")
public record ReplaceEmployeeWorkScheduleRequest(
        @NotNull @Valid @Schema(description = "Ordered list of weekly slots; may be empty to clear the schedule.") List<WorkDayScheduleSlotRequest> slots) {

    @Schema(name = "WorkDayScheduleSlotRequest", description = "Recurring weekly time window.")
    public record WorkDayScheduleSlotRequest(
            @NotNull @Schema(description = "Day of week for this window.", example = "MONDAY", requiredMode = Schema.RequiredMode.REQUIRED) DayOfWeek dayOfWeek,
            @NotNull @Schema(description = "Inclusive shift start (local time).", example = "09:00", type = "string", format = "time", requiredMode = Schema.RequiredMode.REQUIRED) LocalTime startTime,
            @NotNull @Schema(description = "Exclusive or inclusive end depending on client convention; must be after startTime.", example = "18:00", type = "string", format = "time", requiredMode = Schema.RequiredMode.REQUIRED) LocalTime endTime) {
    }
}
