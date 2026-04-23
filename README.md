# Pimienta Alimentos - ERP System

> Enterprise resource planning system for **Pimienta Alimentos**, a real food company in Mexico specializing in food products distribution and production.

## About the Company

**Pimienta Alimentos** is a Mexican company engaged in the food industry, requiring a comprehensive digital solution to manage their operations including:

- Employee management and HR processes
- Payroll processing
- Inventory and stock management
- Customer relationship management (CRM)
- Project tracking
- Task management
- Contract administration

## Project Overview

This is a **full-stack ERP application** consisting of:

| Component | Technology | Description |
|-----------|------------|-------------|
| **Backend** | Spring Boot 4.0.5 + Java 25 | REST API with PostgreSQL & Redis |
| **Frontend** | Angular 21 + TailwindCSS | Modern SPA web application |

## Quick Start

### Prerequisites

- Java 25+
- Node.js 20+
- PostgreSQL 16+
- Redis 7+

### Running Locally

#### Backend

```bash
cd backend

# With Maven
./mvnw spring-boot:run

# Or with Docker
docker-compose up -d
```

The API runs at **http://localhost:8080**

#### Frontend

```bash
cd frontend

# Install dependencies
npm install

# Run development server
npm start
```

The frontend runs at **http://localhost:4200**

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Frontend (Angular 21)                     │
│                    Single Page Application                        │
└─────────────────────────────┬───────────────────────────────────┘
                              │ HTTP/REST
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Backend (Spring Boot)                        │
│  ┌─────────────┬──────────────┬────────────┬─────────────────┐   │
│  │  Employees  │   Payroll   │ Inventory │      CRM        │   │
│  │  Module    │    Module   │  Module  │     Module      │   │
│  └─────────────┴──────────────┴────────────┴─────────────────┘   │
│  ┌─────────────┬──────────────┬────────────┬─────────────────┐   │
│  │  Tasks     │  Contracts   │Headquarters│     Account      │   │
│  │  Module   │    Module   │   Module  │     Module      │   │
│  └─────────────┴──────────────┴────────────┴─────────────────┘   │
└─────────────────────────────┬───────────────────────────────────┘
                              │ JPA / Redis
                              ▼
┌─────────────��───────────────────────────────────────────────────┐
│                      Data Layer                                   │
│  ┌─────────────────────────┐  ┌─────────────────────────────┐  │
│  │    PostgreSQL 16         │  │       Redis 7                │  │
│  │    (Primary DB)          │  │  (Cache + Rate Limiting)      │  │
│  └─────────────────────────┘  └─────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

## Backend Modules

| Module | API Path | Features |
|--------|---------|----------|
| **Account** | `/api/v1/auth`, `/api/v1/users` | JWT authentication, user management |
| **Employees** | `/api/v1/employees` | Full CRUD, statistics, bulk import/export Excel |
| **Payroll** | `/api/v1/payroll` | Periods, records, payments, adjustments |
| **Inventory** | `/api/v1/inventory/*` | Items, stock, locations, transactions |
| **CRM** | `/api/v1/projects`, `/api/v1/opportunities` | Projects, milestones, opportunities |
| **Headquarters** | `/api/v1/headquarters` | Company locations/sedes |
| **Tasks** | `/api/v1/tasks` | Task management with checklists |
| **Contracts** | `/api/v1/contracts` | Employee contracts |

## Tech Stack

### Backend

| Category | Technology |
|----------|------------|
| Framework | Spring Boot 4.0.5 |
| Language | Java 25 |
| Database | PostgreSQL 16 |
| Cache | Redis 7 |
| API Docs | OpenAPI 3 / Swagger UI |
| Excel | Apache POI 5.4.1 |
| Security | Spring Security + JWT |
| Build | Maven |

### Frontend

| Category | Technology |
|----------|------------|
| Framework | Angular 21 |
| Language | TypeScript |
| Styling | TailwindCSS 4.x |
| HTTP | RxJS 7.8 |
| SSR | Angular SSR |

## Security Features

- **JWT Authentication** - Access tokens (15 min TTL)
- **Refresh Tokens** - Stored in Redis (7 days TTL)
- **Rate Limiting** - Redis token bucket algorithm
- **CORS** - Configurable per environment
- **Password Validation** - Strength requirements

## API Documentation

Interactive API documentation available at:

- **Swagger UI**: http://localhost:8080/swagger-ui
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Deployment (AWS)

### Architecture

```
                          ┌──────────────────┐
                          │   CloudFront     │
                          │   (CDN + SSL)    │
                          └────────┬─────────┘
                                   │
                          ┌────────▼─────────┐
                          │  Application    │
                          │  Load Balancer  │
                          └────────┬─────────┘
                                   │
┌──────────────┐                    ┌───▼────────┐
│ ElastiCache │◄──────────────────┤    ECS     │
│   Redis    │                    │  Fargate   │
└────────────┘                    └──────┬─────┘
                                          │
                                   ┌──────▼──────┐
                                   │    RDS     │
                                   │ PostgreSQL │
                                   └───────────┘
```

### Estimated Monthly Cost (AWS)

| Service | Estimated Cost |
|---------|----------------|
| RDS PostgreSQL (db.t3.micro) | $30-50 |
| ElastiCache Redis (cache.t3.micro) | $15-25 |
| ECS Fargate | $20-40 |
| Application Load Balancer | $20-30 |
| CloudFront (CDN) | $5-15 |
| Route 53 | $0.50 |
| **Total** | **$90-160** |

## Project Structure

```
pimienta-alimentos/
├── backend/                    # Spring Boot API
│   ├── src/main/java/        # Java source code
│   │   └── io/github/alexistrejo11/pimienta/
│   │       ├── config/      # Configuration
│   │       ├── module/      # Business modules
│   │       │   ├── account/
│   │       │   ├── employees/
│   │       │   ├── payroll/
│   │       │   ├── inventory/
│   │       │   ├── crm/
│   │       │   ├── headquarter/
│   │       │   ├── task/
│   │       │   └── contract/
│   │       └── shared/       # Shared utilities
│   ├── src/main/resources/
│   │   ├── application.yaml
│   │   └── pimenta-alimentos/  # Bruno API collections
│   ├── src/test/            # Unit & integration tests
│   ├── docs/               # Documentation
│   ├── docker-compose.yml   # Local development
│   ├── Dockerfile          # Production container
│   └── pom.xml             # Maven configuration
│
├── frontend/                   # Angular SPA
│   ├── src/
│   │   └── app/           # Angular source
│   ├── angular.json        # Angular config
│   ├── package.json       # NPM dependencies
│   └── tailwind.config   # TailwindCSS config
│
├── docs/                    # Documentation (mirrored)
└── README.md               # This file
```

## Documentation Index

Full technical documentation available:

### Backend Docs (`backend/docs/`)

| Document | Description |
|----------|-------------|
| [backend/docs/ProjectOverview.md](backend/docs/ProjectOverview.md) | Business context and problem/solution |
| [backend/docs/ProjectFeatures.md](backend/docs/ProjectFeatures.md) | Feature details and status |
| [backend/docs/ProjectArchitectureModel.md](backend/docs/ProjectArchitectureModel.md) | Architecture, patterns, data flow |
| [backend/docs/InfrastructureModel.md](backend/docs/InfrastructureModel.md) | Deployment, Docker, AWS |
| [backend/docs/APISchema.md](backend/docs/APISchema.md) | API endpoints and schemas |
| [backend/docs/ProjectCodeShowCase.md](backend/docs/ProjectCodeShowCase.md) | Code examples |
| [backend/docs/ProjectMetric.md](backend/docs/ProjectMetric.md) | Key metrics |
| [backend/docs/ProjectMetadata.md](backend/docs/ProjectMetadata.md) | Project metadata |
| [backend/docs/MediaGallerySection.md](backend/docs/MediaGallerySection.md) | Screenshots |

## Environment Variables

### Backend

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_PROFILES_ACTIVE` | `default` | Spring profile |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/pimienta_alimentos` | DB URL |
| `SPRING_DATASOURCE_USERNAME` | `pimienta_dba` | DB username |
| `SPRING_DATASOURCE_PASSWORD` | `pimienta_dba` | DB password |
| `SPRING_DATA_REDIS_HOST` | `localhost` | Redis host |
| `SPRING_DATA_REDIS_PORT` | `6379` | Redis port |
| `PIMIENTA_SECURITY_JWT_SECRET` | (required) | JWT signing secret |
| `SERVER_PORT` | `8080` | HTTP port |

## API Examples

### Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"yourPassword123"}'
```

Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "expiresIn": 900
}
```

### Get Employees

```bash
curl -X GET http://localhost:8080/api/v1/employees?status=ACTIVE \
  -H "Authorization: Bearer <access_token>"
```

### Bulk Import Employees

```bash
curl -X POST http://localhost:8080/api/v1/employees/import \
  -H "Authorization: Bearer <access_token>" \
  -F "file=@empleados.xlsx"
```

## License

Apache License 2.0 - See [LICENSE](LICENSE)

## Contact

- **Developer**: Alexis Trejo
- **Email**: alexistrejo11@gmail.com
- **GitHub**: https://github.com/alexistrejo11/pimienta
- **Company**: Pimienta Alimentos (Mexico)