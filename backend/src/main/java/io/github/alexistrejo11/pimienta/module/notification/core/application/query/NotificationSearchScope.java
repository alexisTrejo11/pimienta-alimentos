package io.github.alexistrejo11.pimienta.module.notification.core.application.query;

public enum NotificationSearchScope {
  /** Full filter surface for administrators. */
  ADMIN,
  /** Restricted: LOG channel and notifications created on the current calendar day. */
  MANAGER_LOG
}
