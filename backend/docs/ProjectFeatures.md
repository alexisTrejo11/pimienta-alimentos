# Project Features

## Feature List (`ProjectFeature[]`)

### Feature 1

- **ID**: authentication
- **Title**: JWT Authentication
- **Description**: Sistema de autenticación con tokens JWT, incluyendo access tokens y refresh tokens almacenados en Redis
- **Icon**: 🔐
- **Category** (`FeatureCategory`): `authentication`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - Tokens JWT con HS256
  - Refresh tokens en Redis
  - Filtro de autenticación con Spring Security
- **Tech Stack** (optional):
  - Spring Security
  - jjwt library
  - Redis para refresh tokens
- **Metrics** (optional, `FeatureMetric[]`):
  - **Label**: Access Token TTL
  - **Value**: 15 minutos
  - **Trend** (optional): `stable`
  - **Icon** (optional): ""
- **Code Snippet** (optional, `CodeSnippet`):
  - **Language**: java
  - **Filename** (optional): JwtTokenService.java
  - **Code**:
    ```
    @Component
    public class JwtTokenService {
        public String generateAccessToken(UserDetails user) { ... }
        public String generateRefreshToken(UserDetails user) { ... }
    }
    ```
- **GitHub Example URL** (optional): ""

---

### Feature 2

- **ID**: rate-limiting
- **Title**: Rate Limiting con Redis Token Bucket
- **Description**: Rate limiting a nivel global y por endpoint usando algoritmo token bucket implementado en Redis
- **Icon**: 🚦
- **Category** (`FeatureCategory`): `security`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - Filtro global para /api/**
  - Annotated @RateLimit para endpoints específicos
  - Perfiles: STANDARD, READ_HEAVY, SENSITIVE_OPERATIONS
  - Lua scripts para operaciones atómicas en Redis
- **Tech Stack** (optional):
  - Redis
  - Spring AOP
  - Lua scripts

---

### Feature 3

- **ID**: excel-import-export
- **Title**: Importación/Exportación Excel
- **Description**: Carga masiva y exportación de datos en formato .xlsx usando Apache POI
- **Icon**: 📊
- **Category** (`FeatureCategory`): `integration`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - Importación de empleados, headquarters, tareas
  - Exportación con formato profesional
  - Manejo de errores por fila
  - Validación de datos
- **Tech Stack** (optional):
  - Apache POI 5.4.1
  - Spreadsheet templates

---

### Feature 4

- **ID**: employee-management
- **Title**: Gestión de Empleados
- **Description**: CRUD completo de empleados con estadísticas, búsqueda, filtrado y gestión de estado laboral
- **Icon**: 👥
- **Category** (`FeatureCategory`): `api`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - Registro, actualización, eliminación de empleados
  - Búsqueda por estado, departamento, texto
  - Estadísticas y resumen por departamento
  - Importación/exportación masiva
  - Terminación y recontratación

---

### Feature 5

- **ID**: payroll-management
- **Title**: Gestión de Nóminas
- **Description**: Administración de períodos de nómina, registros, pagos y ajustes
- **Icon**: 💰
- **Category** (`FeatureCategory`): `api`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - Períodos de nómina (semanal, quincenal, mensual)
  - Registros de nómina por empleado
  - Pagos y ajustes
  - Deudas y resúmenes
  - Exportación de nóminas

---

### Feature 6

- **ID**: inventory-management
- **Title**: Gestión de Inventarios
- **Description**: Control completo de items, ubicaciones, stock, transacciones y movimientos
- **Icon**: 📦
- **Category** (`FeatureCategory`): `api`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - Items y categorías
  - Ubicaciones de almacenamiento
  - Control de stock
  - Transacciones y movimientos
  - Historial completo

---

### Feature 7

- **ID**: crm-projects
- **Title**: CRM - Proyectos y Oportunidades
- **Description**: Gestión de proyectos, oportunidades comerciales y milestones
- **Icon**: 🤝
- **Category** (`FeatureCategory`): `api`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - Proyectos con milestones
  - Oportunidades de venta
  - Seguimiento de estados
  - Fechas y deadlines

---

### Feature 8

- **ID**: headquarters
- **Title**: Sedes/Headquarters
- **Description**: Gestión de ubicaciones/sedes de la empresa
- **Icon**: 🏢
- **Category** (`FeatureCategory`): `api`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - CRUD de sedes
  - Importación/exportación
  - Estadísticas por sede

---

### Feature 9

- **ID**: task-management
- **Title**: Gestión de Tareas
- **Description**: Sistema de tareas con checklists y estados
- **Icon**: ✅
- **Category** (`FeatureCategory`): `api`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - CRUD de tareas
  - Checklists
  - Estados personalizables
  - Importación/exportación Excel

---

### Feature 10

- **ID**: openapi-docs
- **Title**: OpenAPI/Swagger Documentation
- **Description**: Documentación interactiva de APIs con Swagger UI
- **Icon**: 📚
- **Category** (`FeatureCategory`): `monitoring`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - Documentación completa en /v3/api-docs
  - UI interactiva en /swagger-ui
  - Anotaciones personalizadas para documentación
  - Tags organizados por módulo

---

### Feature 11

- **ID**: contract-management
- **Title**: Gestión de Contratos
- **Description**: Administración de contratos de empleados
- **Icon**: 📄
- **Category** (`FeatureCategory`): `api`
- **Status** (`FeatureStatus`): `stable`
- **Highlights**:
  - CRUD de contratos
  - Estados de contrato
  - Vinculación con empleados
