package io.github.alexistrejo11.pimienta.module.crm.core.port;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.ProjectSearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectRepository {

  Optional<Project> findById(long id);

  Page<Project> search(ProjectSearchCriteria criteria, Pageable pageable);

  Optional<Project> findByProjectCode(String projectCode);

  Project save(Project project);
}
