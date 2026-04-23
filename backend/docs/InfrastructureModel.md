# Infrastructure Model

## 1. Deployment Layers (`DeploymentLayer[]`)

### Layer 1

- **Name**: Client Layer
- **Color**: #4A90D9
- **Components** (`DeploymentComponent[]`):
  - **Component 1**
    - **Name**: Web Application (SPA)
    - **Icon**: 🌐
    - **Description**: Frontend application consuming the API
  - **Component 2**
    - **Name**: Mobile Apps (Future)
    - **Icon**: 📱
    - **Description**: Mobile applications

---

### Layer 2

- **Name**: Load Balancer / CDN
- **Color**: #F39C12
- **Components**:
  - **AWS ALB / CloudFront**
    - **Icon**: ⚖️
    - **Description**: Application Load Balancer for traffic distribution
  - **AWS API Gateway**
    - **Icon**: 🚪
    - **Description**: API Gateway for routing and management

---

### Layer 3

- **Name**: Application Layer (AWS)
- **Color**: #50C878
- **Components** (`DeploymentComponent[]`):
  - **Component 1**
    - **Name**: AWS ECS Fargate
    - **Icon**: 🐳
    - **Description**: Containerized Spring Boot application
  - **Component 2**
    - **Name**: AWS ECR
    - **Icon**: 📦
    - **Description**: Container registry for Docker images

---

### Layer 4

- **Name**: Data Layer
- **Color**: #9B59B6
- **Components** (`DeploymentComponent[]`):
  - **Component 1**
    - **Name**: AWS RDS PostgreSQL
    - **Icon**: 🐘
    - **Description**: Managed PostgreSQL database
  - **Component 2**
    - **Name**: AWS ElastiCache Redis
    - **Icon**: 🔴
    - **Description**: Managed Redis for caching and rate limiting

---

## 2. Docker Files (`DockerFile[]`)

### Service 1

- **Service**: Pimienta API
- **Description**: Spring Boot Java application container
- **Content**:
  ```dockerfile
  FROM eclipse-temurin:25-jdk-alpine AS build
  WORKDIR /app
  COPY pom.xml .
  COPY src ./src
  COPY mvnw .
  RUN chmod +x mvnw
  RUN ./mvnw clean package -DskipTests

  FROM eclipse-temurin:25-jre-alpine
  WORKDIR /app
  COPY --from=build /app/target/*.jar app.jar
  EXPOSE 8080
  ENTRYPOINT ["java", "-jar", "app.jar"]
  ```

---

## 3. Cloud Services (`CloudService[]`)

For each service:

- **Name**: AWS RDS PostgreSQL
- **Purpose**: Primary database
- **Icon**: 🐘
- **Cost**: ~$30-50/month (db.t3.micro)

- **Name**: AWS ElastiCache Redis
- **Purpose**: Caching and rate limiting
- **Icon**: 🔴
- **Cost**: ~$15-25/month (cache.t3.micro)

- **Name**: AWS ECS Fargate
- **Purpose**: Application hosting
- **Icon**: 🐳
- **Cost**: ~$20-40/month based on usage

- **Name**: AWS ECR
- **Purpose**: Container registry
- **Icon**: 📦
- **Cost**: ~$1/month (storage)

- **Name**: AWS Application Load Balancer
- **Purpose**: Load distribution and SSL termination
- **Icon**: ⚖️
- **Cost**: ~$20-30/month

- **Name**: AWS Route 53
- **Purpose**: DNS management
- **Icon**: 🌐
- **Cost**: ~$0.50/month

- **Name**: AWS Certificate Manager
- **Purpose**: SSL/TLS certificates
- **Icon**: 🔒
- **Cost**: Free

---

## 4. Metrics (`InfrastructureMetric[]`)

For each metric:

- **Label**: API Availability
- **Value**: 99.9%
- **Icon**: ✅
- **Description**: Target uptime SLA

- **Label**: Response Time (P95)
- **Value**: < 200ms
- **Icon**: ⏱️
- **Description**: 95th percentile response time

- **Label**: Concurrent Users
- **Value**: 100+
- **Icon**: 👥
- **Description**: Expected concurrent users

- **Label**: Daily API Calls
- **Value**: 10,000+
- **Icon**: 📊
- **Description**: Expected daily API requests