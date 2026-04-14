package io.github.alexistrejo11.pimienta.module.crm.core.application.query;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Project;

public record ProjectSearchCriteria(
    Project.ProjectStatus status,
    Long clientId,
    Long projectManagerId,
    Long originOpportunityId) {

  public static ProjectSearchCriteria empty() {
    return new ProjectSearchCriteria(null, null, null, null);
  }
}
