package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.account.auth.core.application.AuthUseCases;
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
    return AuthWebMapper.toResponse(authUseCases.register(AuthWebMapper.toRegisterCommand(request)));
  }

  @PostMapping("/login")
  public TokenResponse login(@Valid @RequestBody LoginRequest request) {
    return AuthWebMapper.toResponse(authUseCases.login(AuthWebMapper.toLoginCommand(request)));
  }

  @PostMapping("/refresh")
  public TokenResponse refresh(@Valid @RequestBody RefreshRequest request) {
    return AuthWebMapper.toResponse(
        authUseCases.refresh(AuthWebMapper.toRefreshCommand(request)));
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(@RequestBody(required = false) LogoutRequest request) {
    authUseCases.logout(AuthWebMapper.toLogoutCommand(request));
  }
}
