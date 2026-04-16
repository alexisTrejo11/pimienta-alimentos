package io.github.alexistrejo11.pimienta.module.contract.core.domain;

import io.github.alexistrejo11.pimienta.shared.BaseDomain;

import java.time.LocalDateTime;

public class Contract extends BaseDomain<Long> {
  private String name;
  private String description;
  private ContractType type;

  public enum ContractType {
    EMPLOYEE,
    SUPPLIER,
    CUSTOMER,
    PARTNER,
    OTHER
  }

  public Contract() {
    this.id = 0L;
  }

  public static Contract create(String name, String description, ContractType type) {
    var now = LocalDateTime.now();
    var contract = new Contract();
    contract.name = name;
    contract.description = description;
    contract.type = type;
    contract.createdAt = now;
    contract.updatedAt = now;
    return contract;
  }

  public static Contract update(Long id, String name, String description, ContractType type) {
    var now = LocalDateTime.now();
    var contract = new Contract();
    contract.id = id;
    contract.name = name;
    contract.description = description;
    contract.type = type;
    contract.updatedAt = now;
    return contract;
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

  public ContractType getType() {
    return type;
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
