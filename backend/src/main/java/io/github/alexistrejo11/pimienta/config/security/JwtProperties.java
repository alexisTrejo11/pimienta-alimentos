package io.github.alexistrejo11.pimienta.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pimienta.security.jwt")
public class JwtProperties {

  /**
   * HS256 secret; must be at least 256 bits (32 ASCII chars) for JJWT.
   */
  private String secret =
      "change-me-use-at-least-256-bits-for-hs256-secret-key-pimienta";

  private int accessTokenTtlMinutes = 15;
  private int refreshTokenTtlDays = 7;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public int getAccessTokenTtlMinutes() {
    return accessTokenTtlMinutes;
  }

  public void setAccessTokenTtlMinutes(int accessTokenTtlMinutes) {
    this.accessTokenTtlMinutes = accessTokenTtlMinutes;
  }

  public int getRefreshTokenTtlDays() {
    return refreshTokenTtlDays;
  }

  public void setRefreshTokenTtlDays(int refreshTokenTtlDays) {
    this.refreshTokenTtlDays = refreshTokenTtlDays;
  }
}
