package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.UserStatistics;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.enums.AccountStatus;
import io.github.alexistrejo11.pimienta.module.account.user.core.port.output.UserRepository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository jpaRepository;

  public UserRepositoryImpl(UserJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Optional<User> findById(Long id) {
    return jpaRepository.findById(id).map(UserPersistenceMapper::toDomain);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    if (email == null || email.isBlank()) {
      return Optional.empty();
    }
    return jpaRepository.findByEmailIgnoreCase(email.trim()).map(UserPersistenceMapper::toDomain);
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    return jpaRepository.findAll(pageable).map(UserPersistenceMapper::toDomain);
  }

  @Override
  public User save(User user) {
    var entity = UserPersistenceMapper.toJpa(user);
    var saved = jpaRepository.save(entity);
    return UserPersistenceMapper.toDomain(saved);
  }

  @Override
  public UserStatistics statistics() {
    long total = jpaRepository.count();
    long banned = jpaRepository.countByAccountStatus(AccountStatus.BANNED);
    long active = jpaRepository.countByAccountStatus(AccountStatus.ACTIVE);
    return new UserStatistics(total, active, banned);
  }
}
