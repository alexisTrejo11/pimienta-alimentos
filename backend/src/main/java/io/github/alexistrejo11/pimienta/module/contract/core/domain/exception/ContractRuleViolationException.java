package io.github.alexistrejo11.pimienta.module.contract.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.BusinessValidationException;
import java.util.Map;

/** Thrown when a contract aggregate violates its own invariants or lifecycle rules. */
public class ContractRuleViolationException extends BusinessValidationException {

  public ContractRuleViolationException(String clientMessage, Map<String, Object> context, String logDetails) {
    super(clientMessage, context, logDetails);
  }
}
