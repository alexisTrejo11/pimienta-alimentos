package io.github.alexistrejo11.pimienta.module.employees.core.port.output;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.AttendanceSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttendanceRepository {

  Optional<Attendance> findById(long id);

  Attendance save(Attendance attendance);

  Optional<Attendance> findOpenForEmployeeOnWorkDate(long employeeId, LocalDate workDate);

  /**
   * Paginated attendances for a calendar work date. When {@code headquarterId} is null, all
   * non-deleted rows for that date are returned.
   */
  Page<Attendance> findPageForWorkDate(LocalDate workDate, Long headquarterId, Pageable pageable);

  List<Attendance> findAllOpenCheckedIn();

  Page<Attendance> search(AttendanceSearchCriteria criteria, Pageable pageable);
}
