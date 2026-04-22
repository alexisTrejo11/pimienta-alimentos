package io.github.alexistrejo11.pimienta.config.logger;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuditLoggerConfig implements WebMvcConfigurer {

  @Autowired
  private AuditLogger auditLogger;

  @Value("${spring.application.name:pimienta-backend}")
  private String serviceName;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    AuditLogInterceptor interceptor = new AuditLogInterceptor(auditLogger, serviceName) {

      @Override
      protected String extractUserId(HttpServletRequest request) {
        // Priority 1: authenticated user from Spring Security.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
            && !"anonymousUser".equals(auth.getPrincipal())) {
          return auth.getName();
        }

        // Priority 2: fallback header (useful for testing/integration clients).
        String userIdHeader = request.getHeader("X-User-ID");
        if (userIdHeader != null && !userIdHeader.trim().isEmpty()) {
          return userIdHeader;
        }

        return "anonymous";
      }
    };

    registry.addInterceptor(interceptor)
        .addPathPatterns("/api/**")
        .excludePathPatterns(
            "/api/v2/health/**",
            "/actuator/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/error/**");
  }
}