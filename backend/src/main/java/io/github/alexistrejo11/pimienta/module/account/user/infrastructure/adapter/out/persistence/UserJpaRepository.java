package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

  Optional<UserJpaEntity> findByEmailIgnoreCase(String email);

  long countByBannedTrue();
}
