package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.AdjustmentLine;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.AdjustmentTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.PhysicalAdjustmentCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.PurchaseLine;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.PurchaseTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ReturnClientCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ReturnClientLine;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ReturnSupplierCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ReturnSupplierLine;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.SaleLine;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.SaleTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ScrapCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.ScrapLine;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.TransferLine;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryTransactionCommands.TransferTransactionCommand;
import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.InventoryTransactionSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Inventory;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryMovement;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryTransaction;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.InventoryTransaction.TransactionType;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Item;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation.LocationStatus;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.InventoryMovementRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.InventoryRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.InventoryTransactionRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.ItemRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.StorageLocationRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.InventoryNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.InventoryTransactionNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.InventoryTransactionInvalidStateException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.ItemNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.StorageLocationNotFoundException;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryTransactionManagementUseCasesImpl implements InventoryTransactionManagementUseCases {

  private final InventoryTransactionRepository transactionRepository;
  private final InventoryMovementRepository movementRepository;
  private final InventoryRepository inventoryRepository;
  private final ItemRepository itemRepository;
  private final StorageLocationRepository storageLocationRepository;

  public InventoryTransactionManagementUseCasesImpl(
      InventoryTransactionRepository transactionRepository,
      InventoryMovementRepository movementRepository,
      InventoryRepository inventoryRepository,
      ItemRepository itemRepository,
      StorageLocationRepository storageLocationRepository) {
    this.transactionRepository = transactionRepository;
    this.movementRepository = movementRepository;
    this.inventoryRepository = inventoryRepository;
    this.itemRepository = itemRepository;
    this.storageLocationRepository = storageLocationRepository;
  }

  @Override
  public Page<InventoryTransaction> search(InventoryTransactionSearchCriteria criteria, Pageable pageable) {
    InventoryTransactionSearchCriteria effective =
        criteria != null ? criteria : InventoryTransactionSearchCriteria.empty();
    return transactionRepository.search(effective, pageable);
  }

  @Override
  public InventoryTransaction getById(Long id) {
    return transactionRepository
        .findById(id)
        .orElseThrow(() -> new InventoryTransactionNotFoundException(id));
  }

  private String newTransactionNumber() {
    return "TXN-" + System.currentTimeMillis() + "-" + ThreadLocalRandom.current().nextInt(10000, 99999);
  }

  private Inventory getOrCreateInventory(long itemId, long locationId) {
    return inventoryRepository
        .findByItemIdAndLocationId(itemId, locationId)
        .orElseGet(
            () -> {
              Item item =
                  itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
              StorageLocation loc =
                  storageLocationRepository
                      .findById(locationId)
                      .orElseThrow(() -> new StorageLocationNotFoundException(locationId));
              if (loc.getStatus() == LocationStatus.BLOCKED) {
                throw new InventoryTransactionInvalidStateException(
                    -1L, "Location " + locationId + " is blocked");
              }
              Inventory inv = Inventory.create(item, loc, 0);
              return inventoryRepository.save(inv);
            });
  }

  private void assertLocationWritable(long locationId) {
    StorageLocation loc =
        storageLocationRepository
            .findById(locationId)
            .orElseThrow(() -> new StorageLocationNotFoundException(locationId));
    if (loc.getStatus() == LocationStatus.BLOCKED) {
      throw new InventoryTransactionInvalidStateException(-1L, "Location " + locationId + " is blocked");
    }
  }

  private InventoryTransaction finishCompleted(InventoryTransaction tx) {
    InventoryTransaction loaded =
        transactionRepository.findById(tx.getId()).orElseThrow();
    loaded.complete();
    return transactionRepository.save(loaded);
  }

  @Override
  @Transactional
  public InventoryTransaction purchase(PurchaseTransactionCommand command) {
    InventoryTransaction tx =
        InventoryTransaction.open(
            newTransactionNumber(),
            TransactionType.PURCHASE_RECEIPT,
            command.externalReference(),
            command.notes(),
            command.initiatedById());
    tx = transactionRepository.save(tx);
    for (PurchaseLine line : command.lines()) {
      assertLocationWritable(line.locationId());
      Inventory inv = getOrCreateInventory(line.itemId(), line.locationId());
      inv.addStock(line.quantity());
      inventoryRepository.save(inv);
      InventoryMovement m =
          InventoryMovement.recordEntry(
              inv.getItem(),
              inv.getLocation(),
              line.quantity(),
              line.unitCost(),
              InventoryMovement.InventoryMovementType.PURCHASE,
              command.externalReference(),
              "Purchase receipt",
              command.initiatedById(),
              inv.getAvailableQuantity());
      m.setTransactionId(tx.getId());
      movementRepository.save(m);
    }
    return finishCompleted(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction sale(SaleTransactionCommand command) {
    InventoryTransaction tx =
        InventoryTransaction.open(
            newTransactionNumber(),
            TransactionType.SALE_DISPATCH,
            command.externalReference(),
            command.notes(),
            command.initiatedById());
    tx = transactionRepository.save(tx);
    for (SaleLine line : command.lines()) {
      assertLocationWritable(line.locationId());
      Inventory inv = getOrCreateInventory(line.itemId(), line.locationId());
      inv.removeStock(line.quantity());
      inventoryRepository.save(inv);
      InventoryMovement m =
          InventoryMovement.recordExit(
              inv.getItem(),
              inv.getLocation(),
              line.quantity(),
              line.unitCost(),
              InventoryMovement.InventoryMovementType.SALE,
              command.externalReference(),
              "Sale dispatch",
              command.initiatedById(),
              inv.getAvailableQuantity());
      m.setTransactionId(tx.getId());
      movementRepository.save(m);
    }
    return finishCompleted(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction transfer(TransferTransactionCommand command) {
    InventoryTransaction tx =
        InventoryTransaction.open(
            newTransactionNumber(),
            TransactionType.INTERNAL_TRANSFER,
            command.externalReference(),
            command.notes(),
            command.initiatedById());
    tx = transactionRepository.save(tx);
    for (TransferLine line : command.lines()) {
      assertLocationWritable(line.fromLocationId());
      assertLocationWritable(line.toLocationId());
      Inventory from = getOrCreateInventory(line.itemId(), line.fromLocationId());
      Inventory to = getOrCreateInventory(line.itemId(), line.toLocationId());
      from.removeStock(line.quantity());
      to.addStock(line.quantity());
      inventoryRepository.save(from);
      inventoryRepository.save(to);
      InventoryMovement out =
          InventoryMovement.recordExit(
              from.getItem(),
              from.getLocation(),
              line.quantity(),
              line.unitCost(),
              InventoryMovement.InventoryMovementType.TRANSFER,
              command.externalReference(),
              "Transfer out",
              command.initiatedById(),
              from.getAvailableQuantity());
      out.setTransactionId(tx.getId());
      movementRepository.save(out);
      InventoryMovement in =
          InventoryMovement.recordEntry(
              to.getItem(),
              to.getLocation(),
              line.quantity(),
              line.unitCost(),
              InventoryMovement.InventoryMovementType.TRANSFER,
              command.externalReference(),
              "Transfer in",
              command.initiatedById(),
              to.getAvailableQuantity());
      in.setTransactionId(tx.getId());
      movementRepository.save(in);
    }
    return finishCompleted(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction adjustment(AdjustmentTransactionCommand command) {
    InventoryTransaction tx =
        InventoryTransaction.open(
            newTransactionNumber(),
            TransactionType.PHYSICAL_COUNT,
            command.externalReference(),
            command.notes(),
            command.initiatedById());
    tx = transactionRepository.save(tx);
    for (AdjustmentLine line : command.lines()) {
      assertLocationWritable(line.locationId());
      Inventory inv = getOrCreateInventory(line.itemId(), line.locationId());
      int before = inv.getAvailableQuantity();
      inv.adjust(line.newQuantity());
      inventoryRepository.save(inv);
      int delta = line.newQuantity() - before;
      InventoryMovement m;
      if (delta >= 0) {
        m =
            InventoryMovement.recordEntry(
                inv.getItem(),
                inv.getLocation(),
                Math.abs(delta),
                BigDecimal.ZERO,
                InventoryMovement.InventoryMovementType.ADJUSTMENT_PLUS,
                command.externalReference(),
                line.reason(),
                command.initiatedById(),
                inv.getAvailableQuantity());
      } else {
        m =
            InventoryMovement.recordExit(
                inv.getItem(),
                inv.getLocation(),
                Math.abs(delta),
                BigDecimal.ZERO,
                InventoryMovement.InventoryMovementType.ADJUSTMENT_MINUS,
                command.externalReference(),
                line.reason(),
                command.initiatedById(),
                inv.getAvailableQuantity());
      }
      if (delta != 0) {
        m.setTransactionId(tx.getId());
        movementRepository.save(m);
      }
    }
    return finishCompleted(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction returnFromClient(ReturnClientCommand command) {
    InventoryTransaction tx =
        InventoryTransaction.open(
            newTransactionNumber(),
            TransactionType.RETURN_FROM_CLIENT,
            command.externalReference(),
            command.notes(),
            command.initiatedById());
    tx = transactionRepository.save(tx);
    for (ReturnClientLine line : command.lines()) {
      assertLocationWritable(line.locationId());
      Inventory inv = getOrCreateInventory(line.itemId(), line.locationId());
      inv.addStock(line.quantity());
      inventoryRepository.save(inv);
      InventoryMovement m =
          InventoryMovement.recordEntry(
              inv.getItem(),
              inv.getLocation(),
              line.quantity(),
              line.unitCost(),
              InventoryMovement.InventoryMovementType.RETURN_FROM_CLIENT,
              command.externalReference(),
              "Return from client",
              command.initiatedById(),
              inv.getAvailableQuantity());
      m.setTransactionId(tx.getId());
      movementRepository.save(m);
    }
    return finishCompleted(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction returnToSupplier(ReturnSupplierCommand command) {
    InventoryTransaction tx =
        InventoryTransaction.open(
            newTransactionNumber(),
            TransactionType.RETURN_TO_SUPPLIER,
            command.externalReference(),
            command.notes(),
            command.initiatedById());
    tx = transactionRepository.save(tx);
    for (ReturnSupplierLine line : command.lines()) {
      assertLocationWritable(line.locationId());
      Inventory inv = getOrCreateInventory(line.itemId(), line.locationId());
      inv.removeStock(line.quantity());
      inventoryRepository.save(inv);
      InventoryMovement m =
          InventoryMovement.recordExit(
              inv.getItem(),
              inv.getLocation(),
              line.quantity(),
              line.unitCost(),
              InventoryMovement.InventoryMovementType.RETURN_TO_SUPPLIER,
              command.externalReference(),
              "Return to supplier",
              command.initiatedById(),
              inv.getAvailableQuantity());
      m.setTransactionId(tx.getId());
      movementRepository.save(m);
    }
    return finishCompleted(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction scrap(ScrapCommand command) {
    InventoryTransaction tx =
        InventoryTransaction.open(
            newTransactionNumber(),
            TransactionType.SCRAP_WRITE_OFF,
            command.externalReference(),
            command.notes(),
            command.initiatedById());
    tx = transactionRepository.save(tx);
    for (ScrapLine line : command.lines()) {
      assertLocationWritable(line.locationId());
      Inventory inv = getOrCreateInventory(line.itemId(), line.locationId());
      inv.removeStock(line.quantity());
      inventoryRepository.save(inv);
      InventoryMovement m =
          InventoryMovement.recordExit(
              inv.getItem(),
              inv.getLocation(),
              line.quantity(),
              line.unitCost(),
              InventoryMovement.InventoryMovementType.SCRAP,
              command.externalReference(),
              "Scrap",
              command.initiatedById(),
              inv.getAvailableQuantity());
      m.setTransactionId(tx.getId());
      movementRepository.save(m);
    }
    return finishCompleted(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction physicalAdjustment(PhysicalAdjustmentCommand command) {
    Inventory inv =
        inventoryRepository
            .findById(command.inventoryId())
            .orElseThrow(() -> new InventoryNotFoundException(command.inventoryId()));
    InventoryTransaction tx =
        InventoryTransaction.open(
            newTransactionNumber(),
            TransactionType.PHYSICAL_COUNT,
            null,
            command.reason(),
            command.performedById());
    tx = transactionRepository.save(tx);
    int before = inv.getAvailableQuantity();
    inv.adjust(command.newQuantity());
    inventoryRepository.save(inv);
    int delta = command.newQuantity() - before;
    if (delta != 0) {
      InventoryMovement m;
      if (delta > 0) {
        m =
            InventoryMovement.recordEntry(
                inv.getItem(),
                inv.getLocation(),
                delta,
                BigDecimal.ZERO,
                InventoryMovement.InventoryMovementType.ADJUSTMENT_PLUS,
                null,
                command.reason(),
                command.performedById(),
                inv.getAvailableQuantity());
      } else {
        m =
            InventoryMovement.recordExit(
                inv.getItem(),
                inv.getLocation(),
                -delta,
                BigDecimal.ZERO,
                InventoryMovement.InventoryMovementType.ADJUSTMENT_MINUS,
                null,
                command.reason(),
                command.performedById(),
                inv.getAvailableQuantity());
      }
      m.setTransactionId(tx.getId());
      movementRepository.save(m);
    }
    return finishCompleted(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction submit(Long id) {
    InventoryTransaction tx = getById(id);
    tx.submit();
    return transactionRepository.save(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction approve(Long id, Long approvedById) {
    InventoryTransaction tx = getById(id);
    tx.approve(approvedById);
    return transactionRepository.save(tx);
  }

  @Override
  @Transactional
  public InventoryTransaction complete(Long id) {
    InventoryTransaction tx = getById(id);
    tx.complete();
    return transactionRepository.save(tx);
  }

  @Override
  @Transactional
  public void cancel(Long id) {
    InventoryTransaction tx = getById(id);
    tx.cancel();
    transactionRepository.save(tx);
  }
}
