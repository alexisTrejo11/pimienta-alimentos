package io.github.alexistrejo11.pimienta.module.account.user.core.application;

import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.AddRolesCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.BanUserCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.UserStatistics;
import io.github.alexistrejo11.pimienta.module.account.user.core.port.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserManagementUseCasesImpl implements UserManagementUseCases {

  private final UserRepository userRepository;

  public UserManagementUseCasesImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserStatistics statistics() {
    return userRepository.statistics();
  }

  @Override
  public Page<User> getBy(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @Override
  public User getById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  @Override
  public User getByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException(email));
  }

  @Override
  public void ban(Long userId, BanUserCommand command) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    user.ban(command.reason());
    userRepository.save(user);
  }

  @Override
  public void unban(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    user.unban();
    userRepository.save(user);
  }

  @Override
  public User addRoles(Long userId, AddRolesCommand command) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    user.addRoles(command.roles());
    return userRepository.save(user);
  }
}
