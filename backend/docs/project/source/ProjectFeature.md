---
features:
  - id: "auth-jwt"
    title: "JWT authentication & session management"
    description: "Register, login, refresh, and logout with BCrypt passwords. Refresh tokens persisted in Upstash Redis; access tokens expire in 15 minutes."
    icon: "lock"
    category: "authentication"
    status: "stable"
    highlights:
      - "POST /api/v1/auth/register — pending admin approval"
      - "POST /api/v1/auth/login — returns access + refresh tokens"
      - "POST /api/v1/auth/refresh — rotates session via Redis store"
      - "POST /api/v1/auth/logout — invalidates refresh token"
    techStack:
      - "Spring Security"
      - "jjwt 0.12.5"
      - "Upstash Redis"
    metrics:
      - label: "Access TTL"
        value: "15 min"
        trend: "stable"
      - label: "Refresh TTL"
        value: "7 days"
        trend: "stable"
  - id: "rbac"
    title: "Role-based access control"
    description: "ROLE_ADMIN and ROLE_MANAGER gate sensitive routes (files, notifications, user management) while staff access operational endpoints."
    icon: "shield"
    category: "security"
    status: "stable"
    highlights:
      - "SecurityConfig path rules + @EnableMethodSecurity"
      - "Admin-only file management and notification management"
      - "Manager + Admin access to file resources and notification logs"
  - id: "rate-limiting"
    title: "Redis token-bucket rate limiting"
    description: "Global per-IP protection on /api/** plus per-endpoint @RateLimit profiles (STRICT, AUTH_SESSION, READ_HEAVY, etc.)."
    icon: "gauge"
    category: "performance"
    status: "stable"
    highlights:
      - "GlobalRateLimitFilter with Lua script"
      - "X-RateLimit-Limit / X-RateLimit-Remaining headers"
      - "Disabled in integration tests"
    techStack:
      - "Redis"
      - "Spring Filter chain"
  - id: "employees-hr"
    title: "Employee & HR management"
    description: "Full employee lifecycle: CRUD, photos (S3), work schedules, attendance start/end workday, search and export."
    icon: "users"
    category: "api"
    status: "stable"
    highlights:
      - "Multipart employee registration with photo upload"
      - "Attendance tracking with geo/photo evidence"
      - "XLSX import/export for bulk HR updates"
      - "Hourly stale attendance sweep job"
    techStack:
      - "AWS S3"
      - "Thumbnailator"
      - "Apache POI"
  - id: "inventory"
    title: "Inventory & stock workflows"
    description: "Items, storage locations (tree), stock levels, transactions (purchase/sale/transfer/adjustment) with approve/complete workflow."
    icon: "package"
    category: "api"
    status: "stable"
    highlights:
      - "Low-stock and out-of-stock alerts"
      - "Location tree and block/unblock"
      - "Movement history by item or reference"
  - id: "payroll"
    title: "Payroll management"
    description: "Payroll periods, records, payments, debts, adjustments, and XLSX sync for finance operations."
    icon: "currency"
    category: "api"
    status: "stable"
    highlights:
      - "Record adjustments and payment tracking"
      - "Bulk XLSX export/import"
  - id: "crm"
    title: "CRM — opportunities & projects"
    description: "Sales pipeline (win/lose/reopen), linked tasks, projects with activate/hold/complete, and milestones."
    icon: "chart"
    category: "api"
    status: "stable"
    highlights:
      - "Opportunity pipeline state machine"
      - "Project milestones CRUD"
      - "XLSX bulk sync for opportunities"
  - id: "contracts"
    title: "Employment contracts"
    description: "Contract creation, renewal, extension, and lifecycle management tied to employees."
    icon: "document"
    category: "api"
    status: "stable"
    highlights:
      - "POST /api/v1/contracts/{id}/renew"
      - "POST /api/v1/contracts/{id}/extend"
  - id: "tasks"
    title: "Task management"
    description: "Tasks with checklist, assignment, status updates, and XLSX import/export for operations teams."
    icon: "checklist"
    category: "api"
    status: "stable"
    highlights:
      - "PATCH /api/v1/tasks/{id}/status"
      - "PATCH /api/v1/tasks/{id}/assign"
  - id: "headquarters"
    title: "Headquarters / branches"
    description: "Branch locations with statistics, import/export, and linkage to employees and inventory."
    icon: "building"
    category: "api"
    status: "stable"
    highlights:
      - "GET /api/v1/headquarters/statistics"
      - "XLSX import/export"
  - id: "files-s3"
    title: "File assets (S3)"
    description: "Admin and manager file uploads with presigned download URLs; image resize before storage."
    icon: "cloud-upload"
    category: "integration"
    status: "stable"
    highlights:
      - "Presigned S3 download URLs"
      - "Separate admin management vs manager resources APIs"
    techStack:
      - "AWS SDK S3 2.25"
  - id: "notifications"
    title: "Email notifications"
    description: "Domain-event driven notifications (e.g. pending account approval); admin search and statistics."
    icon: "mail"
    category: "messaging"
    status: "stable"
    highlights:
      - "AccountPendingApprovalEvent listener"
      - "Admin notification logs and statistics"
  - id: "pagination"
    title: "Standard pagination contract"
    description: "PageableRequest (page 0-based, size 1–100) and PagedResponse<T> with PageMetadata across list endpoints."
    icon: "list"
    category: "api"
    status: "stable"
    highlights:
      - "X-Total-Count exposed via CORS"
      - "Consistent items + metadata JSON shape"
    codeSnippet:
      language: "java"
      filename: "PagedResponse.java"
      code: |
        public record PagedResponse<T>(
            List<T> items,
            PageMetadata metadata) {

          public static <T> PagedResponse<T> of(Page<T> page) {
            return new PagedResponse<>(page.getContent(), PageMetadata.fromPage(page));
          }
        }
  - id: "openapi"
    title: "OpenAPI 3 + Swagger UI"
    description: "springdoc-openapi with Bearer JWT scheme; per-endpoint Doc* annotations for maintainable API docs."
    icon: "book"
    category: "api"
    status: "stable"
    highlights:
      - "GET /swagger-ui"
      - "GET /v3/api-docs"
  - id: "flyway"
    title: "Flyway database migrations"
    description: "12 versioned SQL migrations; schema pimienta; Hibernate ddl-auto validate in all environments."
    icon: "database"
    category: "database"
    status: "stable"
    highlights:
      - "V1 headquarters through V12 dummy data"
  - id: "integration-tests"
    title: "MockMvc integration tests"
    description: "14 integration test suites covering auth, employees, inventory, payroll, CRM, and more."
    icon: "test"
    category: "testing"
    status: "stable"
    highlights:
      - "H2 in-memory for tests"
      - "Rate limiting disabled in test profile"
---

# Project Features

> **Production features in daily use:** Every module above supports real company operations for Pimienta Alimentos staff — HR, warehouse, finance, sales, and administration.

**Warning:** Email/Twilio configuration for notifications may require production env vars not listed in `.env.example` — verify before external doc publish.

**Highlight:** Bulk XLSX import/export is a deliberate product choice so operations teams can sync data the way they already work in spreadsheets.
