---
name: pimienta-backend-project-structure
description: >-
  Pimienta Alimentos backend layout: module package, hexagonal folders (core vs
  infrastructure), shared kernel, and naming. Use when adding features or new bounded contexts.
---

# Pimienta backend — project structure

Root: **`company-system/backend`**. Base Java package: **`io.github.alexistrejo11.pimienta`**.

## Top-level packages

| Package | Role |
|--------|------|
| `PimientaApplication` | Spring Boot entry (`@SpringBootApplication`) |
| `shared` | Cross-cutting: `BaseDomain`, exceptions, web filters, global exception handler |
| `module.{boundedContext}` | One folder per bounded context (e.g. `module.headquarter`) |

## Inside a bounded context (`module.{name}`)

```
module.{name}/
├── core/
│   ├── domain/              # Aggregates, value objects, domain records (e.g. statistics)
│   ├── application/       # Use case interfaces, *UseCasesImpl, commands
│   └── port/                # Inbound/outbound interfaces (e.g. repositories)
└── infrastructure/
    ├── adapter/
    │   ├── inbound/web/     # REST controllers, request/response DTOs, mappers
    │   └── out/persistence/ # RepositoryImpl, JPA entities, Spring Data repos, persistence mappers
```

- **`core`**: pure application + domain; **ports** define contracts; **no** Spring/JPA imports in domain.
- **`infrastructure`**: adapters; **implements** ports; **contains** framework and persistence details.

## Naming

| Layer | Pattern | Example |
|-------|---------|---------|
| Domain entity | `{Entity}` | `Headquarter` |
| Use case API | `{Entity}UseCases` | `HeadquarterUseCases` |
| Use case impl | `{Entity}UseCasesImpl` | `HeadquarterUseCasesImpl` |
| Repository port | `{Entity}Repository` | `HeadquarterRepository` |
| Repository adapter | `{Entity}RepositoryImpl` | `HeadquarterRepositoryImpl` |
| Spring Data JPA | `{Entity}JpaRepository` | `HeadquarterJpaRepository` |
| JPA `@Entity` | `{Entity}JpaEntity` | `HeadquarterJpaEntity` |
| Persistence mapper | `{Entity}PersistenceMapper` | `HeadquarterPersistenceMapper` |
| REST controller | `HeadQuarterController` (match existing naming) | — |
| HTTP DTOs | `{Entity}Request`, `{Entity}Response`, … | `HeadQuarterRequest` |

## REST URLs

- Versioned API: **`/api/v1/...`** under the inbound web adapter.
- Prefer **resource-oriented** paths; static segments (e.g. `/statistics`) before **`/{id}`** to avoid path conflicts.

## Configuration

- **`src/main/resources/application.yaml`**: datasource, JPA, Redis, server, logging.

## Tests

- `src/test/java` mirrors main package; use Spring Boot test starters for integration tests.

## Related

- Per-layer coding rules: **`pimienta-backend-layer-conventions`** skill.
