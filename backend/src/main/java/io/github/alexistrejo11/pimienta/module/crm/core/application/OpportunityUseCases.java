package io.github.alexistrejo11.pimienta.module.crm.core.application;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.OpportunitySearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OpportunityUseCases {

  Page<Opportunity> search(OpportunitySearchCriteria criteria, Pageable pageable);

  Opportunity getById(Long id);

  Opportunity create(CreateOpportunityParams params);

  Opportunity update(Long id, UpdateOpportunityParams params);

  void delete(Long id);

  OpportunitySummary getSummary(Long id);

  Opportunity moveToDiscovery(Long id);

  Opportunity sendProposal(Long id);

  Opportunity startNegotiation(Long id);

  Opportunity win(Long id, WinOpportunityParams params);

  Opportunity lose(Long id, String reason);

  Opportunity abandon(Long id);

  Opportunity reopen(Long id);
}
