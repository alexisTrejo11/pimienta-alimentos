package io.github.alexistrejo11.pimienta.module.inventory.core.port.output;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryTransactionSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryTransaction;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryTransactionRepository {

  Optional<InventoryTransaction> findById(long id);

  Page<InventoryTransaction> search(InventoryTransactionSearchCriteria criteria, Pageable pageable);

  InventoryTransaction save(InventoryTransaction transaction);
}
