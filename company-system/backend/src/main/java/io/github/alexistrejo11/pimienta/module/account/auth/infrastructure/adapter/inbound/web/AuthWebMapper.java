package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.account.auth.core.application.command.LoginCommand;
import io.github.alexistrejo11.pimienta.module.account.auth.core.application.command.LogoutCommand;
import io.github.alexistrejo11.pimienta.module.account.auth.core.application.command.RefreshSessionCommand;
import io.github.alexistrejo11.pimienta.module.account.auth.core.application.command.RegisterCommand;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.IssuedTokens;
import io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.dto.LoginRequest;
import io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.dto.LogoutRequest;
import io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.dto.RefreshRequest;
import io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.dto.RegisterRequest;
import io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web.dto.TokenResponse;

final class AuthWebMapper {

  private AuthWebMapper() {}

  static RegisterCommand toRegisterCommand(RegisterRequest request) {
    return new RegisterCommand(
        request.email(),
        request.password(),
        request.firstName(),
        request.lastName(),
        request.gender(),
        request.phone(),
        request.dateOfBirth());
  }

  static LoginCommand toLoginCommand(LoginRequest request) {
    return new LoginCommand(request.email(), request.password());
  }

  static RefreshSessionCommand toRefreshCommand(RefreshRequest request) {
    return new RefreshSessionCommand(request.refreshToken());
  }

  static LogoutCommand toLogoutCommand(LogoutRequest request) {
    return new LogoutCommand(request != null ? request.refreshToken() : null);
  }

  static TokenResponse toResponse(IssuedTokens tokens) {
    return new TokenResponse(
        tokens.accessToken(), tokens.refreshToken(), "Bearer", tokens.accessExpiresInSeconds());
  }
}
