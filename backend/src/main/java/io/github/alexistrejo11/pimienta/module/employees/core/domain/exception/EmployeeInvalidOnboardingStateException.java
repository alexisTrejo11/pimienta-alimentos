package io.github.alexistrejo11.pimienta.module.employees.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.BusinessValidationException;
import java.util.Map;

public class EmployeeInvalidOnboardingStateException extends BusinessValidationException {

  public EmployeeInvalidOnboardingStateException(String clientMessage, Map<String, Object> context, String logDetails) {
    super(clientMessage, context, logDetails);
  }
}
