package io.github.alexistrejo11.pimienta.module.employees.core.port.input;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.EmployeeWorkSchedule;

public interface EmployeeWorkScheduleUseCases {

  EmployeeWorkSchedule getByEmployeeId(long employeeId);

  EmployeeWorkSchedule replace(long employeeId, EmployeeWorkSchedule schedule);
}
