package io.github.alexistrejo11.pimienta.module.task.core.domain;

import io.github.alexistrejo11.pimienta.shared.BaseDomain;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task extends BaseDomain<Long> {

  private String title;
  private String description;
  private Status status;
  private Priority priority;
  private Long assignedToId;
  private Long assignedById;
  private Long createdById;
  private LocalDateTime assignedAt;
  private LocalDateTime completedAt;
  private LocalDateTime dueDate;
  private Long headquarterId;
  private Long projectId;
  private List<ChecklistItem> checklist = new ArrayList<>();

  public enum Status {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
    DELAYED,
    ON_HOLD,
    FAILED
  }

  public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
  }

  public record ChecklistItem(
      String description, boolean isCompleted, LocalDateTime completedAt, int displayOrder) {

    public ChecklistItem(String description, int displayOrder) {
      this(description, false, null, displayOrder);
    }

    public ChecklistItem complete() {
      if (isCompleted) {
        return this;
      }
      return new ChecklistItem(description, true, LocalDateTime.now(), displayOrder);
    }

    public ChecklistItem toggleComplete() {
      if (isCompleted) {
        return new ChecklistItem(description, false, null, displayOrder);
      }
      return complete();
    }
  }

  private Task() {}

  public static Task create(CreateTaskParams params) {
    LocalDateTime now = LocalDateTime.now();
    Task task = new Task();
    task.title = params.title() != null ? params.title().trim() : "";
    task.description = params.description() != null ? params.description().trim() : "";
    task.priority = params.priority() != null ? params.priority() : Priority.MEDIUM;
    task.dueDate = params.dueDate();
    task.headquarterId = params.headquarterId();
    task.projectId = params.projectId();
    task.createdById = params.createdById();
    task.status = Status.PENDING;
    task.checklist = new ArrayList<>();
    if (params.checklistLines() != null) {
      for (CreateChecklistLine line : params.checklistLines()) {
        task.checklist.add(new ChecklistItem(line.description(), line.displayOrder()));
      }
    }
    task.createdAt = now;
    task.updatedAt = now;
    task.deletedAt = null;
    task.version = 0L;
    return task;
  }

  public static Task reconstruct(ReconstructTaskParams params) {
    Task task = new Task();
    task.id = params.id();
    task.title = params.title() != null ? params.title() : "";
    task.description = params.description() != null ? params.description() : "";
    task.status = params.status() != null ? params.status() : Status.PENDING;
    task.priority = params.priority() != null ? params.priority() : Priority.MEDIUM;
    task.assignedToId = params.assignedToId();
    task.assignedById = params.assignedById();
    task.createdById = params.createdById();
    task.assignedAt = params.assignedAt();
    task.completedAt = params.completedAt();
    task.dueDate = params.dueDate();
    task.headquarterId = params.headquarterId();
    task.projectId = params.projectId();
    task.checklist = new ArrayList<>();
    if (params.checklist() != null) {
      task.checklist.addAll(params.checklist());
    }
    task.createdAt = params.createdAt() != null ? params.createdAt() : LocalDateTime.now();
    task.updatedAt = params.updatedAt() != null ? params.updatedAt() : task.createdAt;
    task.deletedAt = params.deletedAt();
    task.version = params.version() != null ? params.version() : 0L;
    return task;
  }

  public void transitionTo(Status newStatus, LocalDateTime when) {
    LocalDateTime t = when != null ? when : LocalDateTime.now();
    this.status = newStatus != null ? newStatus : Status.PENDING;
    if (this.status == Status.COMPLETED) {
      this.completedAt = t;
    } else {
      this.completedAt = null;
    }
    touch();
  }

  public void assignTo(Long employeeId) {
    this.assignedToId = employeeId;
    this.assignedAt = LocalDateTime.now();
    if (this.status == Status.PENDING) {
      this.status = Status.IN_PROGRESS;
    }
    touch();
  }

  /** @return {@code true} si existía un ítem con ese {@code displayOrder} */
  public boolean toggleChecklistItem(int displayOrder) {
    for (int i = 0; i < checklist.size(); i++) {
      ChecklistItem item = checklist.get(i);
      if (item.displayOrder() == displayOrder) {
        checklist.set(i, item.toggleComplete());
        touch();
        return true;
      }
    }
    return false;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
    touch();
  }

  private void touch() {
    this.updatedAt = LocalDateTime.now();
    this.version = this.version != null ? this.version + 1 : 1L;
  }

  public double getProgressPercentage() {
    if (checklist.isEmpty()) {
      return 0;
    }
    long completedCount = checklist.stream().filter(ChecklistItem::isCompleted).count();
    return (double) completedCount / checklist.size() * 100;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Status getStatus() {
    return status;
  }

  public Priority getPriority() {
    return priority;
  }

  public Long getAssignedToId() {
    return assignedToId;
  }

  public Long getAssignedById() {
    return assignedById;
  }

  public Long getCreatedById() {
    return createdById;
  }

  public LocalDateTime getAssignedAt() {
    return assignedAt;
  }

  public LocalDateTime getCompletedAt() {
    return completedAt;
  }

  public LocalDateTime getDueDate() {
    return dueDate;
  }

  public Long getHeadquarterId() {
    return headquarterId;
  }

  public Long getProjectId() {
    return projectId;
  }

  public List<ChecklistItem> getChecklist() {
    return Collections.unmodifiableList(checklist);
  }
}
