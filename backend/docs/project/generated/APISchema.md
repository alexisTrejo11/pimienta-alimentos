# API Schema

**API type:** REST

## Auth

### `POST` /api/v1/auth/register

**Register new user**

Creates account in PENDING_APPROVAL state until an administrator approves. Used by company staff onboarding.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | STRICT |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "type": "object",
  "required": [
    "email",
    "password",
    "fullName"
  ]
}
```

**Example:**

```json
{
  "email": "staff@pimienta.example",
  "password": "SecurePass123!",
  "fullName": "María García"
}
```

#### Responses

- **201** — Registration accepted, pending approval

```json
{
  "message": "Registration successful. Your account is pending approval by an administrator.",
  "status": "PENDING_APPROVAL"
}
```

- **400** — Validation error

```json
{
  "error": "VALIDATION_ERROR"
}
```

---

### `POST` /api/v1/auth/login

**Login**

Issues JWT access and refresh tokens. Refresh token stored in Upstash Redis.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | STRICT |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "type": "object",
  "required": [
    "email",
    "password"
  ]
}
```

**Example:**

```json
{
  "email": "admin@pimienta.example",
  "password": "SecurePass123!"
}
```

#### Responses

- **200** — Tokens issued

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "dGhpcyBpcyBhIHJlZnJlc2g...",
  "tokenType": "Bearer",
  "expiresIn": 900
}
```

- **401** — Invalid credentials

```json
{
  "error": "INVALID_CREDENTIALS"
}
```

---

### `POST` /api/v1/auth/refresh

**Refresh session**

Rotates access token using valid refresh token from Redis store.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | AUTH_SESSION |
| **Tags** | auth |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "type": "object",
  "required": [
    "refreshToken"
  ]
}
```

**Example:**

```json
{
  "refreshToken": "dGhpcyBpcyBhIHJlZnJlc2g..."
}
```

#### Responses

- **200** — New tokens issued

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "bmV3IHJlZnJlc2ggdG9rZW4...",
  "tokenType": "Bearer",
  "expiresIn": 900
}
```

---

### `POST` /api/v1/auth/logout

**Logout**

Invalidates refresh token in Redis. Optional body with refreshToken.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | AUTH_SESSION |
| **Tags** | auth |

#### Responses

- **204** — Logged out
---

## Contracts

### `GET` /api/v1/contracts

**List contracts**

Employment contracts with pagination.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | contracts |

#### Responses

- **200** — Paged contracts

```json
{
  "items": [],
  "metadata": {
    "page": 0,
    "size": 20,
    "totalElements": 10,
    "totalPages": 1
  }
}
```

---

### `POST` /api/v1/contracts/{id}/renew

**Renew contract**

Renews an active employment contract.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | SENSITIVE_OPERATIONS |
| **Tags** | contracts |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| id | path | string | Yes | Contract UUID |

#### Responses

- **200** — Contract renewed

```json
{
  "id": "880e8400-e29b-41d4-a716-446655440003",
  "status": "ACTIVE"
}
```

---

## Crm

### `GET` /api/v1/opportunities

**List CRM opportunities**

Sales pipeline opportunities with filters and pagination.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | crm |

#### Responses

- **200** — Paged opportunities

```json
{
  "items": [],
  "metadata": {
    "page": 0,
    "size": 20,
    "totalElements": 5,
    "totalPages": 1
  }
}
```

---

### `POST` /api/v1/opportunities/{id}/win

**Mark opportunity won**

Transitions opportunity to won state in CRM pipeline.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | STANDARD |
| **Tags** | crm |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| id | path | string | Yes | Opportunity UUID |

#### Responses

- **200** — Opportunity won

```json
{
  "id": "990e8400-e29b-41d4-a716-446655440004",
  "status": "WON"
}
```

---

## Employees

### `GET` /api/v1/employees

**Search employees**

Paginated employee search used by HR staff.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | employees |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| page | query | integer | No | Page index |
| size | query | integer | No | Page size |

#### Responses

- **200** — Paged employees

```json
{
  "items": [],
  "metadata": {
    "page": 0,
    "size": 20,
    "totalElements": 42,
    "totalPages": 3
  }
}
```

---

### `GET` /api/v1/employees/export

**Export employees XLSX**

Downloads spreadsheet of employee records for operations.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | employees |

#### Responses

- **200** — XLSX file

```json
"(binary application/vnd.openxmlformats-officedocument.spreadsheetml.sheet)"
```

---

### `POST` /api/v1/employees/{id}/attendance/start-workday

**Start workday**

Field staff clock-in with optional geo and photo evidence.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | STANDARD |
| **Tags** | employees |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| id | path | string | Yes | Employee UUID |

#### Responses

- **201** — Attendance started

```json
{
  "attendanceId": "770e8400-e29b-41d4-a716-446655440002",
  "status": "IN_PROGRESS"
}
```

---

## Files

### `POST` /api/v1/files/management/upload

**Upload file (admin)**

Uploads file asset to S3; returns metadata and presigned download capability.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | STANDARD |
| **Tags** | files |

#### Request body

**Content-Type:** `multipart/form-data`

**Schema (summary):**

```json
{
  "type": "object"
}
```

**Example:**

```json
{
  "file": "(binary)"
}
```

#### Responses

- **201** — File uploaded

```json
{
  "id": "dd0e8400-e29b-41d4-a716-446655440008",
  "fileName": "policy.pdf",
  "contentType": "application/pdf"
}
```

---

## Headquarters

### `GET` /api/v1/headquarters/statistics

**Headquarters statistics**

Aggregate stats across branch locations.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | headquarters |

#### Responses

- **200** — Statistics

```json
{
  "totalHeadquarters": 3,
  "activeHeadquarters": 3
}
```

---

## Health

### `GET` /api/v2/health

**Service health check**

Public liveness endpoint used by monitoring and load balancers. Returns UP status and service name.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | exempt |
| **Tags** | health |

#### Responses

- **200** — Service is up

```json
{
  "status": "UP",
  "service": "pimienta"
}
```

---

## Inventory

### `POST` /api/v1/inventory/transactions/purchase

**Create purchase transaction**

Records inventory purchase; may require approval workflow.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | STANDARD |
| **Tags** | inventory |

#### Request body

**Content-Type:** `application/json`

**Schema (summary):**

```json
{
  "type": "object"
}
```

**Example:**

```json
{
  "itemId": "aa0e8400-e29b-41d4-a716-446655440005",
  "quantity": 100,
  "locationId": "bb0e8400-e29b-41d4-a716-446655440006"
}
```

#### Responses

- **201** — Transaction created

```json
{
  "id": "cc0e8400-e29b-41d4-a716-446655440007",
  "type": "PURCHASE",
  "status": "PENDING_APPROVAL"
}
```

---

### `GET` /api/v1/inventory/stock/low-stock

**Low stock items**

Alerts for items below threshold — used by warehouse staff.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | inventory |

#### Responses

- **200** — Low stock list

```json
{
  "items": []
}
```

---

## Notifications

### `GET` /api/v1/notifications/management/statistics

**Notification statistics (admin)**

Admin dashboard metrics for sent notifications.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | notifications |

#### Responses

- **200** — Statistics

```json
{
  "totalSent": 150,
  "failed": 2
}
```

---

## Payroll

### `GET` /api/v1/payroll/records

**List payroll records**

Payroll records for finance operations.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | payroll |

#### Responses

- **200** — Paged payroll records

```json
{
  "items": [],
  "metadata": {
    "page": 0,
    "size": 20,
    "totalElements": 8,
    "totalPages": 1
  }
}
```

---

## Tasks

### `GET` /api/v1/tasks

**List tasks**

Internal task management for operations teams.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | tasks |

#### Responses

- **200** — Paged tasks

```json
{
  "items": [],
  "metadata": {
    "page": 0,
    "size": 20,
    "totalElements": 15,
    "totalPages": 1
  }
}
```

---

## Users

### `GET` /api/v1/users/me

**Current user profile**

Returns authenticated staff member profile.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | users |

#### Responses

- **200** — Profile

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "staff@pimienta.example",
  "fullName": "María García",
  "roles": [
    "ROLE_MANAGER"
  ]
}
```

---

### `GET` /api/v1/users/management

**List users (admin)**

Paginated user list for administrators.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | READ_HEAVY |
| **Tags** | users |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| page | query | integer | No | Zero-based page index |
| size | query | integer | No | Page size (1–100, default 20) |

#### Responses

- **200** — Paged users

```json
{
  "items": [],
  "metadata": {
    "page": 0,
    "size": 20,
    "totalElements": 0,
    "totalPages": 0
  }
}
```

---

### `POST` /api/v1/users/management/{id}/approve

**Approve pending user**

Administrator approves a PENDING_APPROVAL account so staff can log in.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | SENSITIVE_OPERATIONS |
| **Tags** | users |

#### Parameters

| Name | In | Type | Required | Description |
| --- | --- | --- | --- | --- |
| id | path | string | Yes | User UUID |

#### Responses

- **200** — User approved

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "status": "ACTIVE"
}
```

---

## Additional notes

# API Schema

> **Live production API** (`/api/v1`) serving Pimienta Alimentos company staff. Full interactive reference: `GET /swagger-ui` on the deployed host.

**Warning:** This document lists representative endpoints, not an exhaustive OpenAPI export. For the complete spec run the app and open `/v3/api-docs` or Swagger UI.

**Highlight:** All business routes require `Authorization: Bearer <access_token>` except `/api/v1/auth/**` and `/api/v2/health/**`.

**Placeholder:** Replace `{{PRODUCTION_HOST}}` in client configs with your EC2 public hostname or ALB DNS name.

