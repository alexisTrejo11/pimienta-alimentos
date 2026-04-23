package io.github.alexistrejo11.pimienta.module.account.user.core.port.output;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.UserStatistics;

public interface UserRepository {

  Optional<User> findById(Long id);

  Optional<User> findByIdAndDeletedAtIsNull(Long id);

  Optional<User> findByEmail(String email);

  Optional<User> findByPhone(String phone);

  Page<User> findAll(Pageable pageable);

  User save(User user);

  UserStatistics statistics();
}
