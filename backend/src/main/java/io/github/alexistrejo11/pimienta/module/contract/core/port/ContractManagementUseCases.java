package io.github.alexistrejo11.pimienta.module.contract.core.port;

import io.github.alexistrejo11.pimienta.module.contract.core.application.command.CreateContractCommand;
import io.github.alexistrejo11.pimienta.module.contract.core.application.command.UpdateContractCommand;
import io.github.alexistrejo11.pimienta.module.contract.core.domain.Contract;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractManagementUseCases {

  Page<Contract> getBy(Pageable pageable);

  Contract getById(Long id);

  Contract create(CreateContractCommand command);

  Contract update(Long id, UpdateContractCommand command);

  void delete(Long id);

  Contract renew(Long id);

  Contract extend(Long id, LocalDate newEnd);
}
