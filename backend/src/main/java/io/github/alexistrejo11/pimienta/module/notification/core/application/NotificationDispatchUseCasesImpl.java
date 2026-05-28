package io.github.alexistrejo11.pimienta.module.notification.core.application;

import io.github.alexistrejo11.pimienta.module.notification.core.application.dto.AdminRecipient;
import io.github.alexistrejo11.pimienta.module.notification.core.application.event.AccountPendingApprovalEvent;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.Notification;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationChannel;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationStatus;
import io.github.alexistrejo11.pimienta.module.notification.core.domain.enums.NotificationType;
import io.github.alexistrejo11.pimienta.module.notification.core.port.input.NotificationDispatchUseCases;
import io.github.alexistrejo11.pimienta.module.notification.core.port.output.AdminRecipientPort;
import io.github.alexistrejo11.pimienta.module.notification.core.port.output.NotificationChannelSender;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationDispatchUseCasesImpl implements NotificationDispatchUseCases {

  private static final Logger log = LoggerFactory.getLogger(NotificationDispatchUseCasesImpl.class);

  private final AdminRecipientPort adminRecipientPort;
  private final Map<NotificationChannel, NotificationChannelSender> sendersByChannel;

  public NotificationDispatchUseCasesImpl(
      AdminRecipientPort adminRecipientPort, List<NotificationChannelSender> senders) {
    this.adminRecipientPort = adminRecipientPort;
    this.sendersByChannel = new EnumMap<>(NotificationChannel.class);
    for (NotificationChannelSender sender : senders) {
      sendersByChannel.put(sender.channel(), sender);
    }
  }

  @Override
  public void dispatchAccountPendingApproval(AccountPendingApprovalEvent event) {
    List<AdminRecipient> admins = adminRecipientPort.findActiveAdmins();
    if (admins.isEmpty()) {
      log.warn(
          "account pending approval: no active admins found newUserId={}", event.newUserId());
      return;
    }

    String correlationId = "account-pending-approval:" + event.newUserId();
    String subject = "New account pending approval";
    String body = buildAdminBody(event);
    Map<String, String> templateVars = Map.of(
        "newUserId", String.valueOf(event.newUserId()),
        "newUserEmail", event.newUserEmail(),
        "newUserFirstName", nullToEmpty(event.newUserFirstName()),
        "newUserLastName", nullToEmpty(event.newUserLastName()),
        "newUserPhone", nullToEmpty(event.newUserPhone()));

    for (AdminRecipient admin : admins) {
      dispatchToAdmin(admin, correlationId, subject, body, templateVars, event.newUserId());
    }
  }

  private void dispatchToAdmin(
      AdminRecipient admin,
      String correlationId,
      String subject,
      String body,
      Map<String, String> templateVars,
      Long relatedUserId) {

    Notification logNotification = baseNotification(
            NotificationChannel.LOG, admin, correlationId, subject, body, templateVars, relatedUserId)
        .compose();
    send(logNotification);

    if (admin.hasEmail()) {
      Notification emailNotification = baseNotification(
              NotificationChannel.EMAIL, admin, correlationId, subject, body, templateVars, relatedUserId)
          .recipientEmail(admin.email())
          .compose();
      send(emailNotification);
    }

    if (admin.hasPhone()) {
      Notification smsNotification = baseNotification(
              NotificationChannel.SMS, admin, correlationId, subject, body, templateVars, relatedUserId)
          .recipientPhone(admin.phone())
          .compose();
      send(smsNotification);
    }
  }

  private static Notification.SafeBuilder baseNotification(
      NotificationChannel channel,
      AdminRecipient admin,
      String correlationId,
      String subject,
      String body,
      Map<String, String> templateVars,
      Long relatedUserId) {
    return Notification.builder()
        .channel(channel)
        .type(NotificationType.ACCOUNT_PENDING_APPROVAL)
        .status(NotificationStatus.PENDING)
        .recipientUserId(admin.userId())
        .recipientDisplayName(admin.displayName())
        .subject(subject)
        .body(body)
        .templateId("account-pending-approval-admin")
        .templateVariables(templateVars)
        .correlationId(correlationId)
        .relatedUserId(relatedUserId);
  }

  private void send(Notification notification) {
    NotificationChannelSender sender = sendersByChannel.get(notification.getChannel());
    if (sender == null) {
      log.warn("no sender registered for channel={}", notification.getChannel());
      return;
    }
    sender.send(notification);
  }

  private static String buildAdminBody(AccountPendingApprovalEvent event) {
    return "A new user registered and is pending approval."
        + " Email: "
        + event.newUserEmail()
        + ", name: "
        + nullToEmpty(event.newUserFirstName())
        + " "
        + nullToEmpty(event.newUserLastName())
        + ", phone: "
        + nullToEmpty(event.newUserPhone())
        + ", userId: "
        + event.newUserId()
        + ".";
  }

  private static String nullToEmpty(String value) {
    return value == null ? "" : value;
  }
}
