package io.github.alexistrejo11.pimienta.module.contract.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class ContractNotFoundException extends ResourceNotFoundException {

  public ContractNotFoundException(Long id) {
    super(
        ErrorCode.CONTRACT_NOT_FOUND,
        "The requested contract was not found.",
        Map.of("contractId", id),
        "Contract not found: id=" + id);
  }
}
