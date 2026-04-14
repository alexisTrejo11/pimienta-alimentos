package io.github.alexistrejo11.pimienta.module.crm.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.ProjectSearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import io.github.alexistrejo11.pimienta.module.crm.core.port.ProjectRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

  private final ProjectJpaRepository jpaRepository;

  public ProjectRepositoryImpl(ProjectJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Optional<Project> findById(long id) {
    return jpaRepository.findByIdAndDeletedAtIsNull(id).map(ProjectPersistenceMapper::toDomain);
  }

  @Override
  public Page<Project> search(ProjectSearchCriteria criteria, Pageable pageable) {
    ProjectSearchCriteria effective = criteria != null ? criteria : ProjectSearchCriteria.empty();
    Specification<ProjectJpaEntity> spec = ProjectSpecifications.fromCriteria(effective);
    return jpaRepository.findAll(spec, pageable).map(ProjectPersistenceMapper::toDomain);
  }

  @Override
  public Optional<Project> findByProjectCode(String projectCode) {
    if (projectCode == null || projectCode.isBlank()) {
      return Optional.empty();
    }
    return jpaRepository
        .findByProjectCodeAndDeletedAtIsNull(projectCode.trim())
        .map(ProjectPersistenceMapper::toDomain);
  }

  @Override
  public Project save(Project project) {
    ProjectJpaEntity entity = ProjectPersistenceMapper.toJpa(project);
    ProjectJpaEntity saved = jpaRepository.save(entity);
    return ProjectPersistenceMapper.toDomain(saved);
  }
}
