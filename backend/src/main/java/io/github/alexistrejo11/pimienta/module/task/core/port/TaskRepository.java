package io.github.alexistrejo11.pimienta.module.task.core.port;

import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepository {

  Optional<Task> findById(long id);

  Page<Task> search(TaskSearchCriteria criteria, Pageable pageable);

  Task save(Task task);

  /** Tareas no borradas ligadas a una oportunidad CRM. */
  long countByOpportunityId(long opportunityId);

  /** Excluye COMPLETED y CANCELLED (seguimiento de trabajo pendiente). */
  long countOpenByOpportunityId(long opportunityId);

  long countByProjectId(long projectId);

  long countOpenByProjectId(long projectId);

  /**
   * Tareas personales (sin proyecto ni oportunidad), no borradas y aún abiertas (no COMPLETED ni
   * CANCELLED).
   */
  long countOpenPersonalTasks();

  /** Tareas personales en estado PENDING. */
  long countPendingPersonalTasks();

  /**
   * Tareas de trabajo (ligadas a proyecto u oportunidad), abiertas (no COMPLETED ni CANCELLED).
   */
  long countOpenWorkTasks();

  /** Tareas de trabajo en estado PENDING. */
  long countPendingWorkTasks();
}
