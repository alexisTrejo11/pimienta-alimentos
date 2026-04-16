package io.github.alexistrejo11.pimienta.config.rate_limit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Verifies rate-limiting beans register when {@code pimienta.rate-limiting.enabled=true}
 * (see {@code application.yaml} for tests).
 */
@SpringBootTest
class RateLimitingBeansIT {

  @Autowired ApplicationContext applicationContext;
  @Autowired Environment environment;

  @Test
  void redisAndEnabledPropertyArePresentSoLimiterCanRegister() {
    assertThat(applicationContext.getBeanNamesForType(StringRedisTemplate.class))
        .as("StringRedisTemplate must exist for RedisTokenBucketRateLimiter")
        .isNotEmpty();
    assertThat(environment.getProperty("pimienta.rate-limiting.enabled"))
        .as("pimienta.rate-limiting.enabled from application.yaml")
        .isEqualTo("true");
  }

  @Test
  void redisTokenBucketRateLimiterBeanIsRegistered() {
    assertThat(applicationContext.getBeanNamesForType(RedisTokenBucketRateLimiter.class))
        .isNotEmpty();
  }

  @Test
  void globalRateLimitFilterRegistrationExists() {
    assertThat(applicationContext.containsBean("globalRateLimitFilterRegistration"))
        .as("FilterRegistrationBean for GlobalRateLimitFilter")
        .isTrue();
  }
}
