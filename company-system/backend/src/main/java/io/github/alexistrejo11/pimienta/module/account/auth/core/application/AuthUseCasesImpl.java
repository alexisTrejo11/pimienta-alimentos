package io.github.alexistrejo11.pimienta.module.account.auth.core.application;

import io.github.alexistrejo11.pimienta.module.account.auth.core.application.command.LoginCommand;
import io.github.alexistrejo11.pimienta.module.account.auth.core.application.command.LogoutCommand;
import io.github.alexistrejo11.pimienta.module.account.auth.core.application.command.RefreshSessionCommand;
import io.github.alexistrejo11.pimienta.module.account.auth.core.application.command.RegisterCommand;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.IssuedTokens;
import io.github.alexistrejo11.pimienta.module.account.auth.core.port.RefreshTokenStore;
import io.github.alexistrejo11.pimienta.module.account.auth.core.port.TokenService;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.EmailAlreadyExistsException;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.UserNotFoundException;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.port.UserRepository;
import io.github.alexistrejo11.pimienta.shared.exception.AuthenticationException;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCasesImpl implements AuthUseCases {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;
  private final RefreshTokenStore refreshTokenStore;

  public AuthUseCasesImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      TokenService tokenService,
      RefreshTokenStore refreshTokenStore) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
    this.refreshTokenStore = refreshTokenStore;
  }

  @Override
  public IssuedTokens register(RegisterCommand command) {
    String email = command.email().trim().toLowerCase();
    if (userRepository.findByEmail(email).isPresent()) {
      throw new EmailAlreadyExistsException(email);
    }
    String hash = passwordEncoder.encode(command.password());
    User created = User.register(email, hash, command.displayName());
    User saved = userRepository.save(created);
    return tokenService.issuePair(saved);
  }

  @Override
  public IssuedTokens login(LoginCommand command) {
    String email = command.email().trim().toLowerCase();
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () ->
                    new AuthenticationException(
                        "Invalid email or password.",
                        Map.of("email", email),
                        "Login failed: user not found " + email));
    if (user.isBanned()) {
      throw new AuthenticationException(
          "This account has been suspended.",
          Map.of("userId", user.getId()),
          "Login blocked: banned user id=" + user.getId());
    }
    if (!passwordEncoder.matches(command.password(), user.getPasswordHash())) {
      throw new AuthenticationException(
          "Invalid email or password.",
          Map.of("email", email),
          "Login failed: bad password for " + email);
    }
    return tokenService.issuePair(user);
  }

  @Override
  public IssuedTokens refresh(RefreshSessionCommand command) {
    var validated = tokenService.validateRefreshToken(command.refreshToken());
    User user =
        userRepository
            .findById(validated.userId())
            .orElseThrow(() -> new UserNotFoundException(validated.userId()));
    if (user.isBanned()) {
      refreshTokenStore.remove(validated.jti());
      throw new AuthenticationException(
          "This account has been suspended.",
          Map.of("userId", validated.userId()),
          "Refresh blocked: banned user id=" + validated.userId());
    }
    refreshTokenStore.remove(validated.jti());
    return tokenService.issuePair(user);
  }

  @Override
  public void logout(LogoutCommand command) {
    if (command.refreshToken() == null || command.refreshToken().isBlank()) {
      return;
    }
    String jti = tokenService.extractJtiFromRefreshToken(command.refreshToken());
    refreshTokenStore.remove(jti);
  }
}
