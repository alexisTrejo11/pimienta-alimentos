package io.github.alexistrejo11.pimienta.module.inventory.core.domain;

import java.time.LocalDateTime;

public class InventoryMovement {
  private Long id;
  private Item item;
  private int quantity;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private InventoryMovementType type;
  private String description;
  private Long version;
  private Long performedById;

  public enum InventoryMovementType {
    PURCHASE,
    SALE,
    ADJUSTMENT,
    TRANSFER,
    RETURN,
    USAGE
  }

  public InventoryMovement() {
    this.id = 0L;
  }
}
