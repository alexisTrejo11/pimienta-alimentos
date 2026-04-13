package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.AdjustmentTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.PhysicalAdjustmentCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.PurchaseTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ReturnClientCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ReturnSupplierCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.SaleTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ScrapCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.TransferTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryTransactionSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryTransactionManagementUseCases {

  Page<InventoryTransaction> search(InventoryTransactionSearchCriteria criteria, Pageable pageable);

  InventoryTransaction getById(Long id);

  InventoryTransaction purchase(PurchaseTransactionCommand command);

  InventoryTransaction sale(SaleTransactionCommand command);

  InventoryTransaction transfer(TransferTransactionCommand command);

  InventoryTransaction adjustment(AdjustmentTransactionCommand command);

  InventoryTransaction returnFromClient(ReturnClientCommand command);

  InventoryTransaction returnToSupplier(ReturnSupplierCommand command);

  InventoryTransaction scrap(ScrapCommand command);

  InventoryTransaction physicalAdjustment(PhysicalAdjustmentCommand command);

  InventoryTransaction submit(Long id);

  InventoryTransaction approve(Long id, Long approvedById);

  InventoryTransaction complete(Long id);

  void cancel(Long id);
}
