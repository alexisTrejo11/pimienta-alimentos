package io.github.alexistrejo11.pimienta.module.notification.core.port.output;

import io.github.alexistrejo11.pimienta.module.notification.core.application.dto.NotificationStatistics;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationStatisticsCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationRepository {

  Notification save(Notification notification);

  void updateStatus(UUID id, NotificationStatus status, LocalDateTime sentAt, String lastError);

  Page<Notification> search(NotificationSearchCriteria criteria, Pageable pageable);

  NotificationStatistics aggregateStatistics(NotificationStatisticsCriteria criteria);
}
