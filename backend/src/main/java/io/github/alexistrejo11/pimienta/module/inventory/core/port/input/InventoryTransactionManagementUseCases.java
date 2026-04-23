package io.github.alexistrejo11.pimienta.module.inventory.core.port.input;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.command.InventoryTransactionCommands.AdjustmentTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.command.InventoryTransactionCommands.PhysicalAdjustmentCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.command.InventoryTransactionCommands.PurchaseTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.command.InventoryTransactionCommands.ReturnClientCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.command.InventoryTransactionCommands.ReturnSupplierCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.command.InventoryTransactionCommands.SaleTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.command.InventoryTransactionCommands.ScrapCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.command.InventoryTransactionCommands.TransferTransactionCommand;
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
