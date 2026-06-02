# Pimienta Alimentos - Backend API

> Enterprise ERP system for Pimienta Alimentos, a real food company in Mexico.

## Overview

Pimienta Alimentos Backend is a robust REST API built with **Spring Boot 4.0.5** and **Java 25** that provides comprehensive management for:

- 👥 **Employee Management** - Full CRUD, statistics, bulk import/export
- 💰 **Payroll** - Periods, records, payments, adjustments
- 📦 **Inventory** - Items, locations, stock, transactions
- 🤝 **CRM** - Projects, opportunities, milestones
- 🏢 **Headquarters** - Company locations/sedes management
- ✅ **Tasks** - Task management with checklists
- 📄 **Contracts** - Employee contract management
- 🔐 **Authentication** - JWT-based security with refresh tokens

## Tech Stack

| Category | Technology |
|----------|------------|
| Framework | Spring Boot 4.0.5 |
| Language | Java 25 |
| Database | PostgreSQL 16 |
| Cache/Rate Limiting | Redis 7 |
| API Documentation | OpenAPI 3 / Swagger UI |
| Excel Processing | Apache POI 5.4.1 |
| Build Tool | Maven |
| Security | Spring Security + JWT (jjwt) |

## Quick Start

### Prerequisites

- Java 25+
- Maven 3.9+
- Docker & Docker Compose (optional)

### Running Locally

```bash
# Clone and navigate
cd pimenta-alimentos/backend

# Run with Maven
./mvnw spring-boot:run

# Or with Docker (local Postgres + Redis + API)
docker compose -f docker/docker-compose.local.yml up -d --build
```

See [docker/README.md](docker/README.md) for local vs cloud Compose and environment variables.

### Access Points

| Service | URL |
|---------|-----|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |

## Architecture

The project follows **Hexagonal Architecture** (Ports & Adapters) with clear separation:

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                   │
│         REST Controllers, DTOs, Mappers, OpenAPI        │
├─────────────────────────────────────────────────────────┤
│                    Application Layer                     │
│              Use Cases, Commands, Queries               │
├─────────────────────────────────────────────────────────┤
│                      Domain Layer                        │
│            Entities, Value Objects, Enums               │
├─────────────────────────────────────────────────────────┤
│                   Infrastructure Layer                   │
│     JPA Repositories, Excel, Redis, Security           │
└─────────────────────────────────────────────────────────┘
```

## Modules

| Module | Path | Description |
|--------|------|-------------|
| Account | `/api/v1/auth`, `/api/v1/users` | Authentication & user management |
| Employees | `/api/v1/employees` | Employee CRUD, statistics, bulk operations |
| Payroll | `/api/v1/payroll` | Payroll periods, records, payments |
| Inventory | `/api/v1/inventory/*` | Items, stock, locations, movements |
| CRM | `/api/v1/projects`, `/api/v1/opportunities` | Projects & sales pipeline |
| Headquarters | `/api/v1/headquarters` | Company locations |
| Tasks | `/api/v1/tasks` | Task management |
| Contracts | `/api/v1/contracts` | Employee contracts |

## Security Features

- **JWT Authentication** - Access tokens (15 min TTL) + Refresh tokens (7 days in Redis)
- **Rate Limiting** - Redis-backed token bucket with profiles:
  - `STANDARD` - 100 req/min
  - `READ_HEAVY` - 200 req/min (GET endpoints)
  - `SENSITIVE_OPERATIONS` - 10 req/min (POST/PUT/DELETE)
- **CORS** - Configurable allowed origins per environment
- **Password Validation** - Custom strength requirements

## Bulk Operations

Import/export data using Excel (.xlsx) files:

```bash
# Example: Import employees
POST /api/v1/employees/import
Content-Type: multipart/form-data
Body: file=empleados.xlsx

# Example: Export employees
GET /api/v1/employees/export?status=ACTIVE&department=ventas
```

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_PROFILES_ACTIVE` | `default` | Spring profile |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/pimienta_alimentos` | DB URL |
| `REDIS_URL` | `redis://localhost:6379` | Redis URL (`redis://` or `rediss://` for TLS) |
| `PIMIENTA_REDIS_KEY_PREFIX` | `pimienta-alimentos` | Global Redis key namespace |
| `PIMIENTA_SECURITY_JWT_SECRET` | (required) | JWT signing secret |
| `SERVER_PORT` | `8080` | HTTP port |

## Deployment (AWS)

### Production Architecture

```
┌─────────────┐      ┌─────────────┐      ┌─────────────┐
│   CloudFront│ ───► │  ALB / API  │ ───► │  ECS        │
│   (CDN)     │      │  Gateway    │      │  Fargate    │
└─────────────┘      └─────────────┘      └─────────────┘
                                                  │
                     ┌─────────────┐      ┌──────┴──────┐
                     │   Elasti    │      │     RDS     │
                     │   Cache     │      │  PostgreSQL │
                     │   Redis     │      │             │
                     └─────────────┘      └─────────────┘
```

### Estimated AWS Monthly Cost

| Service | Estimated Cost |
|---------|---------------|
| RDS PostgreSQL (db.t3.micro) | $30-50 |
| ElastiCache Redis (cache.t3.micro) | $15-25 |
| ECS Fargate | $20-40 |
| ALB | $20-30 |
| Route 53 | $0.50 |
| **Total** | **$85-145** |

## Documentation

Full documentation available in `docs/`:

| Document | Description |
|----------|-------------|
| [docs/ProjectOverview.md](docs/ProjectOverview.md) | Business context and problem/solution |
| [docs/ProjectFeatures.md](docs/ProjectFeatures.md) | Feature details and status |
| [docs/ProjectArchitectureModel.md](docs/ProjectArchitectureModel.md) | Architecture, patterns, diagrams |
| [docs/InfrastructureModel.md](docs/InfrastructureModel.md) | Deployment, Docker, AWS |
| [docs/APISchema.md](docs/APISchema.md) | API endpoints and schemas |
| [docs/ProjectCodeShowCase.md](docs/ProjectCodeShowCase.md) | Code examples |
| [docs/ProjectMetric.md](docs/ProjectMetric.md) | Key metrics |
| [docs/MediaGallerySection.md](docs/MediaGallerySection.md) | Screenshots |

## API Examples

### Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password123"}'
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
curl -X GET http://localhost:8080/api/v1/employees \
  -H "Authorization: Bearer <access_token>"
```

## Project Structure

```
backend/
├── src/main/java/io/github/alexistrejo11/pimienta/
│   ├── config/           # Configuration classes
│   │   ├── cors/        # CORS configuration
│   │   ├── rate_limit/  # Rate limiting
│   │   ├── security/    # Security & JWT
│   │   └── web/         # Web config
│   ├── module/          # Business modules
│   │   ├── account/     # Auth & users
│   │   ├── employees/   # Employee management
│   │   ├── payroll/    # Payroll
│   │   ├── inventory/  # Inventory
│   │   ├── crm/        # CRM
│   │   ├── headquarter/# Headquarters
│   │   ├── task/       # Tasks
│   │   └── contract/  # Contracts
│   └── shared/         # Shared utilities
├── src/main/resources/
│   ├── application.yaml
│   └── pimenta-alimentos/  # Bruno API collections
├── docs/               # Documentation
└── docker/            # Dockerfile, Compose (local / cloud), Docker guide
```

## License

Apache License 2.0 - See [LICENSE](LICENSE)

## Contact

- **Developer**: Alexis Trejo
- **Email**: alexistrejo11@gmail.com
- **GitHub**: https://github.com/alexistrejo11/pimienta
