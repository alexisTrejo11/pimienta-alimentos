package io.github.alexistrejo11.pimienta.module.account.user.core.application;

import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.AddRolesCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.BanUserCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.UserStatistics;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.exceptions.UserNotFoundException;
import io.github.alexistrejo11.pimienta.module.account.user.core.port.input.UserManagementUseCases;
import io.github.alexistrejo11.pimienta.module.account.user.core.port.output.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagementUseCasesImpl implements UserManagementUseCases {

  private static final Logger log = LoggerFactory.getLogger(UserManagementUseCasesImpl.class);

  private final UserRepository userRepository;

  public UserManagementUseCasesImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserStatistics statistics() {
    log.debug("statistics query start");
    UserStatistics stats = userRepository.statistics();

    log.debug(
        "statistics query complete totalUsers={} activeUsers={} bannedUsers={}",
        stats.totalUsers(),
        stats.activeUsers(),
        stats.bannedUsers());
    return stats;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<User> getBy(Pageable pageable) {
    log.debug(
        "list users query start page={} size={}",
        pageable != null ? pageable.getPageNumber() : null,
        pageable != null ? pageable.getPageSize() : null);
    Page<User> page = userRepository.findAll(pageable);

    log.debug("list users query complete totalElements={} numberOfElements={}", page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  @Transactional(readOnly = true)
  public User getById(Long id) {
    log.debug("get user by id query start userId={}", id);
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    log.debug("get user by id query complete userId={}", user.getId());
    return user;
  }

  @Override
  @Transactional(readOnly = true)
  public User getByEmail(String email) {
    String forLog = email == null ? "null" : email.trim().toLowerCase();
    log.debug("get user by email query start email={}", forLog);

    User user = userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException(email));

    log.debug("get user by email query complete userId={}", user.getId());
    return user;
  }

  @Override
  @Transactional
  public void ban(Long userId, BanUserCommand command) {
    log.info("ban user start userId={}", userId);
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    user.ban(command.reason());

    userRepository.save(user);
    log.info("ban user complete userId={}", userId);
  }

  @Override
  @Transactional
  public void unban(Long userId) {
    log.info("unban user start userId={}", userId);

    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    user.unban();

    userRepository.save(user);
    log.info("unban user complete userId={}", userId);
  }

  @Override
  @Transactional
  public User addRoles(Long userId, AddRolesCommand command) {
    int roleCount = command.roles() != null ? command.roles().size() : 0;
    log.info("add roles start userId={} roleCount={}", userId, roleCount);

    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    user.addRoles(command.roles());
    User saved = userRepository.save(user);

    log.info("add roles complete userId={}", userId);
    return saved;
  }

  @Override
  @Transactional
  public void approve(Long userId) {
    log.info("approve user start userId={}", userId);
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    user.activate();
    userRepository.save(user);
    log.info("approve user complete userId={}", userId);
  }
}
