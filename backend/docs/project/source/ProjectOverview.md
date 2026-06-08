---
problemStatement:
  problemTitle: "Fragmented operations for a growing food company"
  problemDescription: "Pimienta Alimentos needed a single backend to coordinate employees, payroll, inventory, sales opportunities, contracts, and internal tasks across headquarters — replacing spreadsheets, ad-hoc tools, and disconnected processes that did not scale with daily staff operations."
  problemList:
    - "Employee records, attendance, and work schedules lived in separate spreadsheets with no audit trail"
    - "Inventory movements (purchase, sale, transfer) lacked approval workflows and real-time stock visibility"
    - "Payroll periods and adjustments were error-prone without a centralized ledger"
    - "CRM opportunities and internal projects had no shared pipeline or milestone tracking"
    - "No secure, role-based API for the company frontend used by staff in the field and office"
    - "Bulk data updates (headquarters, tasks, payroll) required manual re-entry"
solution:
  solutionTitle: "Unified hexagonal Spring Boot API"
  solutionList:
    - title: "Modular bounded contexts"
      description: "Ten domain modules (account, employees, contract, CRM, task, headquarter, inventory, payroll, files, notification) each follow ports-and-adapters architecture."
    - title: "JWT authentication with admin approval"
      description: "Staff register and log in via JWT; new accounts require administrator approval before access. Refresh tokens stored in Redis (Upstash in production)."
    - title: "Operational workflows"
      description: "Inventory transactions, contract renewals, opportunity pipeline (win/lose/reopen), attendance start/end workday, and payroll adjustments are first-class API workflows."
    - title: "Bulk XLSX import/export"
      description: "Apache POI powers spreadsheet sync for employees, opportunities, tasks, headquarters, and payroll — matching how operations teams already work."
    - title: "AWS-backed file storage"
      description: "Employee photos and file assets upload to S3 with presigned download URLs and server-side image resizing."
    - title: "Production-ready infrastructure"
      description: "Deployed on AWS EC2 with RDS PostgreSQL and Upstash Redis; Docker images built via multi-stage Maven pipeline."
keyMetrics:
  metricsTitle: "Platform at a glance"
  metricsList:
    - "10 bounded-context modules"
    - "23 REST controllers"
    - "12 Flyway schema migrations"
    - "14 MockMvc integration test suites"
    - "JWT access token TTL: 15 minutes"
    - "API prefix: /api/v1 (business), /api/v2 (health)"
links:
  github: "https://github.com/alexistrejo11/pimienta"
  demo: "https://{{PRODUCTION_HOST}}/api/v2/health"
  documentation: "docs/project/generated/README.md"
  dockerHub: null
mediaGallery:
  title: "System screenshots"
  description: "Placeholder gallery — add frontend screenshots or architecture diagrams when available."
  items: []
mediaItems: []
metrics:
  - label: "Active users"
    value: "Company staff"
    description: "Used daily by Pimienta Alimentos employees and administrators in production."
  - label: "Deployment"
    value: "AWS EC2"
    description: "Spring Boot JAR in Docker on EC2; RDS PostgreSQL; Upstash Redis."
  - label: "API version"
    value: "v1.0.0"
    description: "Maven artifact io.github.alexistrejo11:pimienta:1.0.0"
  - label: "OpenAPI"
    value: "/swagger-ui"
    description: "Interactive API reference with Bearer JWT security scheme."
---

# Project Overview

> **Real-world production system:** Pimienta Alimentos Backend is live in the cloud and used by company employees and staff for day-to-day operations (HR, inventory, payroll, CRM, and more). This is not a portfolio toy project.

**Warning:** `liveDemoUrl` and `demo` link use `{{PRODUCTION_HOST}}` — replace with your public API hostname before publishing externally.

**Highlight:** The API serves an Angular/frontend client (CORS allows `localhost:4200` and production origins); all authenticated routes require `Authorization: Bearer <access_token>`.
