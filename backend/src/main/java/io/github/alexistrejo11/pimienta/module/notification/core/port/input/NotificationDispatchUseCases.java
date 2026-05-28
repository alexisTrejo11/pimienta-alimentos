package io.github.alexistrejo11.pimienta.module.notification.core.port.input;

import io.github.alexistrejo11.pimienta.module.notification.core.application.event.AccountPendingApprovalEvent;

public interface NotificationDispatchUseCases {

  void dispatchAccountPendingApproval(AccountPendingApprovalEvent event);
}
