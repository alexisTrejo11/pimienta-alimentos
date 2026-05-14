package io.github.alexistrejo11.pimienta.module.employees.core.port.output;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Employee;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository {

  Optional<Employee> findById(long id);

  Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable);

  Employee save(Employee employee);

  long countActive();

  EmployeeStatistics statistics();

  EmployeeSummary summarize();
}
