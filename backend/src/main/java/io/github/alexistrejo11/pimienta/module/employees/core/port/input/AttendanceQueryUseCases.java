package io.github.alexistrejo11.pimienta.module.employees.core.port.input;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.AttendanceSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Attendance;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttendanceQueryUseCases {

  Attendance getById(Long id);

  /**
   * Paginated attendances for the current calendar work date. When {@code headquarterId} is null,
   * returns all sites; otherwise filters by headquarter.
   */
  Page<Attendance> listForToday(Long headquarterId, Pageable pageable);

  /**
   * Paginated attendances for one employee, optionally constrained by inclusive work-date range.
   */
  Page<Attendance> listByEmployee(long employeeId, LocalDate workDateFrom, LocalDate workDateTo, Pageable pageable);

  Page<Attendance> search(AttendanceSearchCriteria criteria, Pageable pageable);
}
