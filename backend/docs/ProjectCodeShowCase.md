# Code Showcase

## Code Examples (`CodeExample[]`)

### Example 1 (`CodeExample`)

- **ID**: jwt-auth
- **Title**: JWT Authentication Implementation
- **Description**: Token generation and validation with jjwt library
- **Category**: Security
- **Duration** (optional): ""
- **Views** (optional): 
- **Tags** (optional):
  - JWT
  - Security
  - Authentication

#### Files (`CodeFile[]`)

- **Name**: JwtTokenService.java
- **Path**: src/main/java/io/github/alexistrejo11/pimienta/module/account/auth/infrastructure/security/JwtTokenService.java
- **Language**: java
- **Content**:
  ```java
  @Component
  public class JwtTokenService {
      @Value("${pimienta.security.jwt.secret}")
      private String secret;
      
      @Value("${pimienta.security.jwt.access-token-ttl-minutes}")
      private long accessTokenTtl;
      
      public String generateAccessToken(UserDetails user) {
          Map<String, Object> claims = new HashMap<>();
          return Jwts.builder()
              .claims(claims)
              .subject(user.getUsername())
              .issuedAt(new Date())
              .expiration(new Date(System.currentTimeMillis() + accessTokenTtl * 60 * 1000))
              .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
              .compact();
      }
      
      public boolean validateToken(String token) {
          try {
              Jwts.parser()
                  .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                  .build()
                  .parseSignedClaims(token);
              return true;
          } catch (JwtException e) {
              return false;
          }
      }
  }
  ```
- **Highlighted** (optional): `true`
- **Explanation** (optional): "JWT token generation with HS256 signing"

---

### Example 2 (`CodeExample`)

- **ID**: rate-limiting
- **Title**: Redis Token Bucket Rate Limiting
- **Description**: Distributed rate limiting using Redis with Lua scripts
- **Category**: Security
- **Files**: 

#### Files

- **Name**: RedisTokenBucketRateLimiter.java
- **Path**: src/main/java/io/github/alexistrejo11/pimienta/config/rate_limit/RedisTokenBucketRateLimiter.java
- **Language**: java
- **Content**:
  ```java
  @Component
  public class RedisTokenBucketRateLimiter {
      private final RedisTemplate<String, String> redisTemplate;
      private final String luaScript = """
          local key = KEYS[1]
          local capacity = tonumber(ARGV[1])
          local refillRate = tonumber(ARGV[2])
          local now = tonumber(ARGV[3])
          local tokens = tonumber(redis.call('get', key..':tokens') or capacity)
          local lastRefill = tonumber(redis.call('get', key..':last') or now)
          local elapsed = now - lastRefill
          tokens = math.min(capacity, tokens + (elapsed * refillRate))
          if tokens >= 1 then
              redis.call('set', key..':tokens', tokens - 1)
              redis.call('set', key..':last', now)
              return 1
          else
              redis.call('set', key..':tokens', tokens)
              redis.call('set', key..':last', now)
              return 0
          end
          """;
      
      public boolean tryAcquire(String key, int capacity, int refillRate) {
          DefaultRedisScript<Long> script = new DefaultRedisScript<>(luaScript, Long.class);
          Long result = redisTemplate.execute(script, 
              List.of(key), 
              capacity, refillRate, System.currentTimeMillis() / 1000);
          return result != null && result == 1L;
      }
  }
  ```

---

### Example 3 (`CodeExample`)

- **ID**: excel-import
- **Title**: Excel Bulk Import with Apache POI
- **Description**: Bulk import employees from Excel files with validation
- **Category**: Integration
- **Files**: 

#### Files

- **Name**: EmployeeBulkSyncUseCasesImpl.java
- **Path**: src/main/java/io/github/alexistrejo11/pimienta/module/employees/core/application/EmployeeBulkSyncUseCasesImpl.java
- **Language**: java
- **Content**:
  ```java
  @Component
  public class EmployeeBulkSyncUseCasesImpl implements EmployeeBulkSyncUseCases {
      private final EmployeeSpreadsheetParser parser;
      private final EmployeeRepository repository;
      
      @Override
      public SpreadsheetBulkImportResult importEmployees(InputStream inputStream, String filename) {
          List<EmployeeImportRow> rows = parser.parse(inputStream, filename);
          List<SpreadsheetBulkImportRowError> errors = new ArrayList<>();
          List<Employee> toSave = new ArrayList<>();
          
          for (int i = 0; i < rows.size(); i++) {
              try {
                  validateRow(rows.get(i), i + 2);
                  toSave.add(mapToEmployee(rows.get(i)));
              } catch (BusinessValidationException e) {
                  errors.add(new SpreadsheetBulkImportRowError(i + 2, e.getMessage()));
              }
          }
          
          repository.saveAll(toSave);
          return new SpreadsheetBulkImportResult(toSave.size(), errors);
      }
  }
  ```

---

### Example 4 (`CodeExample`)

- **ID**: hexagonal-architecture
- **Title**: Hexagonal Architecture - Ports & Adapters
- **Description**: Clean separation of domain logic from infrastructure
- **Category**: Architecture
- **Files**: 

#### Files

- **Name**: EmployeeUseCases.java
- **Path**: src/main/java/io/github/alexistrejo11/pimienta/module/employees/core/port/input/EmployeeUseCases.java
- **Language**: java
- **Content**:
  ```java
  public interface EmployeeUseCases {
      Employee getById(Long id);
      Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable);
      EmployeeStatistics statistics();
      Employee register(RegisterEmployeeParams params);
      Employee update(Long id, UpdateEmployeeParams params);
      void delete(Long id);
  }
  ```

- **Name**: EmployeeRepository.java
- **Path**: src/main/java/io/github/alexistrejo11/pimienta/module/employees/core/port/output/EmployeeRepository.java
- **Language**: java
- **Content**:
  ```java
  public interface EmployeeRepository {
      Optional<Employee> findById(Long id);
      Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable);
      Employee save(Employee employee);
      void delete(Long id);
  }
  ```