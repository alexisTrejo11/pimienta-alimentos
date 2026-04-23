package io.github.alexistrejo11.pimienta.module.contract.core.domain.enums;

/**
 * Who the agreement is with (distinct from employment {@code ContractType} in the employees
 * module).
 */
public enum ContractCategory {
  EMPLOYEE,
  SUPPLIER,
  CUSTOMER,
  PARTNER,
  OTHER,
  /** Sentinel when the value is absent in persistence or not yet set. */
  UNDEFINED
}
