---
metrics:
  - label: "Runtime"
    value: "AWS EC2"
    icon: "server"
    description: "Spring Boot API runs as a Docker container on an EC2 instance (profile docker)."
  - label: "Database"
    value: "AWS RDS PostgreSQL 16"
    icon: "database"
    description: "Managed Postgres; Flyway migrations on startup; schema pimienta."
  - label: "Cache / sessions"
    value: "Upstash Redis"
    icon: "redis"
    description: "TLS via rediss:// URL; refresh tokens, rate limiting, key prefix pimienta-alimentos."
  - label: "Object storage"
    value: "AWS S3"
    icon: "cloud"
    description: "Employee photos and file assets; presigned URLs; region us-east-1."
  - label: "Container port"
    value: "8080"
    icon: "network"
    description: "Exposed via API_PORT; non-root spring user inside Alpine JRE 25 image."
cloudServices:
  - name: "AWS EC2"
    purpose: "Hosts the Spring Boot API Docker container in production"
    icon: "ec2"
    cost: "Varies by instance type"
  - name: "AWS RDS (PostgreSQL 16)"
    purpose: "Primary relational database for all domain data"
    icon: "rds"
    cost: "Varies by instance / storage"
  - name: "Upstash Redis"
    purpose: "Refresh token store, global and per-endpoint rate limiting (token bucket Lua script)"
    icon: "redis"
    cost: "Pay-per-request / tiered"
  - name: "AWS S3"
    purpose: "Employee photos, file assets, presigned downloads"
    icon: "s3"
    cost: "Storage + requests"
  - name: "AWS IAM"
    purpose: "EC2 instance or access keys for S3 SDK"
    icon: "iam"
    cost: "Included"
deploymentLayers:
  - name: "Edge / client"
    components:
      - name: "Company frontend"
        icon: "browser"
        description: "Web app used by Pimienta staff (Angular; CORS-configured origins)."
      - name: "Mobile / field staff"
        icon: "mobile"
        description: "Authenticated clients calling /api/v1/* with JWT Bearer tokens."
    color: "#3B82F6"
  - name: "Compute (AWS EC2)"
    components:
      - name: "Docker container"
        icon: "docker"
        description: "Multi-stage image: Maven 3.9 + JDK 25 build → JRE 25 Alpine runtime."
      - name: "Spring Boot API"
        icon: "spring"
        description: "Profile docker; port 8080; Flyway + JPA validate on startup."
    color: "#10B981"
  - name: "Data layer"
    components:
      - name: "RDS PostgreSQL"
        icon: "postgres"
        description: "POSTGRES_URL via SPRING_DATASOURCE_* env vars in cloud compose."
      - name: "Upstash Redis"
        icon: "redis"
        description: "REDIS_URL=rediss://... for TLS; PIMIENTA_REDIS_KEY_PREFIX namespaces keys."
      - name: "S3 bucket"
        icon: "s3"
        description: "AWS_S3_BUCKET_NAME, AWS_S3_FOLDER, AWS_S3_SOURCES_ROOT."
    color: "#8B5CF6"
dockerFiles:
  - service: "Production Dockerfile"
    description: "Multi-stage build from backend project root; non-root user; exposes 8080."
    content: |
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
  - service: "Cloud compose (API only)"
    description: "Connects to external RDS and Upstash Redis via .env — used in production on EC2."
    content: |
      name: pimienta-backend
      services:
        api:
          build:
            context: ..
            dockerfile: docker/Dockerfile
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
---

# Project Infrastructure

> **Production deployment:** AWS EC2 + RDS PostgreSQL + Upstash Redis. The API is live and serving company staff.

**Warning:** Never commit real `POSTGRES_URL`, `REDIS_URL`, or JWT secrets. Use `backend/.env` (from `.env.example`) on the EC2 host.

**Highlight:** Local development uses `docker/docker-compose.local.yml` (Postgres 16 + Redis 7 + dev API with hot reload). Cloud uses `docker/docker-compose.cloud.yml` (API container only, external managed services).

**Note:** Upstash requires `rediss://` (TLS). Set `PIMIENTA_REDIS_KEY_PREFIX=pimienta-alimentos` when sharing a Redis instance.
