package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.notification.core.application.dto.NotificationStatistics;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationStatisticsCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import io.github.alexistrejo11.pimienta.module.notification.core.port.output.NotificationRepository;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

  private final NotificationJpaRepository jpaRepository;

  public NotificationRepositoryImpl(NotificationJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Notification save(Notification notification) {
    NotificationJpaEntity entity = NotificationPersistenceMapper.toJpa(notification);
    entity.fillCreatedAndUpdatedIfNull();
    NotificationJpaEntity saved = jpaRepository.save(entity);
    return NotificationPersistenceMapper.toDomain(saved);
  }

  @Override
  public void updateStatus(
      UUID id, NotificationStatus status, LocalDateTime sentAt, String lastError) {
    if (id == null) {
      return;
    }
    jpaRepository
        .findById(id)
        .ifPresent(
            entity -> {
              entity.setStatus(status);
              entity.setSentAt(sentAt);
              entity.setLastError(lastError);
              entity.setUpdatedAt(LocalDateTime.now());
              entity.setAttemptCount(entity.getAttemptCount() + 1);
              jpaRepository.save(entity);
            });
  }

  @Override
  public Page<Notification> search(NotificationSearchCriteria criteria, Pageable pageable) {
    Specification<NotificationJpaEntity> spec = NotificationSpecifications.fromCriteria(criteria);
    return jpaRepository.findAll(spec, pageable).map(NotificationPersistenceMapper::toDomain);
  }

  @Override
  public NotificationStatistics aggregateStatistics(NotificationStatisticsCriteria criteria) {
    Specification<NotificationJpaEntity> spec = NotificationSpecifications.forStatistics(criteria);
    List<NotificationJpaEntity> rows = jpaRepository.findAll(spec);

    long total = rows.size();
    long pending = 0;
    long sent = 0;
    long failed = 0;
    long skipped = 0;
    Map<NotificationChannel, Long> byChannel = new EnumMap<>(NotificationChannel.class);
    Map<NotificationType, Long> byType = new EnumMap<>(NotificationType.class);
    Map<NotificationStatus, Long> byStatus = new EnumMap<>(NotificationStatus.class);

    for (NotificationJpaEntity row : rows) {
      switch (row.getStatus()) {
        case PENDING -> pending++;
        case SENT -> sent++;
        case FAILED -> failed++;
        case SKIPPED -> skipped++;
      }
      byChannel.merge(row.getChannel(), 1L, Long::sum);
      byType.merge(row.getType(), 1L, Long::sum);
      byStatus.merge(row.getStatus(), 1L, Long::sum);
    }

    LocalDateTime from = criteria != null ? criteria.from() : null;
    LocalDateTime to = criteria != null ? criteria.to() : null;
    return new NotificationStatistics(from, to, total, pending, sent, failed, skipped, byChannel, byType, byStatus);
  }
}
