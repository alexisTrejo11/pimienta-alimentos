# Project Overview

## 1. Problem Statement (`OverviewProblemStatement`)

- **Problem Title**: Gestión Integral de Recursos Humanos para Pimienta Alimentos
- **Problem Description**:
  - Pimienta Alimentos es una empresa alimenticia que requiere digitalizar y automatizar la gestión de recursos humanos, nóminas, inventario, contratos, proyectos y más.
  - Sin un sistema centralizado, la gestión manual genera errores, ineficiencia y falta de trazabilidad.
- **Problem List**:
  - Gestión manual de empleados, nóminas e inventarios
  - Falta de trazabilidad en procesos de RRHH
  - No existia integracion entre moduli
  - Dificultad para generar reportes y estadísticas

---

## 2. Solution (`OverviewSolution`)

- **Solution Title**: Sistema ERP Web para Pimienta Alimentos
- **Solution List** (array of `Solution`):
  - **Solution 1**
    - **Title**: API REST con Spring Boot
    - **Description**: Backend robusto con Java 25, Spring Boot 4.0.5, PostgreSQL y Redis para caching y rate-limiting
  - **Solution 2**
    - **Title**: Módulos Integrados
    - **Description**: Gestión de empleados, nóminas, inventarios, contratos, proyectos CRM, headquarter y tareas
  - **Solution 3**
    - **Title**: Seguridad JWT
    - **Description**: Autenticación y autorización con tokens JWT, rate-limiting por IP y CORS configurables
  - **Solution 4**
    - **Title**: Importación/Exportación Excel
    - **Description**: Carga masiva y exportación de datos mediante archivos .xlsx usando Apache POI

---

## 3. Key Metrics (`OverviewKeyMetrics`)

- **Metrics Title**: Métricas del Proyecto
- **Metrics List** (strings):
  - 18+ endpoints RESTful
  - 6+ módulos de negocio
  - PostgreSQL + Redis como almacenamiento
  - 100% cobertura de APIs documentadas con OpenAPI/Swagger UI

See also [ProjectMetric.md](ProjectMetric.md) for richer metrics.

---

## 4. Cover Image (`ProjectCoverImage`, optional)

- **URL**: ""
- **Alt**: ""
- **Credit** (optional): ""

---

## 5. Links (`ProjectLinks`)

See [ProjectLinks.md](ProjectLinks.md).

- **GitHub**: https://github.com/alexistrejo11/pimienta
- **Demo**: https://api.pimienta-alimentos.com
- **Documentation**: https://api.pimienta-alimentos.com/swagger-ui
- **Docker Hub**: 

---

## 6. Media Gallery Section (`MediaGallerySection`)

See [MediaGallerySection.md](MediaGallerySection.md).

- **Title**: ""
- **Description**: ""
- **Items**: list of media items (see `ProjectMediaItem` in MediaGallerySection.md).

---

## 7. Media Items (`ProjectMediaItem[]`)

For each media item:

- **Type**: `image` | `video`
- **URL**: ""
- **Thumbnail** (optional): ""
- **Title**: ""
- **Description**: ""
- **Alt** (optional): ""
- **Category** (optional): `screenshot` | `diagram` | `demo` | `architecture`

---

## 8. Metrics (`ProjectMetric[]`)

For each metric see [ProjectMetric.md](ProjectMetric.md):

- **Label**: ""
- **Value**: ""
- **Description** (optional): ""
- **Icon** (optional): ""
- **Unit** (optional): ""
- **Trend** (optional): `up` | `down` | `stable`
- **Threshold** (optional):