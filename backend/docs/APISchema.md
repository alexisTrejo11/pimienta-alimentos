# API Schema

- **Type**: `REST`

---

## HTTP Endpoints (`ApiEndpoint[]`)

### Endpoint 1

- **ID**: auth-login
- **Method**: `POST`
- **URL Path**: /api/v1/auth/login
- **Summary**: User login
- **Description**: Authenticate user and return JWT tokens
- **Tags**:
  - Authentication
- **Authenticated**: `false`
- **Rate Limit**: STANDARD

#### Parameters

#### Request Body

- **Content Type**: application/json
- **Schema**: 
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Example**:
  ```json
  {
    "username": "admin",
    "password": "securePassword123"
  }
  ```

#### Responses

- **Status**: 200
- **Description**: Login successful
- **Schema**: 
  ```json
  {
    "accessToken": "string",
    "refreshToken": "string",
    "expiresIn": 900
  }
  ```

- **Status**: 401
- **Description**: Invalid credentials

---

### Endpoint 2

- **ID**: auth-register
- **Method**: `POST`
- **URL Path**: /api/v1/auth/register
- **Summary**: Register new user
- **Description**: Create a new user account
- **Tags**:
  - Authentication
- **Authenticated**: `false`
- **Rate Limit**: SENSITIVE_OPERATIONS

#### Request Body

- **Content Type**: application/json
- **Schema**: 
  ```json
  {
    "username": "string",
    "email": "string",
    "password": "string"
  }
  ```

#### Responses

- **Status**: 201
- **Description**: User created successfully
- **Status**: 400
- **Description**: Validation error

---

### Endpoint 3

- **ID**: employees-list
- **Method**: `GET`
- **URL Path**: /api/v1/employees
- **Summary**: Search employees
- **Description**: Search and filter employees with pagination
- **Tags**:
  - Employees
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Parameters

- **Name**: status
- **In**: `query`
- **Type**: string
- **Required**: `false`
- **Description**: Filter by employee status

- **Name**: department
- **In**: `query`
- **Type**: string
- **Required**: `false`
- **Description**: Filter by department

- **Name**: q
- **In**: `query`
- **Type**: string
- **Required**: `false`
- **Description**: Search query

- **Name**: page
- **In**: `query`
- **Type**: integer
- **Required**: `false`
- **Description**: Page number

- **Name**: size
- **In**: `query`
- **Type**: integer
- **Required**: `false`
- **Description**: Page size

#### Responses

- **Status**: 200
- **Description**: List of employees with pagination

---

### Endpoint 4

- **ID**: employees-create
- **Method**: `POST`
- **URL Path**: /api/v1/employees
- **Summary**: Register employee
- **Description**: Create a new employee record
- **Tags**:
  - Employees
- **Authenticated**: `true`
- **Rate Limit**: SENSITIVE_OPERATIONS

#### Request Body

- **Content Type**: application/json
- **Schema**: 
  ```json
  {
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "department": "string",
    "position": "string",
    "hireDate": "date"
  }
  ```

#### Responses

- **Status**: 201
- **Description**: Employee created successfully
- **Status**: 400
- **Description**: Validation error

---

### Endpoint 5

- **ID**: employees-export
- **Method**: `GET`
- **URL Path**: /api/v1/employees/export
- **Summary**: Export employees
- **Description**: Export employees to Excel file
- **Tags**:
  - Employees
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: Excel file download
- **Content-Type**: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet

---

### Endpoint 6

- **ID**: employees-import
- **Method**: `POST`
- **URL Path**: /api/v1/employees/import
- **Summary**: Import employees
- **Description**: Bulk import employees from Excel file
- **Tags**:
  - Employees
- **Authenticated**: `true`
- **Rate Limit**: SENSITIVE_OPERATIONS

#### Request Body

- **Content Type**: multipart/form-data
- **Schema**: 
  - file: binary (.xlsx)

#### Responses

- **Status**: 200
- **Description**: Import result with success/error count

---

### Endpoint 7

- **ID**: headquarters-list
- **Method**: `GET`
- **URL Path**: /api/v1/headquarters
- **Summary**: List headquarters
- **Description**: Get all headquarters/sedes
- **Tags**:
  - Headquarters
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: List of headquarters

---

### Endpoint 8

- **ID**: headquarters-create
- **Method**: `POST`
- **URL Path**: /api/v1/headquarters
- **Summary**: Create headquarters
- **Description**: Create a new headquarters/sede
- **Tags**:
  - Headquarters
- **Authenticated**: `true`
- **Rate Limit**: SENSITIVE_OPERATIONS

#### Responses

- **Status**: 201
- **Description**: Headquarters created

---

### Endpoint 9

- **ID**: payroll-periods
- **Method**: `GET`
- **URL Path**: /api/v1/payroll/periods
- **Summary**: List payroll periods
- **Description**: Get all payroll periods
- **Tags**:
  - Payroll
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: List of payroll periods

---

### Endpoint 10

- **ID**: payroll-records
- **Method**: `GET`
- **URL Path**: /api/v1/payroll/records
- **Summary**: List payroll records
- **Description**: Get payroll records with filters
- **Tags**:
  - Payroll
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Parameters

- **Name**: periodId
- **In**: `query`
- **Type**: long
- **Required**: `false`
- **Description**: Filter by period

- **Name**: employeeId
- **In**: `query`
- **Type**: long
- **Required**: `false`
- **Description**: Filter by employee

#### Responses

- **Status**: 200
- **Description**: List of payroll records

---

### Endpoint 11

- **ID**: inventory-items
- **Method**: `GET`
- **URL Path**: /api/v1/inventory/items
- **Summary**: List inventory items
- **Description**: Get all inventory items
- **Tags**:
  - Inventory
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: List of inventory items

---

### Endpoint 12

- **ID**: inventory-stock
- **Method**: `GET`
- **URL Path**: /api/v1/inventory/stock
- **Summary**: Get stock levels
- **Description**: Get current stock levels
- **Tags**:
  - Inventory
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: Stock levels

---

### Endpoint 13

- **ID**: crm-projects
- **Method**: `GET`
- **URL Path**: /api/v1/projects
- **Summary**: List projects
- **Description**: Get all CRM projects
- **Tags**:
  - CRM
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: List of projects

---

### Endpoint 14

- **ID**: crm-opportunities
- **Method**: `GET`
- **URL Path**: /api/v1/opportunities
- **Summary**: List opportunities
- **Description**: Get all sales opportunities
- **Tags**:
  - CRM
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: List of opportunities

---

### Endpoint 15

- **ID**: tasks-list
- **Method**: `GET`
- **URL Path**: /api/v1/tasks
- **Summary**: List tasks
- **Description**: Get all tasks
- **Tags**:
  - Tasks
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: List of tasks

---

### Endpoint 16

- **ID**: contracts-list
- **Method**: `GET`
- **URL Path**: /api/v1/contracts
- **Summary**: List contracts
- **Description**: Get all contracts
- **Tags**:
  - Contracts
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: List of contracts

---

### Endpoint 17

- **ID**: users-me
- **Method**: `GET`
- **URL Path**: /api/v1/users/me
- **Summary**: Get current user
- **Description**: Get authenticated user profile
- **Tags**:
  - Users
- **Authenticated**: `true`
- **Rate Limit**: STANDARD

#### Responses

- **Status**: 200
- **Description**: User profile

---

### Endpoint 18

- **ID**: users-list
- **Method**: `GET`
- **URL Path**: /api/v1/users
- **Summary**: List users
- **Description**: Get all users (admin only)
- **Tags**:
  - Users
- **Authenticated**: `true`
- **Rate Limit**: READ_HEAVY

#### Responses

- **Status**: 200
- **Description**: List of users

---

## Schemas (`ApiSchema[]`)

### Employee

```json
{
  "id": 1,
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "department": "string",
  "position": "string",
  "status": "ACTIVE|INACTIVE|TERMINATED",
  "hireDate": "2024-01-15",
  "terminationDate": null
}
```

### Headquarter

```json
{
  "id": 1,
  "name": "string",
  "address": "string",
  "city": "string",
  "phone": "string",
  "manager": "string"
}
```

### PayrollRecord

```json
{
  "id": 1,
  "employeeId": 1,
  "periodId": 1,
  "baseSalary": 1000.00,
  "bonus": 100.00,
  "deductions": 50.00,
  "netSalary": 1050.00,
  "status": "PENDING|PROCESSED|PAID"
}
```

### InventoryItem

```json
{
  "id": 1,
  "name": "string",
  "sku": "string",
  "category": "string",
  "unit": "string",
  "currentStock": 100,
  "minStock": 10,
  "location": "string"
}
```

### Project

```json
{
  "id": 1,
  "name": "string",
  "description": "string",
  "status": "ACTIVE|COMPLETED|CANCELLED",
  "startDate": "2024-01-01",
  "endDate": "2024-12-31",
  "budget": 50000.00
}
```

### AuthResponse

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "expiresIn": 900
}
```