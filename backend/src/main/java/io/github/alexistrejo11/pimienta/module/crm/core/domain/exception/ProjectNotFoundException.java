package io.github.alexistrejo11.pimienta.module.crm.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class ProjectNotFoundException extends ResourceNotFoundException {

  public ProjectNotFoundException(Long id) {
    super(
        ErrorCode.PROJECT_NOT_FOUND,
        "The requested project was not found.",
        Map.of("projectId", id),
        "Project not found: id=" + id);
  }
}
