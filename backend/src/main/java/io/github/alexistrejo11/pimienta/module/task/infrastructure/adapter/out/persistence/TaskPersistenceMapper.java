package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.task.core.domain.ReconstructTaskParams;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import java.util.ArrayList;
import java.util.List;

final class TaskPersistenceMapper {

  private TaskPersistenceMapper() {}

  static TaskJpaEntity toJpa(Task domain) {
    TaskJpaEntity e = new TaskJpaEntity();
    if (domain.getId() != null && domain.getId() > 0) {
      e.setId(domain.getId());
    }
    e.setTitle(domain.getTitle());
    e.setDescription(domain.getDescription());
    e.setStatus(domain.getStatus());
    e.setPriority(domain.getPriority());
    e.setAssignedToId(domain.getAssignedToId());
    e.setAssignedById(domain.getAssignedById());
    e.setCreatedById(domain.getCreatedById());
    e.setAssignedAt(domain.getAssignedAt());
    e.setCompletedAt(domain.getCompletedAt());
    e.setDueDate(domain.getDueDate());
    e.setHeadquarterId(domain.getHeadquarterId());
    e.setProjectId(domain.getProjectId());
    List<ChecklistItemJson> jsonItems = new ArrayList<>();
    for (Task.ChecklistItem item : domain.getChecklist()) {
      jsonItems.add(
          new ChecklistItemJson(
              item.description(),
              item.isCompleted(),
              item.completedAt(),
              item.displayOrder()));
    }
    e.setChecklist(jsonItems);
    e.setCreatedAt(domain.getCreatedAt());
    e.setUpdatedAt(domain.getUpdatedAt());
    e.setDeletedAt(domain.getDeletedAt());
    e.setVersion(domain.getVersion() != null ? domain.getVersion() : 0L);
    return e;
  }

  static Task toDomain(TaskJpaEntity e) {
    List<Task.ChecklistItem> items = new ArrayList<>();
    if (e.getChecklist() != null) {
      for (ChecklistItemJson j : e.getChecklist()) {
        items.add(
            new Task.ChecklistItem(
                j.description() != null ? j.description() : "",
                j.isCompleted(),
                j.completedAt(),
                j.displayOrder()));
      }
    }
    return Task.reconstruct(
        new ReconstructTaskParams(
            e.getId(),
            e.getTitle(),
            e.getDescription(),
            e.getStatus(),
            e.getPriority(),
            e.getAssignedToId(),
            e.getAssignedById(),
            e.getCreatedById(),
            e.getAssignedAt(),
            e.getCompletedAt(),
            e.getDueDate(),
            e.getHeadquarterId(),
            e.getProjectId(),
            items,
            e.getCreatedAt(),
            e.getUpdatedAt(),
            e.getDeletedAt(),
            e.getVersion()));
  }
}
