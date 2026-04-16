package io.github.alexistrejo11.pimienta.module.headquarter.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class HeadquarterNotFoundException extends ResourceNotFoundException {

  public HeadquarterNotFoundException(Long id) {
    super(
        ErrorCode.HEADQUARTER_NOT_FOUND,
        "The requested headquarter was not found.",
        Map.of("headquarterId", id),
        "Headquarter not found: id=" + id);
  }

  public HeadquarterNotFoundException(String name) {
    super(
        ErrorCode.HEADQUARTER_NOT_FOUND,
        "The requested headquarter was not found.",
        Map.of(),
        "Headquarter not found by name: " + name);
  }
}
