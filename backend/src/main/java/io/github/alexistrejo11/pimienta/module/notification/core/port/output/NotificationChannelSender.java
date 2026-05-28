package io.github.alexistrejo11.pimienta.module.notification.core.port.output;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;

public interface NotificationChannelSender {

  NotificationChannel channel();

  void send(Notification notification);
}
