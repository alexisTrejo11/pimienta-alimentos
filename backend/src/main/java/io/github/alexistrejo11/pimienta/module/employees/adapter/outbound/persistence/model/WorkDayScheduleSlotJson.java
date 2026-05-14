package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record WorkDayScheduleSlotJson(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {}
