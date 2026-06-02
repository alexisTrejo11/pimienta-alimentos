package io.github.alexistrejo11.pimienta.config.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Prepends a fixed namespace to every Redis key so multiple apps can share one instance safely.
 */
final class PrefixedStringRedisSerializer implements RedisSerializer<String> {

  private final String prefix;
  private final StringRedisSerializer delegate = StringRedisSerializer.UTF_8;

  PrefixedStringRedisSerializer(String prefix) {
    this.prefix = prefix;
  }

  @Override
  public byte[] serialize(String key) throws SerializationException {
    if (key == null) {
      return null;
    }
    return delegate.serialize(prefix + key);
  }

  @Override
  public String deserialize(byte[] bytes) throws SerializationException {
    String key = delegate.deserialize(bytes);
    if (key == null || prefix.isEmpty()) {
      return key;
    }
    if (key.startsWith(prefix)) {
      return key.substring(prefix.length());
    }
    return key;
  }
}
