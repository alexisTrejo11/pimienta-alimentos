package io.github.alexistrejo11.pimienta.module.task.core.application;

import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.CreateTaskParams;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import io.github.alexistrejo11.pimienta.module.task.core.domain.exception.TaskChecklistItemNotFoundException;
import io.github.alexistrejo11.pimienta.module.task.core.domain.exception.TaskNotFoundException;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskRepository;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskManagementUseCasesImpl implements TaskManagementUseCases {

  private final TaskRepository taskRepository;

  public TaskManagementUseCasesImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public Page<Task> search(TaskSearchCriteria criteria, Pageable pageable) {
    TaskSearchCriteria effective = criteria != null ? criteria : TaskSearchCriteria.empty();
    return taskRepository.search(effective, pageable);
  }

  @Override
  public Task getById(Long id) {
    return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
  }

  @Override
  public Task create(CreateTaskParams params) {
    Task created = Task.create(params);
    return taskRepository.save(created);
  }

  @Override
  public Task updateStatus(Long id, Task.Status newStatus) {
    Task task = getById(id);
    task.transitionTo(newStatus, LocalDateTime.now());
    return taskRepository.save(task);
  }

  @Override
  public Task assign(Long id, Long employeeId) {
    Task task = getById(id);
    task.assignTo(employeeId);
    return taskRepository.save(task);
  }

  @Override
  public Task toggleChecklistItem(Long id, int itemOrder) {
    Task task = getById(id);
    if (!task.toggleChecklistItem(itemOrder)) {
      throw new TaskChecklistItemNotFoundException(id, itemOrder);
    }
    return taskRepository.save(task);
  }

  @Override
  public void delete(Long id) {
    Task task = getById(id);
    task.delete();
    taskRepository.save(task);
  }
}
