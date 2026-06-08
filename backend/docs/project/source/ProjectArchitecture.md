---
layers:
  - name: "Inbound adapters (Web)"
    description: "REST controllers, DTOs, Jakarta validation, and per-endpoint OpenAPI Doc* annotations."
    components:
      - "AuthController, UserController, EmployeeManagerController"
      - "InventoryTransactionController, OpportunityController"
      - "PayrollManagerController, TaskManagerController"
      - "GlobalExceptionHandler, HealthController"
    color: "#3B82F6"
    responsibilities:
      - "HTTP mapping and request/response serialization"
      - "Rate-limit annotations (@RateLimit profiles)"
      - "Role checks via Spring Security + @PreAuthorize where needed"
    technologies:
      - "Spring Web MVC"
      - "springdoc-openapi"
      - "Jakarta Validation"
  - name: "Application (use cases)"
    description: "Orchestrates domain logic via input ports; commands and queries per bounded context."
    components:
      - "AuthUseCasesImpl, AttendanceUseCasesImpl"
      - "InventoryTransactionManagementUseCasesImpl"
      - "ContractManagementUseCasesImpl"
    color: "#10B981"
    responsibilities:
      - "Transaction boundaries and domain event publication"
      - "Mapping between commands and domain operations"
      - "Coordination across output ports (repos, S3, email)"
    technologies:
      - "Spring @Service"
      - "Spring @Transactional"
  - name: "Domain"
    description: "Entities, value objects, domain exceptions, and business rules per module."
    components:
      - "Employee, Contract, Opportunity, InventoryTransaction"
      - "BaseDomain<ID> with audit fields and soft delete"
    color: "#F59E0B"
    responsibilities:
      - "Invariant enforcement and state transitions"
      - "Rich domain models (not anemic DTOs)"
    technologies:
      - "Plain Java domain classes"
  - name: "Outbound adapters"
    description: "JPA repositories, S3 storage, Redis token store, email senders."
    components:
      - "JPA entities and Spring Data repositories"
      - "S3FileStorageAdapter, RedisRefreshTokenStore"
      - "EmailNotificationSender"
    color: "#8B5CF6"
    responsibilities:
      - "Persistence mapping and Flyway-managed schema"
      - "External service integration"
    technologies:
      - "Spring Data JPA"
      - "AWS SDK S3"
      - "Spring Data Redis"
  - name: "Cross-cutting config"
    description: "Security, CORS, rate limiting, OpenAPI, scheduling, audit logging."
    components:
      - "SecurityConfig, JwtAuthenticationFilter"
      - "GlobalRateLimitFilter, OpenApiConfig"
      - "AttendanceStaleSweepJob"
    color: "#6B7280"
    responsibilities:
      - "JWT stateless auth, BCrypt passwords"
      - "Redis token-bucket rate limits on /api/**"
      - "Structured error responses"
    technologies:
      - "Spring Security"
      - "Spring Boot Actuator"
designPatterns:
  - title: "Hexagonal architecture"
    emoji: "⬡"
    description: "Each module splits core (domain + application + ports) from adapters (web, persistence, external)."
    category: "Structural"
    badge: "Per module"
  - title: "Ports and adapters"
    emoji: "🔌"
    description: "Input ports (use case interfaces) and output ports (repository, storage) keep domain free of framework code."
    category: "Structural"
    badge: "Core pattern"
  - title: "Repository pattern"
    emoji: "📦"
    description: "JPA-backed repositories implement output ports; aggregates extend BaseDomain."
    category: "Persistence"
    badge: "JPA"
  - title: "Domain events"
    emoji: "📣"
    description: "e.g. AccountPendingApprovalEvent triggers notification listeners asynchronously."
    category: "Integration"
    badge: "Events"
  - title: "Token bucket rate limiting"
    emoji: "🪣"
    description: "Global Redis filter plus per-controller @RateLimit profiles (STRICT, READ_HEAVY, etc.)."
    category: "Security"
    badge: "Redis"
scalabilityStrategies:
  - title: "Stateless API instances"
    description: "JWT + Redis refresh tokens allow horizontal scaling of EC2/Docker replicas behind a load balancer (placeholder: ALB not yet documented in repo)."
  - title: "Managed RDS and Upstash"
    description: "Database and cache are external to the container; API container is lightweight and replaceable."
  - title: "Key-prefixed Redis"
    description: "PIMIENTA_REDIS_KEY_PREFIX avoids collisions on shared Upstash instances."
  - title: "Pagination defaults"
    description: "PageableRequest caps page size at 100; PagedResponse standardizes list endpoints."
securityStrategies:
  - title: "JWT Bearer authentication"
    description: "15-minute access tokens; 7-day refresh tokens stored in Redis; logout invalidates refresh."
  - title: "Role-based access"
    description: "ROLE_ADMIN and ROLE_MANAGER enforced in SecurityConfig and method security."
  - title: "Admin-approved registration"
    description: "New users start PENDING_APPROVAL until an administrator approves the account."
  - title: "Rate limiting"
    description: "Per-IP global bucket on /api/** plus stricter profiles on auth and sensitive operations."
  - title: "Production actuator lockdown"
    description: "application-prod.yaml exposes only health and info; metrics require ADMIN."
cacheStrategies:
  - name: "Refresh token store"
    description: "Redis keys for JWT refresh sessions; invalidated on logout."
    ttl: "7 days"
    coverage: "All authenticated sessions"
  - name: "Global rate limit buckets"
    description: "Per-IP token bucket via Lua script (ratelimit-token-bucket.lua)."
    ttl: "Rolling window"
    coverage: "All /api/v1/* requests"
  - name: "Endpoint rate limits"
    description: "@RateLimit profile-specific buckets per controller method."
    ttl: "Profile-dependent"
    coverage: "Auth, exports, imports, sensitive ops"
architectureFeatures:
  - title: "Flyway migrations"
    emoji: "🗄️"
    description: "12 versioned SQL migrations (V1 headquarters through V12 dummy data); ddl-auto validate."
  - title: "OpenAPI per endpoint"
    emoji: "📖"
    description: "Doc* annotation classes keep controllers thin and documentation consistent."
  - title: "XLSX bulk operations"
    emoji: "📊"
    description: "Import/export for employees, CRM, tasks, headquarters, payroll via Apache POI."
  - title: "Audit logging"
    emoji: "📝"
    description: "AuditLogInterceptor records operational actions for compliance traceability."
architectureDiagram:
  legendItems:
    - type: "client"
      label: "Client"
      color: "#3B82F6"
      icon: "browser"
    - type: "gateway"
      label: "API"
      color: "#10B981"
      icon: "server"
    - type: "database"
      label: "Database"
      color: "#8B5CF6"
      icon: "database"
    - type: "queue"
      label: "Cache"
      color: "#EF4444"
      icon: "redis"
  nodes:
    - id: "staff"
      label: "Company staff (frontend)"
      type: "client"
      x: 0
      y: 0
      status: "healthy"
    - id: "ec2"
      label: "EC2 Docker API"
      type: "gateway"
      x: 200
      y: 0
      status: "healthy"
    - id: "rds"
      label: "RDS PostgreSQL"
      type: "database"
      x: 400
      y: -50
      status: "healthy"
    - id: "redis"
      label: "Upstash Redis"
      type: "queue"
      x: 400
      y: 50
      status: "healthy"
    - id: "s3"
      label: "AWS S3"
      type: "database"
      x: 400
      y: 150
      status: "healthy"
  connections:
    - id: "c1"
      from: "staff"
      to: "ec2"
      label: "HTTPS /api/v1"
      protocol: "REST + JWT"
      isActive: true
    - id: "c2"
      from: "ec2"
      to: "rds"
      label: "JDBC"
      protocol: "PostgreSQL"
      isActive: true
    - id: "c3"
      from: "ec2"
      to: "redis"
      label: "rediss://"
      protocol: "Redis TLS"
      isActive: true
    - id: "c4"
      from: "ec2"
      to: "s3"
      label: "SDK"
      protocol: "HTTPS"
      isActive: true
dataFlow:
  requestFlow:
    - number: 1
      title: "Client request"
      description: "Staff app sends HTTP request with Authorization Bearer JWT to EC2-hosted API."
      icon: "arrow-right"
    - number: 2
      title: "Security filter"
      description: "JwtAuthenticationFilter validates token; GlobalRateLimitFilter checks Redis bucket."
      icon: "shield"
    - number: 3
      title: "Controller → use case"
      description: "REST adapter maps DTO to command; application service executes domain logic."
      icon: "layers"
    - number: 4
      title: "Persistence / external"
      description: "JPA writes to RDS; optional S3 upload or Redis session update."
      icon: "database"
    - number: 5
      title: "PagedResponse / JSON"
      description: "Standard pagination or entity DTO returned with OpenAPI-documented shape."
      icon: "code"
  eventFlow:
    - number: 1
      title: "Domain event"
      description: "e.g. user registers → AccountPendingApprovalEvent published."
      icon: "bell"
    - number: 2
      title: "Listener"
      description: "Notification module listener picks up event."
      icon: "ear"
    - number: 3
      title: "Email dispatch"
      description: "EmailNotificationSender notifies administrators."
      icon: "mail"
techDecisions:
  decisions:
    - title: "Hexagonal modules over layered monolith"
      problem: "Ten operational domains (HR, inventory, payroll, CRM) would tangle in a single package."
      solution: "Bounded contexts under module/* with core + adapter split and shared kernel (BaseDomain, pagination)."
      alternatives:
        - "Single-layered Spring package by technical concern"
        - "Microservices per domain"
      outcome: "Clear ownership per domain; deployable monolith suitable for current team size and EC2 footprint."
      icon: "architecture"
    - title: "Upstash Redis over in-container Redis"
      problem: "Production on EC2 needs durable cache/session store without co-locating Redis on the same VM."
      solution: "REDIS_URL with rediss:// to Upstash; key prefix for multi-tenant safety."
      alternatives:
        - "AWS ElastiCache"
        - "Redis sidecar in compose"
      outcome: "Managed TLS Redis for refresh tokens and rate limits with minimal ops overhead."
      icon: "redis"
    - title: "Flyway + validate ddl-auto"
      problem: "Schema drift between environments risks production data corruption."
      solution: "Versioned migrations in db/migration; Hibernate validates entity mapping only."
      alternatives:
        - "Hibernate ddl-auto update"
        - "Manual SQL scripts"
      outcome: "Reproducible schema across local Docker, CI, and RDS."
      icon: "database"
    - title: "JWT stateless sessions"
      problem: "Staff clients need mobile-friendly auth without server-side HTTP sessions."
      solution: "Short-lived access JWT + Redis-backed refresh tokens; stateless SecurityFilterChain."
      alternatives:
        - "Session cookies"
        - "OAuth2 provider only"
      outcome: "Scalable auth model aligned with SPA/frontend clients."
      icon: "lock"
---

# Project Architecture

> **Real-world system:** Architecture choices reflect production constraints for a company whose employees rely on this API daily.

**Warning:** Load balancer / ALB configuration is not in this repository — document separately if EC2 sits behind AWS ALB.

**Highlight:** Every business module follows the same hexagonal layout (`core/domain`, `core/application`, `core/port`, `adapter/inbound/web`, `adapter/outbound`), with slight naming variants (`infrastructure/adapter` vs `adapter`).
