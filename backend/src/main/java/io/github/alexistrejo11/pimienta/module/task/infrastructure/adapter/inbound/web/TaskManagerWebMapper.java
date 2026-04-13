package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.CreateChecklistLine;
import io.github.alexistrejo11.pimienta.module.task.core.domain.CreateTaskParams;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.ChecklistItemResponse;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.ChecklistLineRequest;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskListItemResponse;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskRequest;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskResponse;
import io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.inbound.web.dto.TaskSearchRequest;
import java.util.ArrayList;
import java.util.List;

final class TaskManagerWebMapper {

  private TaskManagerWebMapper() {}

  static TaskSearchCriteria toCriteria(TaskSearchRequest request) {
    return new TaskSearchCriteria(
        request.getHeadquarterId(),
        request.getProjectId(),
        request.getEmployeeId(),
        request.getStatus());
  }

  static CreateTaskParams toCreateParams(TaskRequest request) {
    List<CreateChecklistLine> lines = new ArrayList<>();
    if (request.checklist() != null) {
      for (ChecklistLineRequest line : request.checklist()) {
        lines.add(new CreateChecklistLine(line.description(), line.displayOrder()));
      }
    }
    return new CreateTaskParams(
        request.title(),
        request.description(),
        request.priority(),
        request.dueDate(),
        request.headquarterId(),
        request.projectId(),
        request.createdById(),
        lines);
  }

  static TaskResponse toResponse(Task task) {
    List<ChecklistItemResponse> checklist = new ArrayList<>();
    for (Task.ChecklistItem item : task.getChecklist()) {
      checklist.add(
          new ChecklistItemResponse(
              item.description(),
              item.isCompleted(),
              item.completedAt(),
              item.displayOrder()));
    }
    return new TaskResponse(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getStatus(),
        task.getPriority(),
        task.getAssignedToId(),
        task.getAssignedById(),
        task.getCreatedById(),
        task.getAssignedAt(),
        task.getCompletedAt(),
        task.getDueDate(),
        task.getHeadquarterId(),
        task.getProjectId(),
        checklist,
        task.getProgressPercentage(),
        task.getCreatedAt(),
        task.getUpdatedAt());
  }

  static TaskListItemResponse toListItem(Task task) {
    return new TaskListItemResponse(
        task.getId(),
        task.getTitle(),
        task.getStatus(),
        task.getPriority(),
        task.getAssignedToId(),
        task.getDueDate(),
        task.getHeadquarterId(),
        task.getProjectId(),
        task.getProgressPercentage());
  }
}
