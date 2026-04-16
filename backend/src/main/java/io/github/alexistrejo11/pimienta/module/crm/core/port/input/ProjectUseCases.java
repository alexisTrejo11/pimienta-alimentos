package io.github.alexistrejo11.pimienta.module.crm.core.port.input;

import io.github.alexistrejo11.pimienta.module.crm.core.application.command.CreateProjectParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.summary.ProjectSummary;
import io.github.alexistrejo11.pimienta.module.crm.core.application.command.UpdateProjectParams;
import io.github.alexistrejo11.pimienta.module.crm.core.application.query.ProjectSearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectUseCases {

  Page<Project> search(ProjectSearchCriteria criteria, Pageable pageable);

  Project getById(Long id);

  Project create(CreateProjectParams params);

  Project update(Long id, UpdateProjectParams params);

  void delete(Long id);

  ProjectSummary getSummary(Long id);

  Project activate(Long id);

  Project putOnHold(Long id, String reason);

  Project complete(Long id);

  Project cancel(Long id, String reason);

  Project archive(Long id);
}
