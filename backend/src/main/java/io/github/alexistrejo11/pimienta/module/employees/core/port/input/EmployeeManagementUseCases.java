package io.github.alexistrejo11.pimienta.module.employees.core.port.input;

import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.param.RegisterEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.param.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeManagementUseCases {

  EmployeeStatistics statistics();

  EmployeeSummary summary();

  Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable);

  Employee getById(Long id);

  Employee register(RegisterEmployeeParams params);

  /**
   * Marca contratación efectiva cuando ya existe contrato laboral (llamado
   * típicamente desde el módulo de contratos).
   */
  Employee activateEmploymentAfterContract(Long employeeId, LocalDate hireDate);

  /** DRAFT → PENDING_CONTRACT. */
  Employee submitForContract(Long id);

  Employee update(Long id, UpdateEmployeeParams params);

  Employee terminate(Long id);

  Employee rehire(Long id);

  void delete(Long id);
}
