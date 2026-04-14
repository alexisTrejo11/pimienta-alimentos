package io.github.alexistrejo11.pimienta.config.cors;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig {

  @Bean
  public CorsConfigurationSource corsConfigurationSource(CorsProperties props) {
    CorsConfiguration cors = new CorsConfiguration();
    cors.setAllowedOrigins(props.getAllowedOrigins());
    cors.setAllowedMethods(props.getAllowedMethods());
    cors.setAllowedHeaders(props.getAllowedHeaders());
    cors.setExposedHeaders(props.getExposedHeaders());
    cors.setAllowCredentials(props.isAllowCredentials());
    cors.setMaxAge(props.getMaxAgeSeconds());

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(props.getPathPattern(), cors);
    return source;
  }
}
