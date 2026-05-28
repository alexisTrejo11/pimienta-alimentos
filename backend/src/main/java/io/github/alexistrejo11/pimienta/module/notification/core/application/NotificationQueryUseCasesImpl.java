package io.github.alexistrejo11.pimienta.module.notification.core.application;

import io.github.alexistrejo11.pimienta.module.notification.core.application.dto.NotificationStatistics;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationSearchScope;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationStatisticsCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.port.input.NotificationQueryUseCases;
import io.github.alexistrejo11.pimienta.module.notification.core.port.output.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationQueryUseCasesImpl implements NotificationQueryUseCases {

  private final NotificationRepository notificationRepository;

  public NotificationQueryUseCasesImpl(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Notification> searchAdmin(NotificationSearchCriteria criteria, Pageable pageable) {
    NotificationSearchCriteria effective = withScope(criteria, NotificationSearchScope.ADMIN);
    return notificationRepository.search(effective, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Notification> searchManagerLogs(NotificationSearchCriteria criteria, Pageable pageable) {
    NotificationSearchCriteria base = criteria != null ? criteria : NotificationSearchCriteria.managerLogDefaults();
    NotificationSearchCriteria effective =
        new NotificationSearchCriteria(
            base.id(),
            null,
            base.type(),
            base.status(),
            base.recipientUserId(),
            base.relatedUserId(),
            base.correlationId(),
            null,
            null,
            base.subjectContains(),
            base.bodyContains(),
            null,
            null,
            base.sentFrom(),
            base.sentTo(),
            NotificationSearchScope.MANAGER_LOG);
    return notificationRepository.search(effective, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public NotificationStatistics statistics(NotificationStatisticsCriteria criteria) {
    NotificationIntervalValidator.validateStatisticsInterval(criteria.from(), criteria.to());
    return notificationRepository.aggregateStatistics(criteria);
  }

  private static NotificationSearchCriteria withScope(
      NotificationSearchCriteria criteria, NotificationSearchScope scope) {
    if (criteria == null) {
      return new NotificationSearchCriteria(
          null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, scope);
    }
    return new NotificationSearchCriteria(
        criteria.id(),
        criteria.channel(),
        criteria.type(),
        criteria.status(),
        criteria.recipientUserId(),
        criteria.relatedUserId(),
        criteria.correlationId(),
        criteria.recipientEmailContains(),
        criteria.recipientPhoneContains(),
        criteria.subjectContains(),
        criteria.bodyContains(),
        criteria.createdFrom(),
        criteria.createdTo(),
        criteria.sentFrom(),
        criteria.sentTo(),
        scope);
  }
}
