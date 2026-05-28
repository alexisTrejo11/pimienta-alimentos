package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.notification.core.application.dto.NotificationStatistics;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationSearchScope;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationStatisticsCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto.NotificationAdminSearchRequest;
import io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto.NotificationManagerLogSearchRequest;
import io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto.NotificationResponse;
import io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto.NotificationStatisticsRequest;
import io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.web.dto.NotificationStatisticsResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public final class NotificationWebMapper {

  private NotificationWebMapper() {}

  public static NotificationSearchCriteria toAdminCriteria(NotificationAdminSearchRequest request) {
    return new NotificationSearchCriteria(
        request.getId(),
        request.getChannel(),
        request.getType(),
        request.getStatus(),
        request.getRecipientUserId(),
        request.getRelatedUserId(),
        request.getCorrelationId(),
        request.getRecipientEmailContains(),
        request.getRecipientPhoneContains(),
        request.getSubjectContains(),
        request.getBodyContains(),
        request.getCreatedFrom(),
        request.getCreatedTo(),
        request.getSentFrom(),
        request.getSentTo(),
        NotificationSearchScope.ADMIN);
  }

  public static NotificationSearchCriteria toManagerCriteria(NotificationManagerLogSearchRequest request) {
    return new NotificationSearchCriteria(
        request.getId(),
        NotificationChannel.LOG,
        request.getType(),
        request.getStatus(),
        request.getRecipientUserId(),
        request.getRelatedUserId(),
        request.getCorrelationId(),
        null,
        null,
        request.getSubjectContains(),
        request.getBodyContains(),
        null,
        null,
        request.getSentFrom(),
        request.getSentTo(),
        NotificationSearchScope.MANAGER_LOG);
  }

  public static NotificationStatisticsCriteria toStatisticsCriteria(NotificationStatisticsRequest request) {
    return new NotificationStatisticsCriteria(request.getFrom(), request.getTo());
  }

  public static NotificationResponse toResponse(Notification notification) {
    return new NotificationResponse(
        notification.getId(),
        notification.getChannel(),
        notification.getType(),
        notification.getStatus(),
        notification.getRecipientEmail(),
        notification.getRecipientPhone(),
        notification.getRecipientDisplayName(),
        notification.getRecipientUserId(),
        notification.getSubject(),
        notification.getBody(),
        notification.getTemplateId(),
        notification.getTemplateVariables(),
        notification.getCorrelationId(),
        notification.getRelatedUserId(),
        notification.getLocale(),
        notification.getAttemptCount(),
        notification.getLastError(),
        notification.getCreatedAt(),
        notification.getSentAt());
  }

  public static NotificationStatisticsResponse toStatisticsResponse(NotificationStatistics stats) {
    return new NotificationStatisticsResponse(
        stats.from(),
        stats.to(),
        stats.total(),
        stats.pending(),
        stats.sent(),
        stats.failed(),
        stats.skipped(),
        enumMapToStringKeys(stats.countByChannel()),
        enumMapToStringKeys(stats.countByType()),
        enumMapToStringKeys(stats.countByStatus()));
  }

  private static <E extends Enum<E>> Map<String, Long> enumMapToStringKeys(Map<E, Long> source) {
    Map<String, Long> result = new LinkedHashMap<>();
    if (source == null) {
      return result;
    }
    source.forEach((key, value) -> result.put(key.name(), value));
    return result;
  }
}
