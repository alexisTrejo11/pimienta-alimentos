package io.github.alexistrejo11.pimienta.module.task.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class TaskChecklistItemNotFoundException extends ResourceNotFoundException {

  public TaskChecklistItemNotFoundException(Long taskId, int displayOrder) {
    super(
        ErrorCode.TASK_CHECKLIST_ITEM_NOT_FOUND,
        "The requested checklist item was not found.",
        Map.of("taskId", taskId, "displayOrder", displayOrder),
        "Checklist item not found: taskId=" + taskId + " displayOrder=" + displayOrder);
  }
}
