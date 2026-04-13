package io.github.alexistrejo11.pimienta.module.account.auth.core.port;

import java.time.Duration;
import java.util.Optional;

public interface RefreshTokenStore {

  void remember(String jti, Long userId, Duration ttl);

  Optional<Long> findUserId(String jti);

  void remove(String jti);
}
