# Code Showcase

## Thin REST adapter with use-case port

AuthController delegates to AuthUseCases input port — no business logic in the web layer. OpenAPI and rate limits applied declaratively.

**Category:** Hexagonal / Web | **Tags:** auth, controller, rate-limit

### AuthController.java

**Path:** `src/main/java/.../auth/infrastructure/adapter/inbound/web/AuthController.java`

Driving adapter: maps HTTP DTOs to commands and invokes the application port.

```java
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
```

## Stateless JWT security filter chain

Public paths for auth, health, and Swagger; role rules for admin/manager routes; JWT filter before UsernamePasswordAuthenticationFilter.

**Category:** Security | **Tags:** jwt, spring-security

### SecurityConfig.java

**Path:** `src/main/java/.../config/security/SecurityConfig.java`

Central security policy for the production API used by all staff clients.

```java
http.csrf(AbstractHttpConfigurer::disable)
    .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/v1/auth/**").permitAll()
        .requestMatchers("/api/v2/health/**").permitAll()
        .requestMatchers("/api/v1/files/management/**").hasRole("ADMIN")
        .anyRequest().authenticated())
    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
```

## Shared aggregate base with audit fields

All domain entities extend BaseDomain for id, timestamps, soft delete (deletedAt), and optimistic locking (version).

**Category:** Domain | **Tags:** ddd, aggregate

### BaseDomain.java

**Path:** `src/main/java/.../shared/BaseDomain.java`

Repository-style thin aggregates share persistence metadata without JPA in the domain package.

```java
public abstract class BaseDomain<ID> {
  protected ID id;
  protected LocalDateTime createdAt;
  protected LocalDateTime updatedAt;
  protected LocalDateTime deletedAt;
  protected Long version;
}
```

## Infrastructure-wide Redis rate limiter

OncePerRequestFilter applies token-bucket limits per client IP on all /api/** paths except health and actuator.

**Category:** Performance | **Tags:** redis, rate-limit

### GlobalRateLimitFilter.java

**Path:** `src/main/java/.../config/rate_limit/GlobalRateLimitFilter.java`

Protects the production API from abuse while staff use the system daily.

```java
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
```

## Consistent pagination response

PagedResponse record wraps Spring Data Page into items + metadata for every list endpoint.

**Category:** API contract | **Tags:** pagination, openapi

### PagedResponse.java

**Path:** `src/main/java/.../shared/web/PagedResponse.java`

```java
public record PagedResponse<T>(
    List<T> items,
    PageMetadata metadata) {

  public static <T> PagedResponse<T> of(Page<T> page) {
    return new PagedResponse<>(page.getContent(), PageMetadata.fromPage(page));
  }
}
```

## Production multi-stage Dockerfile

Maven build on JDK 25, runtime on JRE 25 Alpine with non-root spring user — deployed on AWS EC2.

**Category:** Infrastructure | **Tags:** docker, aws, ec2

### Dockerfile

**Path:** `docker/Dockerfile`

Image built from backend/ context; SPRING_PROFILES_ACTIVE=docker in production.

```dockerfile
FROM maven:3.9-eclipse-temurin-25-alpine AS build
RUN mvn -q -B -DskipTests package

FROM eclipse-temurin:25-jre-alpine AS runtime
USER spring:spring
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS:-} -jar /app/app.jar"]
```

## Additional notes

# Code Showcase

> Curated excerpts from the **live production codebase** powering Pimienta Alimentos daily operations.

**Note:** Paths use `...` shorthand in showcase entries; full paths are under `src/main/java/io/github/alexistrejo11/pimienta/`.

**Highlight:** Controllers stay thin — business rules live in `core/application` and `core/domain`, matching the hexagonal convention documented in `.cursor/skills/`.

