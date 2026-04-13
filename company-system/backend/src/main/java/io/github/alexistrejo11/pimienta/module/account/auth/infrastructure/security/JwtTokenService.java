package io.github.alexistrejo11.pimienta.module.account.auth.infrastructure.security;

import io.github.alexistrejo11.pimienta.config.security.JwtProperties;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.IssuedTokens;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.ParsedAccessToken;
import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.RefreshTokenValidation;
import io.github.alexistrejo11.pimienta.module.account.auth.core.port.RefreshTokenStore;
import io.github.alexistrejo11.pimienta.module.account.auth.core.port.TokenService;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.Permission;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.Role;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.User;
import io.github.alexistrejo11.pimienta.shared.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService implements TokenService {

  private final JwtProperties properties;
  private final RefreshTokenStore refreshTokenStore;

  public JwtTokenService(JwtProperties properties, RefreshTokenStore refreshTokenStore) {
    this.properties = properties;
    this.refreshTokenStore = refreshTokenStore;
  }

  @Override
  public IssuedTokens issuePair(User user) {
    SecretKey key = signingKey();
    Instant now = Instant.now();
    String accessJti = UUID.randomUUID().toString();
    String refreshJti = UUID.randomUUID().toString();

    List<String> roleNames = user.getRoles().stream().map(Enum::name).toList();
    List<String> permissionNames = collectPermissionNames(user);

    Duration accessTtl = Duration.ofMinutes(properties.getAccessTokenTtlMinutes());
    Duration refreshTtl = Duration.ofDays(properties.getRefreshTokenTtlDays());

    refreshTokenStore.remember(refreshJti, user.getId(), refreshTtl);

    String access =
        Jwts.builder()
            .id(accessJti)
            .subject(String.valueOf(user.getId()))
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(accessTtl)))
            .claim("typ", "access")
            .claim("email", user.getEmail())
            .claim("roles", roleNames)
            .claim("permissions", permissionNames)
            .signWith(key)
            .compact();

    String refresh =
        Jwts.builder()
            .id(refreshJti)
            .subject(String.valueOf(user.getId()))
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(refreshTtl)))
            .claim("typ", "refresh")
            .signWith(key)
            .compact();

    return new IssuedTokens(access, refresh, accessTtl.getSeconds());
  }

  @Override
  public RefreshTokenValidation validateRefreshToken(String refreshToken) {
    try {
      Claims claims = parseClaims(refreshToken);
      if (!"refresh".equals(claims.get("typ"))) {
        throw invalidRefresh("Not a refresh token.");
      }
      String jti = claims.getId();
      if (jti == null || jti.isBlank()) {
        throw invalidRefresh("Missing token id.");
      }
      long sub = Long.parseLong(claims.getSubject());
      Long stored =
          refreshTokenStore
              .findUserId(jti)
              .orElseThrow(() -> invalidRefresh("Refresh token revoked or unknown."));
      if (!stored.equals(sub)) {
        throw invalidRefresh("Refresh token mismatch.");
      }
      return new RefreshTokenValidation(stored, jti);
    } catch (ExpiredJwtException e) {
      throw invalidRefresh("Refresh token expired.", e);
    } catch (JwtException | IllegalArgumentException e) {
      throw invalidRefresh("Invalid refresh token.", e);
    }
  }

  @Override
  public ParsedAccessToken parseAccessToken(String accessToken) {
    try {
      Claims claims = parseClaims(accessToken);
      if (!"access".equals(claims.get("typ"))) {
        throw invalidAccess("Not an access token.");
      }
      String jti = claims.getId();
      long userId = Long.parseLong(claims.getSubject());
      String email = claims.get("email", String.class);
      @SuppressWarnings("unchecked")
      List<String> roles = (List<String>) claims.get("roles", List.class);
      @SuppressWarnings("unchecked")
      List<String> permissions = (List<String>) claims.get("permissions", List.class);
      if (roles == null) {
        roles = List.of();
      }
      if (permissions == null) {
        permissions = List.of();
      }
      return new ParsedAccessToken(userId, email, roles, permissions, jti);
    } catch (ExpiredJwtException e) {
      throw invalidAccess("Access token expired.", e);
    } catch (JwtException | IllegalArgumentException e) {
      throw invalidAccess("Invalid access token.", e);
    }
  }

  @Override
  public String extractJtiFromRefreshToken(String refreshToken) {
    if (refreshToken == null || refreshToken.isBlank()) {
      return null;
    }
    try {
      Claims claims = parseClaims(refreshToken);
      if (!"refresh".equals(claims.get("typ"))) {
        return null;
      }
      return claims.getId();
    } catch (JwtException e) {
      return null;
    }
  }

  private Claims parseClaims(String token) {
    return Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token).getPayload();
  }

  private SecretKey signingKey() {
    byte[] bytes = properties.getSecret().getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(bytes);
  }

  private static List<String> collectPermissionNames(User user) {
    var set = new HashSet<String>();
    for (Role r : user.getRoles()) {
      for (Permission p : r.getPermissions()) {
        set.add(p.name());
      }
    }
    var list = new ArrayList<>(set);
    list.sort(String::compareTo);
    return list;
  }

  private static AuthenticationException invalidRefresh(String message) {
    return new AuthenticationException(
        message, Map.of(), message, null);
  }

  private static AuthenticationException invalidRefresh(String message, Throwable cause) {
    return new AuthenticationException(
        message, Map.of(), message != null ? message : "invalid refresh", cause);
  }

  private static AuthenticationException invalidAccess(String message) {
    return new AuthenticationException(
        message, Map.of(), message, null);
  }

  private static AuthenticationException invalidAccess(String message, Throwable cause) {
    return new AuthenticationException(
        message, Map.of(), message != null ? message : "invalid access", cause);
  }
}
