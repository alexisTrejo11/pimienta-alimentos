package io.github.alexistrejo11.pimienta.module.account.user.core.port;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.UserStatistics;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);

  Page<User> findAll(Pageable pageable);

  User save(User user);

  UserStatistics statistics();
}
