package io.github.alexistrejo11.pimienta.module.employees.core.port.output;

import java.util.Optional;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.EmployeeWorkSchedule;

/**
 * Outbound port: load or persist work schedule keyed by employee id (storage is
 * adapter-specific).
 */
public interface EmployeeWorkScheduleRepository {

  Optional<EmployeeWorkSchedule> findByEmployeeId(long employeeId);

  void replace(long employeeId, EmployeeWorkSchedule schedule);
}
