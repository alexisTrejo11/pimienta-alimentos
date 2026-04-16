package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskJpaRepository jpaRepository;

  public TaskRepositoryImpl(TaskJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Optional<Task> findById(long id) {
    return jpaRepository.findByIdAndDeletedAtIsNull(id).map(TaskPersistenceMapper::toDomain);
  }

  @Override
  public Page<Task> search(TaskSearchCriteria criteria, Pageable pageable) {
    Specification<TaskJpaEntity> spec = TaskSpecifications.fromCriteria(criteria);
    return jpaRepository.findAll(spec, pageable).map(TaskPersistenceMapper::toDomain);
  }

  @Override
  public Task save(Task task) {
    TaskJpaEntity entity = TaskPersistenceMapper.toJpa(task);
    TaskJpaEntity saved = jpaRepository.save(entity);
    return TaskPersistenceMapper.toDomain(saved);
  }

  @Override
  public long countByOpportunityId(long opportunityId) {
    return jpaRepository.countByOpportunityIdAndDeletedAtIsNull(opportunityId);
  }

  @Override
  public long countOpenByOpportunityId(long opportunityId) {
    return jpaRepository.countByOpportunityIdAndDeletedAtIsNullAndStatusNotIn(
        opportunityId, List.of(Task.Status.COMPLETED, Task.Status.CANCELLED));
  }

  @Override
  public long countByProjectId(long projectId) {
    return jpaRepository.countByProjectIdAndDeletedAtIsNull(projectId);
  }

  @Override
  public long countOpenByProjectId(long projectId) {
    return jpaRepository.countByProjectIdAndDeletedAtIsNullAndStatusNotIn(
        projectId, List.of(Task.Status.COMPLETED, Task.Status.CANCELLED));
  }

  @Override
  public long countOpenPersonalTasks() {
    return count(
        Specification.where(TaskSpecifications.personalNotDeleted())
            .and(TaskSpecifications.statusOpen()));
  }

  @Override
  public long countPendingPersonalTasks() {
    return count(
        Specification.where(TaskSpecifications.personalNotDeleted())
            .and(TaskSpecifications.statusPending()));
  }

  @Override
  public long countOpenWorkTasks() {
    return count(
        Specification.where(TaskSpecifications.workNotDeleted())
            .and(TaskSpecifications.statusOpen()));
  }

  @Override
  public long countPendingWorkTasks() {
    return count(
        Specification.where(TaskSpecifications.workNotDeleted())
            .and(TaskSpecifications.statusPending()));
  }

  private long count(Specification<TaskJpaEntity> spec) {
    return jpaRepository.count(spec);
  }
}
