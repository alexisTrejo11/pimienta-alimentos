package io.github.alexistrejo11.pimienta.config.redis;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Registers a namespaced {@link StringRedisTemplate} before Boot's default template so every
 * Redis consumer shares the same key prefix on shared cloud instances.
 */
@AutoConfiguration(before = DataRedisAutoConfiguration.class)
@ConditionalOnClass(StringRedisTemplate.class)
@EnableConfigurationProperties(RedisProperties.class)
public class PimientaRedisAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(StringRedisTemplate.class)
  StringRedisTemplate stringRedisTemplate(
      RedisConnectionFactory connectionFactory, RedisProperties properties) {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(connectionFactory);
    String prefix = properties.normalizedKeyPrefix();
    if (!prefix.isEmpty()) {
      PrefixedStringRedisSerializer serializer = new PrefixedStringRedisSerializer(prefix);
      template.setKeySerializer(serializer);
      template.setHashKeySerializer(serializer);
    }
    template.afterPropertiesSet();
    return template;
  }
}
