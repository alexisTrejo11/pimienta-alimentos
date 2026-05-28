package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.enums.AccountStatus;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.enums.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

  Optional<UserJpaEntity> findByPhoneAndDeletedAtIsNull(String phone);

  Optional<UserJpaEntity> findByEmailAndDeletedAtIsNull(String email);

  Optional<UserJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  long countByAccountStatus(AccountStatus accountStatus);

  @Query(
      """
      SELECT DISTINCT u FROM UserJpaEntity u
      JOIN u.roles r
      WHERE r = :role
        AND u.deletedAt IS NULL
        AND u.accountStatus = :activeStatus
      """)
  List<UserJpaEntity> findActiveByRole(
      @Param("role") Role role, @Param("activeStatus") AccountStatus activeStatus);
}
