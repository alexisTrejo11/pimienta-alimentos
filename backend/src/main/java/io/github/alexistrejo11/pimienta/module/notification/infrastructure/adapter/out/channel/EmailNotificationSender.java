package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.out.channel;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.port.output.NotificationChannelSender;
import org.springframework.stereotype.Component;

/** SMTP / provider integration placeholder. */
@Component
public class EmailNotificationSender implements NotificationChannelSender {

  @Override
  public NotificationChannel channel() {
    return NotificationChannel.EMAIL;
  }

  @Override
  public void send(Notification notification) {
    // TODO: integrate email provider (e.g. SES, SendGrid)
  }
}
