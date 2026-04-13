package io.github.alexistrejo11.pimienta.module.employees.core.port;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository {

  Optional<Employee> findById(long id);

  Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable);

  Employee save(Employee employee);

  EmployeeStatistics statistics();

  EmployeeSummary summarize();
}
