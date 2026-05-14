package io.github.alexistrejo11.pimienta.module.employees.core.port.input;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.AttendanceSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Attendance;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttendanceQueryUseCases {

  Attendance getById(Long id);

  /**
   * All attendance rows for a headquarter on the current calendar day (site
   * default timezone).
   */
  List<Attendance> listForHeadquarterAndToday(Long headquarterId);

  Page<Attendance> search(AttendanceSearchCriteria criteria, Pageable pageable);
}
