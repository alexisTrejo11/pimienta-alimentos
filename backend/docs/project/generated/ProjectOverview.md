# Project Overview

## Fragmented operations for a growing food company

Pimienta Alimentos needed a single backend to coordinate employees, payroll, inventory, sales opportunities, contracts, and internal tasks across headquarters — replacing spreadsheets, ad-hoc tools, and disconnected processes that did not scale with daily staff operations.

### Pain points

- Employee records, attendance, and work schedules lived in separate spreadsheets with no audit trail
- Inventory movements (purchase, sale, transfer) lacked approval workflows and real-time stock visibility
- Payroll periods and adjustments were error-prone without a centralized ledger
- CRM opportunities and internal projects had no shared pipeline or milestone tracking
- No secure, role-based API for the company frontend used by staff in the field and office
- Bulk data updates (headquarters, tasks, payroll) required manual re-entry

## Unified hexagonal Spring Boot API

- **Modular bounded contexts** — Ten domain modules (account, employees, contract, CRM, task, headquarter, inventory, payroll, files, notification) each follow ports-and-adapters architecture.
- **JWT authentication with admin approval** — Staff register and log in via JWT; new accounts require administrator approval before access. Refresh tokens stored in Redis (Upstash in production).
- **Operational workflows** — Inventory transactions, contract renewals, opportunity pipeline (win/lose/reopen), attendance start/end workday, and payroll adjustments are first-class API workflows.
- **Bulk XLSX import/export** — Apache POI powers spreadsheet sync for employees, opportunities, tasks, headquarters, and payroll — matching how operations teams already work.
- **AWS-backed file storage** — Employee photos and file assets upload to S3 with presigned download URLs and server-side image resizing.
- **Production-ready infrastructure** — Deployed on AWS EC2 with RDS PostgreSQL and Upstash Redis; Docker images built via multi-stage Maven pipeline.

## Platform at a glance

- 10 bounded-context modules
- 23 REST controllers
- 12 Flyway schema migrations
- 14 MockMvc integration test suites
- JWT access token TTL: 15 minutes
- API prefix: /api/v1 (business), /api/v2 (health)

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexistrejo11/pimienta |
| Demo | https://{{PRODUCTION_HOST}}/api/v2/health |
| Documentation | docs/project/generated/README.md |
| Dockerhub | None |

## System screenshots

Placeholder gallery — add frontend screenshots or architecture diagrams when available.

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Active users | Company staff | Used daily by Pimienta Alimentos employees and administrators in production. |
| Deployment | AWS EC2 | Spring Boot JAR in Docker on EC2; RDS PostgreSQL; Upstash Redis. |
| API version | v1.0.0 | Maven artifact io.github.alexistrejo11:pimienta:1.0.0 |
| OpenAPI | /swagger-ui | Interactive API reference with Bearer JWT security scheme. |

## Additional notes

# Project Overview

> **Real-world production system:** Pimienta Alimentos Backend is live in the cloud and used by company employees and staff for day-to-day operations (HR, inventory, payroll, CRM, and more). This is not a portfolio toy project.

**Warning:** `liveDemoUrl` and `demo` link use `{{PRODUCTION_HOST}}` — replace with your public API hostname before publishing externally.

**Highlight:** The API serves an Angular/frontend client (CORS allows `localhost:4200` and production origins); all authenticated routes require `Authorization: Bearer <access_token>`.

