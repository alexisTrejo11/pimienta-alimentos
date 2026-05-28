package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.out.channel;

import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.port.output.NotificationChannelSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** Local development / audit channel: writes notification payloads to application logs. */
@Component
public class LogNotificationSender implements NotificationChannelSender {

  private static final Logger log = LoggerFactory.getLogger(LogNotificationSender.class);

  @Override
  public NotificationChannel channel() {
    return NotificationChannel.LOG;
  }

  @Override
  public void send(Notification notification) {
    log.info(
        "notification channel=LOG type={} correlationId={} recipientUserId={} subject={} body={}",
        notification.getType(),
        notification.getCorrelationId(),
        notification.getRecipientUserId(),
        notification.getSubject(),
        notification.getBody());
  }
}
