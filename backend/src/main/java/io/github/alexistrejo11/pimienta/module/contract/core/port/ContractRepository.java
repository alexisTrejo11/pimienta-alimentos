package io.github.alexistrejo11.pimienta.module.contract.core.port;

import io.github.alexistrejo11.pimienta.module.contract.core.domain.Contract;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractRepository {

  Optional<Contract> findById(long id);

  /** Contracts that are not soft-deleted. */
  Page<Contract> findActive(Pageable pageable);

  Contract save(Contract contract);
}
