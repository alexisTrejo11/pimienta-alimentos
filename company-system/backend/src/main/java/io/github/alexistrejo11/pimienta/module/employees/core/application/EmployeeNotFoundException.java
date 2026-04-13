package io.github.alexistrejo11.pimienta.module.employees.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class EmployeeNotFoundException extends ResourceNotFoundException {

  public EmployeeNotFoundException(Long id) {
    super(
        ErrorCode.EMPLOYEE_NOT_FOUND,
        "The requested employee was not found.",
        Map.of("employeeId", id),
        "Employee not found: id=" + id);
  }
}
