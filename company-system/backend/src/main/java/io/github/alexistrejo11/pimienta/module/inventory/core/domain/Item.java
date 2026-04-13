package io.github.alexistrejo11.pimienta.module.inventory.core.domain;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Item {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int quantity;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private Long version;

  public Item() {
    this.id = 0L;
    this.name = "";
    this.description = "";
    this.price = BigDecimal.ZERO;
    this.quantity = 0;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.deletedAt = null;
    this.version = 0L;
  }

  public static Item create(String name, String description, BigDecimal price, int quantity) {
    var now = LocalDateTime.now();
    var item = new Item();
    item.name = name;
    item.description = description;
    item.price = price;
    item.quantity = quantity;
    item.createdAt = now;
    item.updatedAt = now;
    return item;
  }

  public static Item update(Long id, String name, String description, BigDecimal price, int quantity) {
    var now = LocalDateTime.now();
    var item = new Item();
    item.id = id;
    item.name = name;
    item.description = description;
    item.price = price;
    item.quantity = quantity;
    item.updatedAt = now;
    item.deletedAt = null;
    item.version += 1;
    return item;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
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
