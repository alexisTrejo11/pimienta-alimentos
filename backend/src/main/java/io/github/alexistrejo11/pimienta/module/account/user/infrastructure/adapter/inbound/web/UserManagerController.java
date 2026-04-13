package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.account.user.core.application.UserManagementUseCases;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.AddRolesCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.BanUserCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.UserStatistics;
import io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto.AddRolesRequest;
import io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto.BanUserRequest;
import io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto.UserResponse;
import io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto.UserStatisticsResponse;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/management")
public class UserManagerController {

  private final UserManagementUseCases userManagementUseCases;

  public UserManagerController(UserManagementUseCases userManagementUseCases) {
    this.userManagementUseCases = userManagementUseCases;
  }

  @GetMapping("/statistics")
  public UserStatisticsResponse getStatistics() {
    UserStatistics statistics = userManagementUseCases.statistics();
    return UserManagerWebMapper.toStatisticsResponse(statistics);
  }

  @GetMapping
  public PagedResponse<UserResponse> listUsers(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    Page<User> users = userManagementUseCases.getBy(PageRequest.of(page, size));
    return PagedResponse.map(users, UserManagerWebMapper::toResponse);
  }

  @GetMapping("/{id}")
  public UserResponse getUserById(@PathVariable Long id) {
    User user = userManagementUseCases.getById(id);
    return UserManagerWebMapper.toResponse(user);
  }

  @GetMapping("/by-email")
  public UserResponse getUserByEmail(@RequestParam @Email String email) {
    User user = userManagementUseCases.getByEmail(email.trim().toLowerCase());
    return UserManagerWebMapper.toResponse(user);
  }

  @PostMapping("/{id}/ban")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void banUser(@PathVariable Long id, @Valid @RequestBody(required = false) BanUserRequest request) {
    BanUserCommand command = UserManagerWebMapper.toBanCommand(request);
    userManagementUseCases.ban(id, command);
  }

  @PostMapping("/{id}/unban")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unbanUser(@PathVariable Long id) {
    userManagementUseCases.unban(id);
  }

  @PostMapping("/{id}/roles")
  public UserResponse addRoles(@PathVariable Long id, @Valid @RequestBody AddRolesRequest request) {
    AddRolesCommand command = UserManagerWebMapper.toAddRolesCommand(request);
    User updated = userManagementUseCases.addRoles(id, command);
    return UserManagerWebMapper.toResponse(updated);
  }
}
