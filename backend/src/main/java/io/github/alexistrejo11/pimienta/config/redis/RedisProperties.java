package io.github.alexistrejo11.pimienta.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis application settings. Key prefix is supplied via {@code PIMIENTA_REDIS_KEY_PREFIX} in
 * {@code .env} and bound through {@code pimienta.redis.key-prefix} in {@code application.yaml}.
 */
@ConfigurationProperties(prefix = "pimienta.redis")
public class RedisProperties {

  /** From {@code PIMIENTA_REDIS_KEY_PREFIX}; trailing colon added when missing. */
  private String keyPrefix;

  public String getKeyPrefix() {
    return keyPrefix;
  }

  public void setKeyPrefix(String keyPrefix) {
    this.keyPrefix = keyPrefix;
  }

  /** Empty when unset/blank (no prefix). */
  public String normalizedKeyPrefix() {
    if (keyPrefix == null || keyPrefix.isBlank()) {
      return "";
    }
    return keyPrefix.endsWith(":") ? keyPrefix : keyPrefix + ":";
  }
}
