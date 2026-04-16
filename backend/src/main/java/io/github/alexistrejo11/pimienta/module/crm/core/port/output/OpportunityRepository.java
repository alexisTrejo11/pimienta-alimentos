package io.github.alexistrejo11.pimienta.module.crm.core.port.output;

import io.github.alexistrejo11.pimienta.module.crm.core.application.query.OpportunitySearchCriteria;
import io.github.alexistrejo11.pimienta.module.crm.core.domain.Opportunity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OpportunityRepository {

  Optional<Opportunity> findById(long id);

  Page<Opportunity> search(OpportunitySearchCriteria criteria, Pageable pageable);

  Opportunity save(Opportunity opportunity);
}
