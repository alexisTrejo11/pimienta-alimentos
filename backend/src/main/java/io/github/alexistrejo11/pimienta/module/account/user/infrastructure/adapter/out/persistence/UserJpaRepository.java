package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.enums.AccountStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

  Optional<UserJpaEntity> findByPhoneAndDeletedAtIsNull(String phone);

  Optional<UserJpaEntity> findByEmailAndDeletedAtIsNull(String email);

  Optional<UserJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  long countByAccountStatus(AccountStatus accountStatus);
}
