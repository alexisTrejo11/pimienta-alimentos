package io.github.alexistrejo11.pimienta.shared;

import java.time.DayOfWeek;
import java.time.LocalTime;

/** One recurring weekly window for an employee work schedule. */
public record WorkDayScheduleSlot(
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime) {
}
