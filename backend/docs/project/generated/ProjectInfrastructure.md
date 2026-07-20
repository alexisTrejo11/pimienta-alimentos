# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Runtime | AWS EC2 | Spring Boot API runs as a Docker container on an EC2 instance (profile docker). |
| Database | AWS RDS PostgreSQL 16 | Managed Postgres; Flyway migrations on startup; schema pimienta. |
| Cache / sessions | Upstash Redis | TLS via rediss:// URL; refresh tokens, rate limiting, key prefix pimienta-alimentos. |
| Object storage | AWS S3 | Employee photos and file assets; presigned URLs; region us-east-1. |
| Container port | 8080 | Exposed via API_PORT; non-root spring user inside Alpine JRE 25 image. |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| AWS EC2 | Hosts the Spring Boot API Docker container in production | Varies by instance type |
| AWS RDS (PostgreSQL 16) | Primary relational database for all domain data | Varies by instance / storage |
| Upstash Redis | Refresh token store, global and per-endpoint rate limiting (token bucket Lua script) | Pay-per-request / tiered |
| AWS S3 | Employee photos, file assets, presigned downloads | Storage + requests |
| AWS IAM | EC2 instance or access keys for S3 SDK | Included |

## Deployment layers

### Edge / client

- **Company frontend** — Web app used by Pimienta staff (Angular; CORS-configured origins).
- **Mobile / field staff** — Authenticated clients calling /api/v1/* with JWT Bearer tokens.

### Compute (AWS EC2)

- **Docker container** — Multi-stage image: Maven 3.9 + JDK 25 build → JRE 25 Alpine runtime.
- **Spring Boot API** — Profile docker; port 8080; Flyway + JPA validate on startup.

### Data layer

- **RDS PostgreSQL** — POSTGRES_URL via SPRING_DATASOURCE_* env vars in cloud compose.
- **Upstash Redis** — REDIS_URL=rediss://... for TLS; PIMIENTA_REDIS_KEY_PREFIX namespaces keys.
- **S3 bucket** — AWS_S3_BUCKET_NAME, AWS_S3_FOLDER, AWS_S3_SOURCES_ROOT.

## Docker configuration

### Production Dockerfile

Multi-stage build from backend project root; non-root user; exposes 8080.

```yaml
FROM maven:3.9-eclipse-temurin-25-alpine AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -q -B -DskipTests package

FROM eclipse-temurin:25-jre-alpine AS runtime
WORKDIR /app
RUN addgroup -S spring -g 1001 && adduser -S spring -u 1001 -G spring
COPY --from=build /workspace/target/*.jar /app/app.jar
RUN mkdir -p /app/logs && chown -R spring:spring /app
USER spring:spring
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS:-} -jar /app/app.jar"]
```

### Cloud compose (API only)

Connects to external RDS and Upstash Redis via .env — used in production on EC2.

```yaml
name: pimienta-backend
services:
  api:
    build: .
    ports:
      - "${API_PORT:-8080}:8080"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_DATASOURCE_URL: ${POSTGRES_URL}
      SPRING_DATASOURCE_USERNAME: ${POSTGRES_USER}
      SPRING_DATASOURCE_PASSWORD: ${POSTGRES_PASSWORD}
      REDIS_URL: ${REDIS_URL}
      PIMIENTA_SECURITY_JWT_SECRET: ${PIMIENTA_SECURITY_JWT_SECRET}
      AWS_REGION: ${AWS_REGION:-us-east-1}
      AWS_S3_BUCKET_NAME: ${AWS_S3_BUCKET_NAME}
```

## Additional notes

# Project Infrastructure

> **Production deployment:** AWS EC2 + RDS PostgreSQL + Upstash Redis. The API is live and serving company staff.

**Warning:** Never commit real `POSTGRES_URL`, `REDIS_URL`, or JWT secrets. Use `backend/.env` (from `.env.example`) on the EC2 host.

**Highlight:** `backend/docker-compose.yml` — default is API only; `--profile local` adds Postgres 16 + Redis 7. Single `Dockerfile`. Shared external network `pimienta-net`.

**Note:** Upstash requires `rediss://` (TLS). Set `PIMIENTA_REDIS_KEY_PREFIX=pimienta-alimentos` when sharing a Redis instance.

