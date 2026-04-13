package io.github.alexistrejo11.pimienta.module.task.core.domain;

import java.time.LocalDateTime;

import io.github.alexistrejo11.pimienta.shared.BaseDomain;

public class Task extends BaseDomain<Long> {

  private String title;
  private String description;
  private String status;
  private String priority;
  private Long assignedToId;
  private Long assignedById;
  private LocalDateTime assignedAt;
  private LocalDateTime completedAt;
}
