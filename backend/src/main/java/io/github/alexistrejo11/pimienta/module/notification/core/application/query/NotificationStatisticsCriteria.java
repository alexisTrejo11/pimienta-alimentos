package io.github.alexistrejo11.pimienta.module.notification.core.application.query;

import java.time.LocalDateTime;

public record NotificationStatisticsCriteria(LocalDateTime from, LocalDateTime to) {}
