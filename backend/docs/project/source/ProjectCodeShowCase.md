---
codeExamples:
  - id: "hexagonal-auth-controller"
    title: "Thin REST adapter with use-case port"
    description: "AuthController delegates to AuthUseCases input port — no business logic in the web layer. OpenAPI and rate limits applied declaratively."
    category: "Hexagonal / Web"
    tags:
      - "auth"
      - "controller"
      - "rate-limit"
    files:
      - name: "AuthController.java"
        path: "src/main/java/.../auth/infrastructure/adapter/inbound/web/AuthController.java"
        language: "java"
        highlighted: true
        explanation: "Driving adapter: maps HTTP DTOs to commands and invokes the application port."
        content: |
          @RestController
          @RequestMapping("/api/v1/auth")
          public class AuthController {

            private final AuthUseCases authUseCases;

            @PostMapping("/login")
            @RateLimit(profile = RateLimitProfile.STRICT)
            public TokenResponse login(@Valid @RequestBody LoginRequest request) {
              LoginCommand command = AuthWebMapper.toLoginCommand(request);
              IssuedTokens issued = authUseCases.login(command);
              return AuthWebMapper.toResponse(issued);
            }
          }
  - id: "security-config"
    title: "Stateless JWT security filter chain"
    description: "Public paths for auth, health, and Swagger; role rules for admin/manager routes; JWT filter before UsernamePasswordAuthenticationFilter."
    category: "Security"
    tags:
      - "jwt"
      - "spring-security"
    files:
      - name: "SecurityConfig.java"
        path: "src/main/java/.../config/security/SecurityConfig.java"
        language: "java"
        highlighted: true
        explanation: "Central security policy for the production API used by all staff clients."
        content: |
          http.csrf(AbstractHttpConfigurer::disable)
              .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/v1/auth/**").permitAll()
                  .requestMatchers("/api/v2/health/**").permitAll()
                  .requestMatchers("/api/v1/files/management/**").hasRole("ADMIN")
                  .anyRequest().authenticated())
              .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  - id: "base-domain"
    title: "Shared aggregate base with audit fields"
    description: "All domain entities extend BaseDomain for id, timestamps, soft delete (deletedAt), and optimistic locking (version)."
    category: "Domain"
    tags:
      - "ddd"
      - "aggregate"
    files:
      - name: "BaseDomain.java"
        path: "src/main/java/.../shared/BaseDomain.java"
        language: "java"
        explanation: "Repository-style thin aggregates share persistence metadata without JPA in the domain package."
        content: |
          public abstract class BaseDomain<ID> {
            protected ID id;
            protected LocalDateTime createdAt;
            protected LocalDateTime updatedAt;
            protected LocalDateTime deletedAt;
            protected Long version;
          }
  - id: "global-rate-limit"
    title: "Infrastructure-wide Redis rate limiter"
    description: "OncePerRequestFilter applies token-bucket limits per client IP on all /api/** paths except health and actuator."
    category: "Performance"
    tags:
      - "redis"
      - "rate-limit"
    files:
      - name: "GlobalRateLimitFilter.java"
        path: "src/main/java/.../config/rate_limit/GlobalRateLimitFilter.java"
        language: "java"
        highlighted: true
        explanation: "Protects the production API from abuse while staff use the system daily."
        content: |
          protected boolean shouldNotFilter(HttpServletRequest request) {
            String path = request.getRequestURI();
            return !path.startsWith("/api/")
                || path.startsWith("/api/v2/health")
                || path.startsWith("/actuator");
          }

          protected void doFilterInternal(...) {
            String key = "ratelimit:global:v1:ip:" + ClientIpResolver.resolve(request);
            RateLimitDecision d = rateLimiter.tryConsume(key, capacity, refillPerSecond);
            if (!d.allowed()) {
              RateLimitResponseWriter.writeTooManyRequests(response, ...);
              return;
            }
            filterChain.doFilter(request, response);
          }
  - id: "paged-response"
    title: "Consistent pagination response"
    description: "PagedResponse record wraps Spring Data Page into items + metadata for every list endpoint."
    category: "API contract"
    tags:
      - "pagination"
      - "openapi"
    files:
      - name: "PagedResponse.java"
        path: "src/main/java/.../shared/web/PagedResponse.java"
        language: "java"
        content: |
          public record PagedResponse<T>(
              List<T> items,
              PageMetadata metadata) {

            public static <T> PagedResponse<T> of(Page<T> page) {
              return new PagedResponse<>(page.getContent(), PageMetadata.fromPage(page));
            }
          }
  - id: "docker-production"
    title: "Production multi-stage Dockerfile"
    description: "Maven build on JDK 25, runtime on JRE 25 Alpine with non-root spring user — deployed on AWS EC2."
    category: "Infrastructure"
    tags:
      - "docker"
      - "aws"
      - "ec2"
    files:
      - name: "Dockerfile"
        path: "docker/Dockerfile"
        language: "dockerfile"
        highlighted: true
        explanation: "Image built from backend/ context; SPRING_PROFILES_ACTIVE=docker in production."
        content: |
          FROM maven:3.9-eclipse-temurin-25-alpine AS build
          RUN mvn -q -B -DskipTests package

          FROM eclipse-temurin:25-jre-alpine AS runtime
          USER spring:spring
          EXPOSE 8080
          ENV SPRING_PROFILES_ACTIVE=docker
          ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS:-} -jar /app/app.jar"]
---

# Code Showcase

> Curated excerpts from the **live production codebase** powering Pimienta Alimentos daily operations.

**Note:** Paths use `...` shorthand in showcase entries; full paths are under `src/main/java/io/github/alexistrejo11/pimienta/`.

**Highlight:** Controllers stay thin — business rules live in `core/application` and `core/domain`, matching the hexagonal convention documented in `.cursor/skills/`.
