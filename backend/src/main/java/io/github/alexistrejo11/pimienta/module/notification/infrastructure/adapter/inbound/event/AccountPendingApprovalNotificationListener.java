package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.inbound.event;

import io.github.alexistrejo11.pimienta.module.notification.core.application.event.AccountPendingApprovalEvent;
import io.github.alexistrejo11.pimienta.module.notification.core.port.input.NotificationDispatchUseCases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AccountPendingApprovalNotificationListener {

  private static final Logger log =
      LoggerFactory.getLogger(AccountPendingApprovalNotificationListener.class);

  private final NotificationDispatchUseCases notificationDispatchUseCases;

  public AccountPendingApprovalNotificationListener(
      NotificationDispatchUseCases notificationDispatchUseCases) {
    this.notificationDispatchUseCases = notificationDispatchUseCases;
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onAccountPendingApproval(AccountPendingApprovalEvent event) {
    log.debug("consuming AccountPendingApprovalEvent newUserId={}", event.newUserId());
    notificationDispatchUseCases.dispatchAccountPendingApproval(event);
  }
}
