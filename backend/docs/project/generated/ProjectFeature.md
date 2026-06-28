# Project Features

## JWT authentication & session management

Register, login, refresh, and logout with BCrypt passwords. Refresh tokens persisted in Upstash Redis; access tokens expire in 15 minutes.

| Property | Value |
| --- | --- |
| ID | auth-jwt |
| Category | authentication |
| Status | stable |
| Icon | lock |

### Highlights

- POST /api/v1/auth/register — pending admin approval
- POST /api/v1/auth/login — returns access + refresh tokens
- POST /api/v1/auth/refresh — rotates session via Redis store
- POST /api/v1/auth/logout — invalidates refresh token

### Tech stack

- Spring Security
- jjwt 0.12.5
- Upstash Redis

### Metrics

| Label | Value | Trend |
| --- | --- | --- |
| Access TTL | 15 min | stable |
| Refresh TTL | 7 days | stable |

## Role-based access control

ROLE_ADMIN and ROLE_MANAGER gate sensitive routes (files, notifications, user management) while staff access operational endpoints.

| Property | Value |
| --- | --- |
| ID | rbac |
| Category | security |
| Status | stable |
| Icon | shield |

### Highlights

- SecurityConfig path rules + @EnableMethodSecurity
- Admin-only file management and notification management
- Manager + Admin access to file resources and notification logs

## Redis token-bucket rate limiting

Global per-IP protection on /api/** plus per-endpoint @RateLimit profiles (STRICT, AUTH_SESSION, READ_HEAVY, etc.).

| Property | Value |
| --- | --- |
| ID | rate-limiting |
| Category | performance |
| Status | stable |
| Icon | gauge |

### Highlights

- GlobalRateLimitFilter with Lua script
- X-RateLimit-Limit / X-RateLimit-Remaining headers
- Disabled in integration tests

### Tech stack

- Redis
- Spring Filter chain

## Employee & HR management

Full employee lifecycle: CRUD, photos (S3), work schedules, attendance start/end workday, search and export.

| Property | Value |
| --- | --- |
| ID | employees-hr |
| Category | api |
| Status | stable |
| Icon | users |

### Highlights

- Multipart employee registration with photo upload
- Attendance tracking with geo/photo evidence
- XLSX import/export for bulk HR updates
- Hourly stale attendance sweep job

### Tech stack

- AWS S3
- Thumbnailator
- Apache POI

## Inventory & stock workflows

Items, storage locations (tree), stock levels, transactions (purchase/sale/transfer/adjustment) with approve/complete workflow.

| Property | Value |
| --- | --- |
| ID | inventory |
| Category | api |
| Status | stable |
| Icon | package |

### Highlights

- Low-stock and out-of-stock alerts
- Location tree and block/unblock
- Movement history by item or reference

## Payroll management

Payroll periods, records, payments, debts, adjustments, and XLSX sync for finance operations.

| Property | Value |
| --- | --- |
| ID | payroll |
| Category | api |
| Status | stable |
| Icon | currency |

### Highlights

- Record adjustments and payment tracking
- Bulk XLSX export/import

## CRM — opportunities & projects

Sales pipeline (win/lose/reopen), linked tasks, projects with activate/hold/complete, and milestones.

| Property | Value |
| --- | --- |
| ID | crm |
| Category | api |
| Status | stable |
| Icon | chart |

### Highlights

- Opportunity pipeline state machine
- Project milestones CRUD
- XLSX bulk sync for opportunities

## Employment contracts

Contract creation, renewal, extension, and lifecycle management tied to employees.

| Property | Value |
| --- | --- |
| ID | contracts |
| Category | api |
| Status | stable |
| Icon | document |

### Highlights

- POST /api/v1/contracts/{id}/renew
- POST /api/v1/contracts/{id}/extend

## Task management

Tasks with checklist, assignment, status updates, and XLSX import/export for operations teams.

| Property | Value |
| --- | --- |
| ID | tasks |
| Category | api |
| Status | stable |
| Icon | checklist |

### Highlights

- PATCH /api/v1/tasks/{id}/status
- PATCH /api/v1/tasks/{id}/assign

## Headquarters / branches

Branch locations with statistics, import/export, and linkage to employees and inventory.

| Property | Value |
| --- | --- |
| ID | headquarters |
| Category | api |
| Status | stable |
| Icon | building |

### Highlights

- GET /api/v1/headquarters/statistics
- XLSX import/export

## File assets (S3)

Admin and manager file uploads with presigned download URLs; image resize before storage.

| Property | Value |
| --- | --- |
| ID | files-s3 |
| Category | integration |
| Status | stable |
| Icon | cloud-upload |

### Highlights

- Presigned S3 download URLs
- Separate admin management vs manager resources APIs

### Tech stack

- AWS SDK S3 2.25

## Email notifications

Domain-event driven notifications (e.g. pending account approval); admin search and statistics.

| Property | Value |
| --- | --- |
| ID | notifications |
| Category | messaging |
| Status | stable |
| Icon | mail |

### Highlights

- AccountPendingApprovalEvent listener
- Admin notification logs and statistics

## Standard pagination contract

PageableRequest (page 0-based, size 1–100) and PagedResponse<T> with PageMetadata across list endpoints.

| Property | Value |
| --- | --- |
| ID | pagination |
| Category | api |
| Status | stable |
| Icon | list |

### Highlights

- X-Total-Count exposed via CORS
- Consistent items + metadata JSON shape

### Code snippet

_PagedResponse.java_

```java
public record PagedResponse<T>(
    List<T> items,
    PageMetadata metadata) {

  public static <T> PagedResponse<T> of(Page<T> page) {
    return new PagedResponse<>(page.getContent(), PageMetadata.fromPage(page));
  }
}
```

## OpenAPI 3 + Swagger UI

springdoc-openapi with Bearer JWT scheme; per-endpoint Doc* annotations for maintainable API docs.

| Property | Value |
| --- | --- |
| ID | openapi |
| Category | api |
| Status | stable |
| Icon | book |

### Highlights

- GET /swagger-ui
- GET /v3/api-docs

## Flyway database migrations

12 versioned SQL migrations; schema pimienta; Hibernate ddl-auto validate in all environments.

| Property | Value |
| --- | --- |
| ID | flyway |
| Category | database |
| Status | stable |
| Icon | database |

### Highlights

- V1 headquarters through V12 dummy data

## MockMvc integration tests

14 integration test suites covering auth, employees, inventory, payroll, CRM, and more.

| Property | Value |
| --- | --- |
| ID | integration-tests |
| Category | testing |
| Status | stable |
| Icon | test |

### Highlights

- H2 in-memory for tests
- Rate limiting disabled in test profile

## Additional notes

# Project Features

> **Production features in daily use:** Every module above supports real company operations for Pimienta Alimentos staff — HR, warehouse, finance, sales, and administration.

**Warning:** Email/Twilio configuration for notifications may require production env vars not listed in `.env.example` — verify before external doc publish.

**Highlight:** Bulk XLSX import/export is a deliberate product choice so operations teams can sync data the way they already work in spreadsheets.

