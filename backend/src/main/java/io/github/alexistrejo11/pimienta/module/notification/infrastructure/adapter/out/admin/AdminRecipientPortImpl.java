package io.github.alexistrejo11.pimienta.module.notification.infrastructure.adapter.out.admin;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.enums.Role;
import io.github.alexistrejo11.pimienta.module.account.user.core.port.output.UserRepository;
import io.github.alexistrejo11.pimienta.module.notification.core.application.dto.AdminRecipient;
import io.github.alexistrejo11.pimienta.module.notification.core.port.output.AdminRecipientPort;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AdminRecipientPortImpl implements AdminRecipientPort {

  private final UserRepository userRepository;

  public AdminRecipientPortImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<AdminRecipient> findActiveAdmins() {
    return userRepository.findActiveByRole(Role.ADMIN).stream()
        .map(AdminRecipientPortImpl::toRecipient)
        .toList();
  }

  private static AdminRecipient toRecipient(User user) {
    String displayName = (user.getFirstName() + " " + user.getLastName()).trim();
    return new AdminRecipient(user.getId(), user.getEmail(), user.getPhone(), displayName);
  }
}
