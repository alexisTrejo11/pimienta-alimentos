package io.github.alexistrejo11.pimienta.module.account.user.core.domain.enums;

public enum AccountStatus {
  /**
   * Registration submitted; waiting for an administrator to approve the account.
   */
  PENDING_APPROVAL,
  /** Account approved and allowed to log in. */
  ACTIVE,
  /** Account suspended by an administrator. */
  BANNED
}
