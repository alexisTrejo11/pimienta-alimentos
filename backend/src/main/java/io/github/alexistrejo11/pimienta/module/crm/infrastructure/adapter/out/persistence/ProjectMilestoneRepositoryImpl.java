package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.ProjectMilestone;
import io.github.alexistrejo11.pimienta.module.crm.core.port.ProjectMilestoneRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectMilestoneRepositoryImpl implements ProjectMilestoneRepository {

  private final ProjectMilestoneJpaRepository jpaRepository;

  public ProjectMilestoneRepositoryImpl(ProjectMilestoneJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Optional<ProjectMilestone> findByIdAndProjectId(long milestoneId, long projectId) {
    return jpaRepository
        .findByIdAndProjectIdAndDeletedAtIsNull(milestoneId, projectId)
        .map(ProjectMilestonePersistenceMapper::toDomain);
  }

  @Override
  public Page<ProjectMilestone> findByProjectId(long projectId, Pageable pageable) {
    return jpaRepository
        .findByProjectIdAndDeletedAtIsNullOrderBySortOrderAsc(projectId, pageable)
        .map(ProjectMilestonePersistenceMapper::toDomain);
  }

  @Override
  public long countByProjectId(long projectId) {
    return jpaRepository.countByProjectIdAndDeletedAtIsNull(projectId);
  }

  @Override
  public long countCompletedByProjectId(long projectId) {
    return jpaRepository.countByProjectIdAndDeletedAtIsNullAndStatus(
        projectId, ProjectMilestone.MilestoneStatus.COMPLETED);
  }

  @Override
  public ProjectMilestone save(ProjectMilestone milestone) {
    ProjectMilestoneJpaEntity entity = ProjectMilestonePersistenceMapper.toJpa(milestone);
    ProjectMilestoneJpaEntity saved = jpaRepository.save(entity);
    return ProjectMilestonePersistenceMapper.toDomain(saved);
  }
}
