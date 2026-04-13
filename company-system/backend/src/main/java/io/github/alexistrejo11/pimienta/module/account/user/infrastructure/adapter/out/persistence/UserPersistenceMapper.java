package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.Role;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;

final class UserPersistenceMapper {

  private UserPersistenceMapper() {}

  static UserJpaEntity toJpa(User domain) {
    var e = new UserJpaEntity();
    if (domain.getId() != null && domain.getId() > 0) {
      e.setId(domain.getId());
    }
    e.setEmail(domain.getEmail());
    e.setPasswordHash(domain.getPasswordHash());
    e.setFirstName(domain.getFirstName());
    e.setLastName(domain.getLastName());
    e.setGender(domain.getGender());
    e.setPhone(domain.getPhone());
    e.setDateOfBirth(domain.getDateOfBirth());
    e.setBanned(domain.isBanned());
    e.setBannedReason(domain.getBannedReason());
    e.setBannedAt(domain.getBannedAt());
    e.setRoles(new LinkedHashSet<>(domain.getRoles()));
    e.setCreatedAt(domain.getCreatedAt());
    e.setUpdatedAt(domain.getUpdatedAt());
    e.setDeletedAt(domain.getDeletedAt());
    e.setVersion(domain.getVersion() != null ? domain.getVersion() : 0L);
    return e;
  }

  static User toDomain(UserJpaEntity e) {
    var roles = new ArrayList<Role>();
    if (e.getRoles() != null) {
      roles.addAll(e.getRoles());
    }
    return User.reconstruct(
        e.getId(),
        e.getEmail(),
        e.getPasswordHash(),
        e.getFirstName(),
        e.getLastName(),
        e.getGender(),
        e.getPhone(),
        e.getDateOfBirth(),
        e.isBanned(),
        e.getBannedReason(),
        e.getBannedAt(),
        roles,
        e.getCreatedAt(),
        e.getUpdatedAt(),
        e.getDeletedAt(),
        e.getVersion());
  }
}
