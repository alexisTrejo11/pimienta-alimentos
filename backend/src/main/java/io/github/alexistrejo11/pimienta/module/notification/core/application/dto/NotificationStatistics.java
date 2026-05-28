package io.github.alexistrejo11.pimienta.module.notification.core.application.dto;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import java.time.LocalDateTime;
import java.util.Map;

public record NotificationStatistics(
    LocalDateTime from,
    LocalDateTime to,
    long total,
    long pending,
    long sent,
    long failed,
    long skipped,
    Map<NotificationChannel, Long> countByChannel,
    Map<NotificationType, Long> countByType,
    Map<NotificationStatus, Long> countByStatus) {}
