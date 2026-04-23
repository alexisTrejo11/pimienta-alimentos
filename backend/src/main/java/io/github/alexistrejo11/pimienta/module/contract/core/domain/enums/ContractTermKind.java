package io.github.alexistrejo11.pimienta.module.contract.core.domain.enums;

/** Whether the agreement has a fixed end date or runs without a scheduled expiry. */
public enum ContractTermKind {
  /** End date required; may be renewed or extended. */
  FIXED_TERM,
  /** No contractual end date in the system (permanent / indefinite). */
  INDEFINITE,
  /** Sentinel when the value is absent in persistence or not yet set. */
  UNDEFINED
}
