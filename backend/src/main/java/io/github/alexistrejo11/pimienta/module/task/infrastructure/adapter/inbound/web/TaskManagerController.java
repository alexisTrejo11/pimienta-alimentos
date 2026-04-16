package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.task.core.application.TaskManagementUseCases;
import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.CreateTaskParams;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.AssignTaskRequest;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.StatusUpdateRequest;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskListItemResponse;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskRequest;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskResponse;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskSearchRequest;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimit;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimitProfile;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RateLimit(profile = RateLimitProfile.STANDARD)
public class TaskManagerController {

  private final TaskManagementUseCases taskManagementUseCases;

  public TaskManagerController(TaskManagementUseCases taskManagementUseCases) {
    this.taskManagementUseCases = taskManagementUseCases;
  }

  /** Búsqueda global con filtros opcionales (sede, proyecto, empleado, estado). */
  @GetMapping
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public PagedResponse<TaskListItemResponse> searchTasks(@ModelAttribute TaskSearchRequest filter) {
    TaskSearchCriteria criteria = TaskManagerWebMapper.toCriteria(filter);
    Page<Task> page =
        taskManagementUseCases.search(
            criteria, PageRequest.of(filter.getPage(), filter.getSize()));
    return PagedResponse.map(page, TaskManagerWebMapper::toListItem);
  }

  @GetMapping("/{id}")
  public TaskResponse getTaskById(@PathVariable Long id) {
    Task task = taskManagementUseCases.getById(id);
    return TaskManagerWebMapper.toResponse(task);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponse createTask(@Valid @RequestBody TaskRequest request) {
    CreateTaskParams params = TaskManagerWebMapper.toCreateParams(request);
    Task created = taskManagementUseCases.create(params);
    return TaskManagerWebMapper.toResponse(created);
  }

  @PatchMapping("/{id}/status")
  public TaskResponse updateStatus(
      @PathVariable Long id, @Valid @RequestBody StatusUpdateRequest status) {
    Task updated = taskManagementUseCases.updateStatus(id, status.status());
    return TaskManagerWebMapper.toResponse(updated);
  }

  @PatchMapping("/{id}/assign")
  public TaskResponse assignTask(@PathVariable Long id, @Valid @RequestBody AssignTaskRequest request) {
    Task updated = taskManagementUseCases.assign(id, request.employeeId());
    return TaskManagerWebMapper.toResponse(updated);
  }

  @PatchMapping("/{id}/checklist/{itemOrder}/toggle")
  public TaskResponse toggleChecklistItem(@PathVariable Long id, @PathVariable int itemOrder) {
    Task updated = taskManagementUseCases.toggleChecklistItem(id, itemOrder);
    return TaskManagerWebMapper.toResponse(updated);
  }

  @DeleteMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskManagementUseCases.delete(id);
    return ResponseEntity.noContent().build();
  }
}
