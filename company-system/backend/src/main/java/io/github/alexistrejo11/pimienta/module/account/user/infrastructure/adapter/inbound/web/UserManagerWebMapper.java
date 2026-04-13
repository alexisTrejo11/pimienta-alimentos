package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.AddRolesCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.BanUserCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.Role;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.UserStatistics;
import io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto.AddRolesRequest;
import io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto.BanUserRequest;
import io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto.UserResponse;
import io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto.UserStatisticsResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

final class UserManagerWebMapper {

  private UserManagerWebMapper() {}

  static UserStatisticsResponse toStatisticsResponse(UserStatistics statistics) {
    return new UserStatisticsResponse(
        statistics.totalUsers(), statistics.activeUsers(), statistics.bannedUsers());
  }

  static UserResponse toResponse(User user) {
    List<String> roleNames = user.getRoles().stream().map(Enum::name).toList();
    var permSet = new HashSet<String>();
    for (Role r : user.getRoles()) {
      r.getPermissions().forEach(p -> permSet.add(p.name()));
    }
    var permissions = new ArrayList<>(permSet);
    permissions.sort(String::compareTo);
    return new UserResponse(
        user.getId(),
        user.getEmail(),
        user.getDisplayName(),
        user.isBanned(),
        user.getBannedReason(),
        user.getBannedAt(),
        roleNames,
        permissions,
        user.getCreatedAt(),
        user.getUpdatedAt());
  }

  static BanUserCommand toBanCommand(BanUserRequest request) {
    if (request == null) {
      return new BanUserCommand(null);
    }
    return new BanUserCommand(request.reason());
  }

  static AddRolesCommand toAddRolesCommand(AddRolesRequest request) {
    List<Role> roles = new ArrayList<>();
    for (String name : request.roles()) {
      roles.add(Role.valueOf(name.trim().toUpperCase()));
    }
    return new AddRolesCommand(roles);
  }
}
