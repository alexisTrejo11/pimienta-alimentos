package io.github.alexistrejo11.pimienta.config.env;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

/**
 * Merges a {@code .env} file from the working directory into the environment. Missing files are
 * ignored. Real OS environment variables and JVM system properties keep higher precedence than this
 * source (see insertion point relative to {@value
 * StandardEnvironment#SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME}).
 */
public final class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

  static final String PROPERTY_SOURCE_NAME = "dotenv";

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    Map<String, Object> map = new LinkedHashMap<>();
    for (DotenvEntry e : dotenv.entries()) {
      map.putIfAbsent(e.getKey(), e.getValue());
    }
    if (map.isEmpty()) {
      return;
    }

    MutablePropertySources propertySources = environment.getPropertySources();
    if (propertySources.contains(PROPERTY_SOURCE_NAME)) {
      return;
    }

    MapPropertySource dotenvSource = new MapPropertySource(PROPERTY_SOURCE_NAME, map);
    if (propertySources.contains(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)) {
      propertySources.addAfter(
          StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, dotenvSource);
    } else {
      propertySources.addLast(dotenvSource);
    }
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}
