package io.github.alexistrejo11.pimienta.module.notification.core.application.query;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Filters for notification search. {@link NotificationSearchScope#MANAGER_LOG} applies restrictive
 * defaults (LOG channel, created today) via specifications.
 */
public record NotificationSearchCriteria(
    UUID id,
    NotificationChannel channel,
    NotificationType type,
    NotificationStatus status,
    Long recipientUserId,
    Long relatedUserId,
    String correlationId,
    String recipientEmailContains,
    String recipientPhoneContains,
    String subjectContains,
    String bodyContains,
    LocalDateTime createdFrom,
    LocalDateTime createdTo,
    LocalDateTime sentFrom,
    LocalDateTime sentTo,
    NotificationSearchScope scope) {

  public NotificationSearchCriteria {
    if (scope == null) {
      scope = NotificationSearchScope.ADMIN;
    }
  }

  public static NotificationSearchCriteria managerLogDefaults() {
    return new NotificationSearchCriteria(
        null,
        NotificationChannel.LOG,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        NotificationSearchScope.MANAGER_LOG);
  }
}
