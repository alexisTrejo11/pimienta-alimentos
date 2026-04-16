package io.github.alexistrejo11.pimienta.config.rate_limit;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Runs after {@link DataRedisAutoConfiguration} so {@link StringRedisTemplate} exists when
 * {@link ConditionalOnBean} is evaluated (user {@code @Configuration} can run too early).
 */
@AutoConfiguration(after = DataRedisAutoConfiguration.class)
@EnableConfigurationProperties(RateLimitingProperties.class)
@ConditionalOnProperty(prefix = "pimienta.rate-limiting", name = "enabled", havingValue = "true")
@ConditionalOnBean(StringRedisTemplate.class)
public class RateLimitingAutoConfiguration {

  @Bean
  RedisTokenBucketRateLimiter redisTokenBucketRateLimiter(
      StringRedisTemplate stringRedisTemplate, RateLimitingProperties properties) {
    return new RedisTokenBucketRateLimiter(stringRedisTemplate, properties);
  }

  @Bean
  FilterRegistrationBean<GlobalRateLimitFilter> globalRateLimitFilterRegistration(
      RedisTokenBucketRateLimiter rateLimiter, RateLimitingProperties properties) {
    GlobalRateLimitFilter filter = new GlobalRateLimitFilter(rateLimiter, properties);
    FilterRegistrationBean<GlobalRateLimitFilter> bean = new FilterRegistrationBean<>(filter);
    bean.addUrlPatterns("/api/*");
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 40);
    return bean;
  }

  @Bean
  AnnotatedRateLimitInterceptor annotatedRateLimitInterceptor(
      RedisTokenBucketRateLimiter rateLimiter, RateLimitingProperties properties) {
    return new AnnotatedRateLimitInterceptor(rateLimiter, properties);
  }

  @Bean
  WebMvcConfigurer rateLimitWebMvcConfigurer(AnnotatedRateLimitInterceptor interceptor) {
    return new WebMvcConfigurer() {
      @Override
      public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api/**");
      }
    };
  }
}
