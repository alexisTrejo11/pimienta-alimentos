package io.github.alexistrejo11.pimienta.module.notification.core.port.input;

import io.github.alexistrejo11.pimienta.module.notification.core.application.dto.NotificationStatistics;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.application.query.NotificationStatisticsCriteria;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationQueryUseCases {

  Page<Notification> searchAdmin(NotificationSearchCriteria criteria, Pageable pageable);

  Page<Notification> searchManagerLogs(NotificationSearchCriteria criteria, Pageable pageable);

  NotificationStatistics statistics(NotificationStatisticsCriteria criteria);
}
