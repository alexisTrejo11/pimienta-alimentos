package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.out.channel;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.port.output.NotificationChannelSender;
import org.springframework.stereotype.Component;

/** Twilio SMS integration placeholder. */
@Component
public class SmsNotificationSender implements NotificationChannelSender {

  @Override
  public NotificationChannel channel() {
    return NotificationChannel.SMS;
  }

  @Override
  public void send(Notification notification) {
    // TODO: integrate Twilio REST API using recipient phone and body
  }
}
