package io.github.alexistrejo11.pimienta.config.cors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * CORS settings under {@code pimienta.cors.*} in application.yaml. Adjust origins for each
 * environment; browsers only send credentials to listed origins (not {@code *}).
 */
@ConfigurationProperties(prefix = "pimienta.cors")
public class CorsProperties {

  /** Ant-style path pattern applied to {@link org.springframework.web.cors.UrlBasedCorsConfigurationSource}. */
  private String pathPattern = "/**";

  private List<String> allowedOrigins = new ArrayList<>();

  private List<String> allowedMethods =
      new ArrayList<>(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));

  private List<String> allowedHeaders = new ArrayList<>(List.of("*"));

  /** Headers the browser may read from the response (e.g. pagination). */
  private List<String> exposedHeaders =
      new ArrayList<>(List.of("Authorization", "Content-Type", "X-Total-Count"));

  private boolean allowCredentials = true;

  /** Preflight cache (Access-Control-Max-Age), seconds. */
  private long maxAgeSeconds = 3600;

  public String getPathPattern() {
    return pathPattern;
  }

  public void setPathPattern(String pathPattern) {
    this.pathPattern = pathPattern;
  }

  public List<String> getAllowedOrigins() {
    return allowedOrigins;
  }

  public void setAllowedOrigins(List<String> allowedOrigins) {
    this.allowedOrigins = allowedOrigins != null ? allowedOrigins : new ArrayList<>();
  }

  public List<String> getAllowedMethods() {
    return allowedMethods;
  }

  public void setAllowedMethods(List<String> allowedMethods) {
    this.allowedMethods = allowedMethods != null ? allowedMethods : new ArrayList<>();
  }

  public List<String> getAllowedHeaders() {
    return allowedHeaders;
  }

  public void setAllowedHeaders(List<String> allowedHeaders) {
    this.allowedHeaders = allowedHeaders != null ? allowedHeaders : new ArrayList<>();
  }

  public List<String> getExposedHeaders() {
    return exposedHeaders;
  }

  public void setExposedHeaders(List<String> exposedHeaders) {
    this.exposedHeaders = exposedHeaders != null ? exposedHeaders : new ArrayList<>();
  }

  public boolean isAllowCredentials() {
    return allowCredentials;
  }

  public void setAllowCredentials(boolean allowCredentials) {
    this.allowCredentials = allowCredentials;
  }

  public long getMaxAgeSeconds() {
    return maxAgeSeconds;
  }

  public void setMaxAgeSeconds(long maxAgeSeconds) {
    this.maxAgeSeconds = maxAgeSeconds;
  }
}
