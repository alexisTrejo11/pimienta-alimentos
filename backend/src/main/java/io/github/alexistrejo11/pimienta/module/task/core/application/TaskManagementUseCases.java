package io.github.alexistrejo11.pimienta.module.task.core.application;

import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.CreateTaskParams;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskManagementUseCases {

  Page<Task> search(TaskSearchCriteria criteria, Pageable pageable);

  Task getById(Long id);

  Task create(CreateTaskParams params);

  Task updateStatus(Long id, Task.Status newStatus);

  Task assign(Long id, Long employeeId);

  Task toggleChecklistItem(Long id, int itemOrder);

  void delete(Long id);
}
