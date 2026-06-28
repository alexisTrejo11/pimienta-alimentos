# Architecture

## Inbound adapters (Web)

REST controllers, DTOs, Jakarta validation, and per-endpoint OpenAPI Doc* annotations.

### Components

- AuthController, UserController, EmployeeManagerController
- InventoryTransactionController, OpportunityController
- PayrollManagerController, TaskManagerController
- GlobalExceptionHandler, HealthController

### Responsibilities

- HTTP mapping and request/response serialization
- Rate-limit annotations (@RateLimit profiles)
- Role checks via Spring Security + @PreAuthorize where needed

### Technologies

- Spring Web MVC
- springdoc-openapi
- Jakarta Validation

## Application (use cases)

Orchestrates domain logic via input ports; commands and queries per bounded context.

### Components

- AuthUseCasesImpl, AttendanceUseCasesImpl
- InventoryTransactionManagementUseCasesImpl
- ContractManagementUseCasesImpl

### Responsibilities

- Transaction boundaries and domain event publication
- Mapping between commands and domain operations
- Coordination across output ports (repos, S3, email)

### Technologies

- Spring @Service
- Spring @Transactional

## Domain

Entities, value objects, domain exceptions, and business rules per module.

### Components

- Employee, Contract, Opportunity, InventoryTransaction
- BaseDomain<ID> with audit fields and soft delete

### Responsibilities

- Invariant enforcement and state transitions
- Rich domain models (not anemic DTOs)

### Technologies

- Plain Java domain classes

## Outbound adapters

JPA repositories, S3 storage, Redis token store, email senders.

### Components

- JPA entities and Spring Data repositories
- S3FileStorageAdapter, RedisRefreshTokenStore
- EmailNotificationSender

### Responsibilities

- Persistence mapping and Flyway-managed schema
- External service integration

### Technologies

- Spring Data JPA
- AWS SDK S3
- Spring Data Redis

## Cross-cutting config

Security, CORS, rate limiting, OpenAPI, scheduling, audit logging.

### Components

- SecurityConfig, JwtAuthenticationFilter
- GlobalRateLimitFilter, OpenApiConfig
- AttendanceStaleSweepJob

### Responsibilities

- JWT stateless auth, BCrypt passwords
- Redis token-bucket rate limits on /api/**
- Structured error responses

### Technologies

- Spring Security
- Spring Boot Actuator

## Design patterns

| Pattern | Category | Description |
| --- | --- | --- |
| ⬡ Hexagonal architecture | Structural | Each module splits core (domain + application + ports) from adapters (web, persistence, external). |
| 🔌 Ports and adapters | Structural | Input ports (use case interfaces) and output ports (repository, storage) keep domain free of framework code. |
| 📦 Repository pattern | Persistence | JPA-backed repositories implement output ports; aggregates extend BaseDomain. |
| 📣 Domain events | Integration | e.g. AccountPendingApprovalEvent triggers notification listeners asynchronously. |
| 🪣 Token bucket rate limiting | Security | Global Redis filter plus per-controller @RateLimit profiles (STRICT, READ_HEAVY, etc.). |

## Scalability strategies

- **Stateless API instances** — JWT + Redis refresh tokens allow horizontal scaling of EC2/Docker replicas behind a load balancer (placeholder: ALB not yet documented in repo).
- **Managed RDS and Upstash** — Database and cache are external to the container; API container is lightweight and replaceable.
- **Key-prefixed Redis** — PIMIENTA_REDIS_KEY_PREFIX avoids collisions on shared Upstash instances.
- **Pagination defaults** — PageableRequest caps page size at 100; PagedResponse standardizes list endpoints.

## Security strategies

- **JWT Bearer authentication** — 15-minute access tokens; 7-day refresh tokens stored in Redis; logout invalidates refresh.
- **Role-based access** — ROLE_ADMIN and ROLE_MANAGER enforced in SecurityConfig and method security.
- **Admin-approved registration** — New users start PENDING_APPROVAL until an administrator approves the account.
- **Rate limiting** — Per-IP global bucket on /api/** plus stricter profiles on auth and sensitive operations.
- **Production actuator lockdown** — application-prod.yaml exposes only health and info; metrics require ADMIN.

## Cache strategies

| Name | TTL | Coverage | Description |
| --- | --- | --- | --- |
| Refresh token store | 7 days | All authenticated sessions | Redis keys for JWT refresh sessions; invalidated on logout. |
| Global rate limit buckets | Rolling window | All /api/v1/* requests | Per-IP token bucket via Lua script (ratelimit-token-bucket.lua). |
| Endpoint rate limits | Profile-dependent | Auth, exports, imports, sensitive ops | @RateLimit profile-specific buckets per controller method. |

## Architecture highlights

### 🗄️ Flyway migrations

12 versioned SQL migrations (V1 headquarters through V12 dummy data); ddl-auto validate.

### 📖 OpenAPI per endpoint

Doc* annotation classes keep controllers thin and documentation consistent.

### 📊 XLSX bulk operations

Import/export for employees, CRM, tasks, headquarters, payroll via Apache POI.

### 📝 Audit logging

AuditLogInterceptor records operational actions for compliance traceability.

## Architecture diagram

### Legend

| Type | Label |
| --- | --- |
| client | Client |
| gateway | API |
| database | Database |
| queue | Cache |

### Nodes

| ID | Label | Type | Status |
| --- | --- | --- | --- |
| staff | Company staff (frontend) | client | healthy |
| ec2 | EC2 Docker API | gateway | healthy |
| rds | RDS PostgreSQL | database | healthy |
| redis | Upstash Redis | queue | healthy |
| s3 | AWS S3 | database | healthy |

### Connections

| From | To | Label | Protocol |
| --- | --- | --- | --- |
| staff | ec2 | HTTPS /api/v1 | REST + JWT |
| ec2 | rds | JDBC | PostgreSQL |
| ec2 | redis | rediss:// | Redis TLS |
| ec2 | s3 | SDK | HTTPS |

### Mermaid overview

```mermaid
flowchart LR
    staff([Company staff (frontend)])
    ec2{EC2 Docker API}
    rds[(RDS PostgreSQL)]
    redis[/Upstash Redis/]
    s3[(AWS S3)]
    staff -->|HTTPS /api/v1| ec2
    ec2 -->|JDBC| rds
    ec2 -->|rediss://| redis
    ec2 -->|SDK| s3
```

## Data flow

### Request flow

1. **Client request** — Staff app sends HTTP request with Authorization Bearer JWT to EC2-hosted API.
2. **Security filter** — JwtAuthenticationFilter validates token; GlobalRateLimitFilter checks Redis bucket.
3. **Controller → use case** — REST adapter maps DTO to command; application service executes domain logic.
4. **Persistence / external** — JPA writes to RDS; optional S3 upload or Redis session update.
5. **PagedResponse / JSON** — Standard pagination or entity DTO returned with OpenAPI-documented shape.

### Event flow

1. **Domain event** — e.g. user registers → AccountPendingApprovalEvent published.
2. **Listener** — Notification module listener picks up event.
3. **Email dispatch** — EmailNotificationSender notifies administrators.

## Technical decisions

### Hexagonal modules over layered monolith

**Problem:** Ten operational domains (HR, inventory, payroll, CRM) would tangle in a single package.

**Solution:** Bounded contexts under module/* with core + adapter split and shared kernel (BaseDomain, pagination).

**Outcome:** Clear ownership per domain; deployable monolith suitable for current team size and EC2 footprint.

#### Alternatives considered

- Single-layered Spring package by technical concern
- Microservices per domain

### Upstash Redis over in-container Redis

**Problem:** Production on EC2 needs durable cache/session store without co-locating Redis on the same VM.

**Solution:** REDIS_URL with rediss:// to Upstash; key prefix for multi-tenant safety.

**Outcome:** Managed TLS Redis for refresh tokens and rate limits with minimal ops overhead.

#### Alternatives considered

- AWS ElastiCache
- Redis sidecar in compose

### Flyway + validate ddl-auto

**Problem:** Schema drift between environments risks production data corruption.

**Solution:** Versioned migrations in db/migration; Hibernate validates entity mapping only.

**Outcome:** Reproducible schema across local Docker, CI, and RDS.

#### Alternatives considered

- Hibernate ddl-auto update
- Manual SQL scripts

### JWT stateless sessions

**Problem:** Staff clients need mobile-friendly auth without server-side HTTP sessions.

**Solution:** Short-lived access JWT + Redis-backed refresh tokens; stateless SecurityFilterChain.

**Outcome:** Scalable auth model aligned with SPA/frontend clients.

#### Alternatives considered

- Session cookies
- OAuth2 provider only

## Additional notes

# Project Architecture

> **Real-world system:** Architecture choices reflect production constraints for a company whose employees rely on this API daily.

**Warning:** Load balancer / ALB configuration is not in this repository — document separately if EC2 sits behind AWS ALB.

**Highlight:** Every business module follows the same hexagonal layout (`core/domain`, `core/application`, `core/port`, `adapter/inbound/web`, `adapter/outbound`), with slight naming variants (`infrastructure/adapter` vs `adapter`).

