---
type: "REST"
httpEndpoints:
  - id: "health"
    method: "GET"
    urlPath: "/api/v2/health"
    summary: "Service health check"
    description: "Public liveness endpoint used by monitoring and load balancers. Returns UP status and service name."
    tags:
      - "health"
    authenticated: false
    rateLimit: "exempt"
    responses:
      - status: 200
        description: "Service is up"
        example:
          status: "UP"
          service: "pimienta"
  - id: "auth-register"
    method: "POST"
    urlPath: "/api/v1/auth/register"
    summary: "Register new user"
    description: "Creates account in PENDING_APPROVAL state until an administrator approves. Used by company staff onboarding."
    tags:
      - "auth"
    authenticated: false
    rateLimit: "STRICT"
    requestBody:
      contentType: "application/json"
      schema:
        type: "object"
        required:
          - "email"
          - "password"
          - "fullName"
      example:
        email: "staff@pimienta.example"
        password: "SecurePass123!"
        fullName: "María García"
    responses:
      - status: 201
        description: "Registration accepted, pending approval"
        example:
          message: "Registration successful. Your account is pending approval by an administrator."
          status: "PENDING_APPROVAL"
      - status: 400
        description: "Validation error"
        example:
          error: "VALIDATION_ERROR"
  - id: "auth-login"
    method: "POST"
    urlPath: "/api/v1/auth/login"
    summary: "Login"
    description: "Issues JWT access and refresh tokens. Refresh token stored in Upstash Redis."
    tags:
      - "auth"
    authenticated: false
    rateLimit: "STRICT"
    requestBody:
      contentType: "application/json"
      schema:
        type: "object"
        required:
          - "email"
          - "password"
      example:
        email: "admin@pimienta.example"
        password: "SecurePass123!"
    responses:
      - status: 200
        description: "Tokens issued"
        example:
          accessToken: "eyJhbGciOiJIUzI1NiIs..."
          refreshToken: "dGhpcyBpcyBhIHJlZnJlc2g..."
          tokenType: "Bearer"
          expiresIn: 900
      - status: 401
        description: "Invalid credentials"
        example:
          error: "INVALID_CREDENTIALS"
  - id: "auth-refresh"
    method: "POST"
    urlPath: "/api/v1/auth/refresh"
    summary: "Refresh session"
    description: "Rotates access token using valid refresh token from Redis store."
    tags:
      - "auth"
    authenticated: false
    rateLimit: "AUTH_SESSION"
    requestBody:
      contentType: "application/json"
      schema:
        type: "object"
        required:
          - "refreshToken"
      example:
        refreshToken: "dGhpcyBpcyBhIHJlZnJlc2g..."
    responses:
      - status: 200
        description: "New tokens issued"
        example:
          accessToken: "eyJhbGciOiJIUzI1NiIs..."
          refreshToken: "bmV3IHJlZnJlc2ggdG9rZW4..."
          tokenType: "Bearer"
          expiresIn: 900
  - id: "auth-logout"
    method: "POST"
    urlPath: "/api/v1/auth/logout"
    summary: "Logout"
    description: "Invalidates refresh token in Redis. Optional body with refreshToken."
    tags:
      - "auth"
    authenticated: false
    rateLimit: "AUTH_SESSION"
    responses:
      - status: 204
        description: "Logged out"
        example: null
  - id: "users-me"
    method: "GET"
    urlPath: "/api/v1/users/me"
    summary: "Current user profile"
    description: "Returns authenticated staff member profile."
    tags:
      - "users"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "Profile"
        example:
          id: "550e8400-e29b-41d4-a716-446655440000"
          email: "staff@pimienta.example"
          fullName: "María García"
          roles:
            - "ROLE_MANAGER"
  - id: "users-management-list"
    method: "GET"
    urlPath: "/api/v1/users/management"
    summary: "List users (admin)"
    description: "Paginated user list for administrators."
    tags:
      - "users"
    authenticated: true
    rateLimit: "READ_HEAVY"
    parameters:
      - name: "page"
        in: "query"
        type: "integer"
        required: false
        description: "Zero-based page index"
        example: 0
      - name: "size"
        in: "query"
        type: "integer"
        required: false
        description: "Page size (1–100, default 20)"
        example: 20
    responses:
      - status: 200
        description: "Paged users"
        example:
          items: []
          metadata:
            page: 0
            size: 20
            totalElements: 0
            totalPages: 0
  - id: "users-approve"
    method: "POST"
    urlPath: "/api/v1/users/management/{id}/approve"
    summary: "Approve pending user"
    description: "Administrator approves a PENDING_APPROVAL account so staff can log in."
    tags:
      - "users"
    authenticated: true
    rateLimit: "SENSITIVE_OPERATIONS"
    parameters:
      - name: "id"
        in: "path"
        type: "string"
        required: true
        description: "User UUID"
        example: "550e8400-e29b-41d4-a716-446655440000"
    responses:
      - status: 200
        description: "User approved"
        example:
          id: "550e8400-e29b-41d4-a716-446655440000"
          status: "ACTIVE"
  - id: "employees-list"
    method: "GET"
    urlPath: "/api/v1/employees"
    summary: "Search employees"
    description: "Paginated employee search used by HR staff."
    tags:
      - "employees"
    authenticated: true
    rateLimit: "READ_HEAVY"
    parameters:
      - name: "page"
        in: "query"
        type: "integer"
        required: false
        description: "Page index"
        example: 0
      - name: "size"
        in: "query"
        type: "integer"
        required: false
        description: "Page size"
        example: 20
    responses:
      - status: 200
        description: "Paged employees"
        example:
          items: []
          metadata:
            page: 0
            size: 20
            totalElements: 42
            totalPages: 3
  - id: "employees-export"
    method: "GET"
    urlPath: "/api/v1/employees/export"
    summary: "Export employees XLSX"
    description: "Downloads spreadsheet of employee records for operations."
    tags:
      - "employees"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "XLSX file"
        example: "(binary application/vnd.openxmlformats-officedocument.spreadsheetml.sheet)"
  - id: "employees-attendance-start"
    method: "POST"
    urlPath: "/api/v1/employees/{id}/attendance/start-workday"
    summary: "Start workday"
    description: "Field staff clock-in with optional geo and photo evidence."
    tags:
      - "employees"
    authenticated: true
    rateLimit: "STANDARD"
    parameters:
      - name: "id"
        in: "path"
        type: "string"
        required: true
        description: "Employee UUID"
        example: "660e8400-e29b-41d4-a716-446655440001"
    responses:
      - status: 201
        description: "Attendance started"
        example:
          attendanceId: "770e8400-e29b-41d4-a716-446655440002"
          status: "IN_PROGRESS"
  - id: "contracts-list"
    method: "GET"
    urlPath: "/api/v1/contracts"
    summary: "List contracts"
    description: "Employment contracts with pagination."
    tags:
      - "contracts"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "Paged contracts"
        example:
          items: []
          metadata:
            page: 0
            size: 20
            totalElements: 10
            totalPages: 1
  - id: "contracts-renew"
    method: "POST"
    urlPath: "/api/v1/contracts/{id}/renew"
    summary: "Renew contract"
    description: "Renews an active employment contract."
    tags:
      - "contracts"
    authenticated: true
    rateLimit: "SENSITIVE_OPERATIONS"
    parameters:
      - name: "id"
        in: "path"
        type: "string"
        required: true
        description: "Contract UUID"
        example: "880e8400-e29b-41d4-a716-446655440003"
    responses:
      - status: 200
        description: "Contract renewed"
        example:
          id: "880e8400-e29b-41d4-a716-446655440003"
          status: "ACTIVE"
  - id: "opportunities-list"
    method: "GET"
    urlPath: "/api/v1/opportunities"
    summary: "List CRM opportunities"
    description: "Sales pipeline opportunities with filters and pagination."
    tags:
      - "crm"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "Paged opportunities"
        example:
          items: []
          metadata:
            page: 0
            size: 20
            totalElements: 5
            totalPages: 1
  - id: "opportunities-win"
    method: "POST"
    urlPath: "/api/v1/opportunities/{id}/win"
    summary: "Mark opportunity won"
    description: "Transitions opportunity to won state in CRM pipeline."
    tags:
      - "crm"
    authenticated: true
    rateLimit: "STANDARD"
    parameters:
      - name: "id"
        in: "path"
        type: "string"
        required: true
        description: "Opportunity UUID"
        example: "990e8400-e29b-41d4-a716-446655440004"
    responses:
      - status: 200
        description: "Opportunity won"
        example:
          id: "990e8400-e29b-41d4-a716-446655440004"
          status: "WON"
  - id: "tasks-list"
    method: "GET"
    urlPath: "/api/v1/tasks"
    summary: "List tasks"
    description: "Internal task management for operations teams."
    tags:
      - "tasks"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "Paged tasks"
        example:
          items: []
          metadata:
            page: 0
            size: 20
            totalElements: 15
            totalPages: 1
  - id: "headquarters-statistics"
    method: "GET"
    urlPath: "/api/v1/headquarters/statistics"
    summary: "Headquarters statistics"
    description: "Aggregate stats across branch locations."
    tags:
      - "headquarters"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "Statistics"
        example:
          totalHeadquarters: 3
          activeHeadquarters: 3
  - id: "inventory-transaction-purchase"
    method: "POST"
    urlPath: "/api/v1/inventory/transactions/purchase"
    summary: "Create purchase transaction"
    description: "Records inventory purchase; may require approval workflow."
    tags:
      - "inventory"
    authenticated: true
    rateLimit: "STANDARD"
    requestBody:
      contentType: "application/json"
      schema:
        type: "object"
      example:
        itemId: "aa0e8400-e29b-41d4-a716-446655440005"
        quantity: 100
        locationId: "bb0e8400-e29b-41d4-a716-446655440006"
    responses:
      - status: 201
        description: "Transaction created"
        example:
          id: "cc0e8400-e29b-41d4-a716-446655440007"
          type: "PURCHASE"
          status: "PENDING_APPROVAL"
  - id: "inventory-stock-low"
    method: "GET"
    urlPath: "/api/v1/inventory/stock/low-stock"
    summary: "Low stock items"
    description: "Alerts for items below threshold — used by warehouse staff."
    tags:
      - "inventory"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "Low stock list"
        example:
          items: []
  - id: "payroll-records"
    method: "GET"
    urlPath: "/api/v1/payroll/records"
    summary: "List payroll records"
    description: "Payroll records for finance operations."
    tags:
      - "payroll"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "Paged payroll records"
        example:
          items: []
          metadata:
            page: 0
            size: 20
            totalElements: 8
            totalPages: 1
  - id: "files-upload"
    method: "POST"
    urlPath: "/api/v1/files/management/upload"
    summary: "Upload file (admin)"
    description: "Uploads file asset to S3; returns metadata and presigned download capability."
    tags:
      - "files"
    authenticated: true
    rateLimit: "STANDARD"
    requestBody:
      contentType: "multipart/form-data"
      schema:
        type: "object"
      example:
        file: "(binary)"
    responses:
      - status: 201
        description: "File uploaded"
        example:
          id: "dd0e8400-e29b-41d4-a716-446655440008"
          fileName: "policy.pdf"
          contentType: "application/pdf"
  - id: "notifications-statistics"
    method: "GET"
    urlPath: "/api/v1/notifications/management/statistics"
    summary: "Notification statistics (admin)"
    description: "Admin dashboard metrics for sent notifications."
    tags:
      - "notifications"
    authenticated: true
    rateLimit: "READ_HEAVY"
    responses:
      - status: 200
        description: "Statistics"
        example:
          totalSent: 150
          failed: 2
---

# API Schema

> **Live production API** (`/api/v1`) serving Pimienta Alimentos company staff. Full interactive reference: `GET /swagger-ui` on the deployed host.

**Warning:** This document lists representative endpoints, not an exhaustive OpenAPI export. For the complete spec run the app and open `/v3/api-docs` or Swagger UI.

**Highlight:** All business routes require `Authorization: Bearer <access_token>` except `/api/v1/auth/**` and `/api/v2/health/**`.

**Placeholder:** Replace `{{PRODUCTION_HOST}}` in client configs with your EC2 public hostname or ALB DNS name.
