package io.github.alexistrejo11.pimienta.module.task.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class TaskNotFoundException extends ResourceNotFoundException {

  public TaskNotFoundException(Long id) {
    super(
        ErrorCode.TASK_NOT_FOUND,
        "The requested task was not found.",
        Map.of("taskId", id),
        "Task not found: id=" + id);
  }
}
