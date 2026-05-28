package io.github.alexistrejo11.pimienta.module.notification.core.application.event;

import java.time.Instant;

/**
 * Published when a new account is registered and awaits administrator approval. Consumed by the
 * notification module to alert admins.
 */
public record AccountPendingApprovalEvent(
    Long newUserId,
    String newUserEmail,
    String newUserFirstName,
    String newUserLastName,
    String newUserPhone,
    Instant occurredAt) {

  public AccountPendingApprovalEvent {
    if (newUserId == null) {
      throw new IllegalArgumentException("newUserId is required");
    }
    if (newUserEmail == null || newUserEmail.isBlank()) {
      throw new IllegalArgumentException("newUserEmail is required");
    }
    if (occurredAt == null) {
      occurredAt = Instant.now();
    }
  }
}
