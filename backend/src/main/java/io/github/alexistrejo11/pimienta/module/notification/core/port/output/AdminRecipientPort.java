package io.github.alexistrejo11.pimienta.module.notification.core.port.output;

import io.github.alexistrejo11.pimienta.module.notification.core.application.dto.AdminRecipient;
import java.util.List;

public interface AdminRecipientPort {

  List<AdminRecipient> findActiveAdmins();
}
