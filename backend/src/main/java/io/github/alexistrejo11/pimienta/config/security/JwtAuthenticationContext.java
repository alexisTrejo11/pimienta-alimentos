package io.github.alexistrejo11.pimienta.config.security;

import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity.ParsedAccessToken;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * Central security principal for stateless JWT access tokens: all claims from {@link
 * ParsedAccessToken} are reachable from this single object on the {@link
 * org.springframework.security.core.context.SecurityContext}.
 */
public final class JwtAuthenticationContext implements Principal, Serializable {

  private static final long serialVersionUID = 1L;

  private final ParsedAccessToken accessToken;

  public JwtAuthenticationContext(ParsedAccessToken accessToken) {
    this.accessToken = Objects.requireNonNull(accessToken, "accessToken");
  }

  public ParsedAccessToken accessToken() {
    return accessToken;
  }

  public long userId() {
    Long id = accessToken.userId();
    if (id == null) {
      throw new IllegalStateException("JWT subject (user id) is missing");
    }
    return id;
  }

  public String email() {
    return accessToken.email();
  }

  public List<String> roles() {
    return accessToken.roles();
  }

  public List<String> permissions() {
    return accessToken.permissions();
  }

  @Override
  public String getName() {
    String email = accessToken.email();
    return email != null && !email.isBlank() ? email : String.valueOf(accessToken.userId());
  }

  @Override
  public String toString() {
    return "JwtAuthenticationContext(userId=" + accessToken.userId() + ")";
  }
}
