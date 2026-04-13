package io.github.alexistrejo11.pimienta.module.employees.core.port;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.HireEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.UpdateEmployeeParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeManagementUseCases {

  EmployeeStatistics statistics();

  EmployeeSummary summary();

  Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable);

  Employee getById(Long id);

  Employee hire(HireEmployeeParams params);

  Employee update(Long id, UpdateEmployeeParams params);

  Employee terminate(Long id);

  Employee rehire(Long id);

  Employee changePosition(Long id, String newPosition);

  void delete(Long id);
}
