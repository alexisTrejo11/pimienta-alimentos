package io.github.alexistrejo11.pimienta.module.inventory.core.domain;

import java.time.LocalDateTime;

public class Inventory {
  private Long id;
  private Item item;
  private int totalQuantity;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private Long version;

  public Inventory() {
    this.id = 0L;
  }

  public static Inventory create(Item item, int totalQuantity) {
    var now = LocalDateTime.now();
    var inventory = new Inventory();
    inventory.item = item;
    inventory.totalQuantity = totalQuantity;
    inventory.createdAt = now;
    inventory.updatedAt = now;
    return inventory;
  }

  public static Inventory update(Long id, Item item, int totalQuantity) {
    var now = LocalDateTime.now();
    var inventory = new Inventory();
    inventory.id = id;
    inventory.item = item;
    inventory.totalQuantity = totalQuantity;
    inventory.updatedAt = now;
    return inventory;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public Item getItem() {
    return item;
  }

  public int getTotalQuantity() {
    return totalQuantity;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public Long getVersion() {
    return version;
  }
}
