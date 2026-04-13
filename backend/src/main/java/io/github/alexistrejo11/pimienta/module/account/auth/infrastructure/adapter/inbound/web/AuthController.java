package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.account.auth.core.application.AuthUseCases;
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
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthUseCases authUseCases;

  public AuthController(AuthUseCases authUseCases) {
    this.authUseCases = authUseCases;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public TokenResponse register(@Valid @RequestBody RegisterRequest request) {
    RegisterCommand command = AuthWebMapper.toRegisterCommand(request);
    IssuedTokens issued = authUseCases.register(command);
    return AuthWebMapper.toResponse(issued);
  }

  @PostMapping("/login")
  public TokenResponse login(@Valid @RequestBody LoginRequest request) {
    LoginCommand command = AuthWebMapper.toLoginCommand(request);
    IssuedTokens issued = authUseCases.login(command);
    return AuthWebMapper.toResponse(issued);
  }

  @PostMapping("/refresh")
  public TokenResponse refresh(@Valid @RequestBody RefreshRequest request) {
    RefreshSessionCommand command = AuthWebMapper.toRefreshCommand(request);
    IssuedTokens issued = authUseCases.refresh(command);
    return AuthWebMapper.toResponse(issued);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(@RequestBody(required = false) LogoutRequest request) {
    LogoutCommand command = AuthWebMapper.toLogoutCommand(request);
    authUseCases.logout(command);
  }
}
