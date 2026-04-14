package io.github.alexistrejo11.pimienta.module.crm.core.application.query;

import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;

public record OpportunitySearchCriteria(
    Opportunity.OpportunityStatus status, String companyNameContains, String titleContains) {

  public static OpportunitySearchCriteria empty() {
    return new OpportunitySearchCriteria(null, null, null);
  }
}
