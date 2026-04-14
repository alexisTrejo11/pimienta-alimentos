package io.github.alexistrejo11.pimienta.module.crm.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class OpportunityNotFoundException extends ResourceNotFoundException {

  public OpportunityNotFoundException(Long id) {
    super(
        ErrorCode.OPPORTUNITY_NOT_FOUND,
        "The requested opportunity was not found.",
        Map.of("opportunityId", id),
        "Opportunity not found: id=" + id);
  }
}
