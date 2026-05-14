package io.github.alexistrejo11.pimienta.module.employees.core.application.usecase;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.exception.EmployeeNotFoundException;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.EmployeeWorkSchedule;
import io.github.alexistrejo11.pimienta.module.employees.core.port.input.EmployeeWorkScheduleUseCases;
import io.github.alexistrejo11.pimienta.module.employees.core.port.output.EmployeeWorkScheduleRepository;
import io.github.alexistrejo11.pimienta.shared.WorkDayScheduleSlot;

import java.time.LocalTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeWorkScheduleUseCasesImpl implements EmployeeWorkScheduleUseCases {

  private final EmployeeWorkScheduleRepository workScheduleRepository;

  public EmployeeWorkScheduleUseCasesImpl(EmployeeWorkScheduleRepository workScheduleRepository) {
    this.workScheduleRepository = workScheduleRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public EmployeeWorkSchedule getByEmployeeId(long employeeId) {
    return workScheduleRepository
        .findByEmployeeId(employeeId)
        .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
  }

  @Override
  @Transactional
  public EmployeeWorkSchedule replace(long employeeId, EmployeeWorkSchedule schedule) {
    assertTimeRangeIntegrity(schedule);
    workScheduleRepository.replace(employeeId, schedule);
    return getByEmployeeId(employeeId);
  }

  private static void assertTimeRangeIntegrity(EmployeeWorkSchedule schedule) {
    for (WorkDayScheduleSlot slot : schedule.slots()) {
      LocalTime start = slot.startTime();
      LocalTime end = slot.endTime();
      if (start == null || end == null || !start.isBefore(end)) {
        throw new IllegalArgumentException(
            "Work schedule startTime must be strictly before endTime for day " + slot.dayOfWeek());
      }
    }
  }
}
