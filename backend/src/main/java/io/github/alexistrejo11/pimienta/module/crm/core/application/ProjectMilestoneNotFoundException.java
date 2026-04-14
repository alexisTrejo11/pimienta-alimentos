package io.github.alexistrejo11.pimienta.module.crm.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class ProjectMilestoneNotFoundException extends ResourceNotFoundException {

  public ProjectMilestoneNotFoundException(Long projectId, Long milestoneId) {
    super(
        ErrorCode.PROJECT_MILESTONE_NOT_FOUND,
        "The requested project milestone was not found.",
        Map.of("projectId", projectId, "milestoneId", milestoneId),
        "Project milestone not found: projectId=" + projectId + ", milestoneId=" + milestoneId);
  }
}
