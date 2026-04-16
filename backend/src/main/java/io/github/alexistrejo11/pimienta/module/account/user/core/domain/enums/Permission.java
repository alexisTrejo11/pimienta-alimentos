package io.github.alexistrejo11.pimienta.module.account.user.core.domain.enums;

/** Fine-grained authorization; derived from {@link Role} assignments. */
public enum Permission {
  PROFILE_READ,
  PROFILE_WRITE,
  USERS_READ,
  USERS_WRITE,
  USERS_BAN,
  USERS_MANAGE_ROLES
}
