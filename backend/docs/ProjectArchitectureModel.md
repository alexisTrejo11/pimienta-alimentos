# Architecture Model

## 1. Layers (`ArchitectureLayer[]`)

### Layer 1

- **Name**: Presentation Layer
- **Description**: API REST Controllers handling HTTP requests/responses
- **Components**:
  - REST Controllers (@RestController)
  - DTOs (Request/Response objects)
  - Web Mappers
  - OpenAPI Annotations
- **Color**: #4A90D9
- **Expanded** (optional): `true`
- **Responsibilities** (optional):
  - Handle HTTP requests
  - Validate input
  - Map to use cases
  - Return responses
- **Technologies** (optional):
  - Spring Web MVC
  - Spring Validation
  - OpenAPI 3 / Swagger

---

### Layer 2

- **Name**: Application Layer
- **Description**: Use Cases / Application Services implementing business logic
- **Components**:
  - UseCase implementations
  - Commands and Queries (CQRS)
  - DTOs for internal use
- **Color**: #50C878
- **Responsibilities**:
  - Execute business logic
  - Orchestrate domain operations
  - Handle transactions

---

### Layer 3

- **Name**: Domain Layer
- **Description**: Core business entities, value objects, enums
- **Components**:
  - Domain Entities
  - Value Objects
  - Domain Enums
  - Domain Exceptions
- **Color**: #FF6B6B
- **Responsibilities**:
  - Business rules
  - Domain logic
  - Entity state management

---

### Layer 4

- **Name**: Infrastructure Layer
- **Description**: External adapters and persistence
- **Components**:
  - JPA Repositories
  - Excel Import/Export
  - Redis integrations
  - Security services
- **Color**: #9B59B6
- **Responsibilities**:
  - Database persistence
  - External integrations
  - Authentication/Authorization

---

## 2. Design Patterns (`DesignPattern[]`)

For each pattern:

- **Title**: Hexagonal Architecture
- **Emoji**: 🔷
- **Description**: Ports and Adapters pattern separating domain from infrastructure
- **Category**: Architecture
- **Badge**: Core Pattern
- **GitHub Example URL**: ""

---

- **Title**: CQRS
- **Emoji**: ⚡
- **Description**: Command Query Responsibility Segregation for clear separation of read/write operations
- **Category**: Pattern
- **Badge**: Pattern

---

- **Title**: DTO Mapping
- **Emoji**: 🔄
- **Description**: MapStruct-style mappers between layers
- **Category**: Pattern
- **Badge**: Pattern

---

- **Title**: Token Bucket Rate Limiting
- **Emoji**: 🪣
- **Description**: Redis-backed algorithm for rate limiting
- **Category**: Performance
- **Badge**: Pattern

---

## 3. Scalability Strategies (`StrategyItem[]`)

- **Title**: Stateless API Design
- **Description**: All state stored in database, no session affinity. Enables horizontal scaling.

- **Title**: Redis Caching
- **Description**: Rate limiting tokens cached in Redis for distributed environments.

- **Title**: Database Connection Pooling
- **Description**: HikariCP for efficient connection management.

---

## 4. Security Strategies (`StrategyItem[]`)

- **Title**: JWT Tokens
- **Description**: Access tokens with 15min TTL, refresh tokens in Redis with 7 days TTL

- **Title**: Rate Limiting
- **Description**: Per-IP rate limiting with configurable profiles

- **Title**: CORS Configuration
- **Description**: Configurable allowed origins per environment

- **Title**: Password Validation
- **Description**: Custom password strength validator ensuring secure passwords

---

## 5. Cache Strategies (`CacheStrategy[]`)

- **Name**: Rate Limit Tokens
- **Description**: Token bucket state in Redis
- **TTL**: Dynamic per profile
- **Coverage**: All /api/** endpoints

- **Name**: Refresh Tokens
- **Description**: Redis-backed refresh token storage
- **TTL**: 7 days
- **Coverage**: Authentication

---

## 6. Architecture Features (`ArchitectureFeature[]`)

- **Title**: OpenAPI Documentation
- **Emoji**: 📚
- **Description**: Auto-generated API docs with Swagger UI at /swagger-ui

- **Title**: Excel Bulk Operations
- **Emoji**: 📊
- **Description**: Apache POI integration for bulk import/export

- **Title**: Distributed Rate Limiting
- **Emoji**: 🚦
- **Description**: Redis-based token bucket supporting multiple instances

- **Title**: Trace ID Logging
- **Emoji**: 🆔
- **Description**: Request tracking with X-Trace-Id header

---

## 7. Architecture Diagram (`ArchitectureDiagramModel`)

### Legend (`LegendItem[]`)

- **Type**: client
- **Label**: Frontend/SPA
- **Color**: #4A90D9
- **Icon**: 🌐

- **Type**: gateway
- **Label**: Load Balancer / API Gateway
- **Color**: #F39C12
- **Icon**: 🚪

- **Type**: service
- **Label**: Pimienta API Service
- **Color**: #50C878
- **Icon**: ⚙️

- **Type**: database
- **Label**: PostgreSQL
- **Color**: #336791
- **Icon**: 🐘

- **Type**: cache
- **Label**: Redis
- **Color**: #DC382D
- **Icon**: 🔴

### Nodes (`DiagramNode[]`)

For each node:

- **ID**: client
- **Label**: Frontend (Angular/React/SPA)
- **Type**: `client`
- **x**: 100
- **y**: 200
- **Connections** (optional): 
- **Status** (optional): `healthy`
- **Traffic** (optional): 

- **ID**: api
- **Label**: Pimienta API
- **Type**: `service`
- **x**: 300
- **y**: 200
- **Status**: `healthy`

- **ID**: postgres
- **Label**: PostgreSQL
- **Type**: `database`
- **x**: 500
- **y**: 100
- **Status**: `healthy`

- **ID**: redis
- **Label**: Redis
- **Type**: `cache`
- **x**: 500
- **y**: 300
- **Status**: `healthy`

### Connections (`DiagramConnection[]`)

- **ID**: c1
- **From**: client
- **To**: api
- **Label** (optional): "HTTP/HTTPS"
- **Protocol** (optional): "REST"
- **Is Active** (optional): `true`

- **ID**: c2
- **From**: api
- **To**: postgres
- **Label**: "JDBC"
- **Protocol**: "PostgreSQL"
- **Is Active**: `true`

- **ID**: c3
- **From**: api
- **To**: redis
- **Label**: "Redis Protocol"
- **Protocol**: "TCP"
- **Is Active**: `true`

---

## 8. Data Flow (`DataFlowModel`)

### Request Flow (`FlowStep[]`)

For each step:

- **Number**: 1
- **Title**: HTTP Request
- **Description**: Client sends request with JWT and Trace ID
- **Icon**: 📥

- **Number**: 2
- **Title**: Rate Limiting Check
- **Description**: Redis token bucket validation
- **Icon**: 🚦

- **Number**: 3
- **Title**: Authentication
- **Description**: JWT validation and context setup
- **Icon**: 🔐

- **Number**: 4
- **Title**: Controller Processing
- **Description**: Handle request, validate, map to use case
- **Icon**: ⚙️

- **Number**: 5
- **Title**: Use Case Execution
- **Description**: Business logic and domain operations
- **Icon**: 💼

- **Number**: 6
- **Title**: Persistence
- **Description**: JPA save/update to PostgreSQL
- **Icon**: 💾

- **Number**: 7
- **Title**: Response
- **Description**: Return DTO to client
- **Icon**: 📤

### Event Flow (`FlowStep[]`)

For each step:

- **Number**: 1
- **Title**: Bulk Import
- **Description**: Excel file uploaded
- **Icon**: 📊

- **Number**: 2
- **Title**: Parse & Validate
- **Description**: Apache POI parses, validation per row
- **Icon**: ✅

- **Number**: 3
- **Title**: Persistence
- **Description**: Batch save to database
- **Icon**: 💾

- **Number**: 4
- **Title**: Result
- **Description**: Return success/errors summary
- **Icon**: 📋

---

## 9. Tech Decisions (`TechDecisionsModel`)

For each decision (`TechDecisionModel`):

- **Title**: Spring Boot 4.0 + Java 25
- **Problem**: Need modern, high-performance framework
- **Solution**: Spring Boot 4.0.5 with Java 25 for latest features
- **Alternatives**:
  - Spring Boot 3.x
  - Quarkus
  - Micronaut
- **Outcome**: Selected for team familiarity and ecosystem
- **Icon**: 🌱

- **Title**: PostgreSQL over MySQL
- **Problem**: Need robust relational database
- **Solution**: PostgreSQL for JSON support, full-text search, better concurrency
- **Alternatives**:
  - MySQL
  - MariaDB
- **Outcome**: Better feature set and performance

- **Title**: Redis for Rate Limiting
- **Problem**: Distributed rate limiting across multiple instances
- **Solution**: Redis with Lua scripts for atomic token bucket
- **Alternatives**:
  - In-memory (not distributed)
  - Database-based
- **Outcome**: Atomic, fast, distributed

- **Title**: JWT + Redis Refresh Tokens
- **Problem**: Secure stateless authentication
- **Solution**: Short-lived access tokens + Redis-backed refresh tokens
- **Alternatives**:
  - Session-based
  - OAuth2 provider
- **Outcome**: Stateless API suitable for SPA