---
name: pimienta-backend-layer-conventions
description: >-
  Pimienta Alimentos Spring Boot backend: hexagonal layer rules for controllers,
  use case interfaces and implementations, domain models, ports, and repository
  adapters. Apply when editing company-system/backend Java code or designing new modules.
---

# Pimienta backend — layer conventions

Rules for `company-system/backend` and the `io.github.alexistrejo11.pimienta` package tree. Prefer **orchestration** in application code, **interfaces** at boundaries, and **no infrastructure types** in domain.

## Controller (inbound web adapter)

- Validate request bodies with **`@Valid`** when the body is a DTO.
- Use **DTOs** for request and response; do not expose domain entities or persistence models on the HTTP API.
- Use **mappers** to convert DTO ↔ domain (or commands) at the adapter boundary.
- Use **`var`** only in static methods with verbose naming (otherwise prefer explicit types).
- Base path: **`/api/v1/{entityName}`** (plural resource name as appropriate).
- **DELETE** endpoints return **`ResponseEntity<Void>`** (e.g. `204 No Content`).
- If there are **more than two** request parameters (path/query/body fields), group them in a **request object**.
- **Locals vs. nesting**: Do not hide intermediate values inside **method arguments** (e.g. `mapper.toDto(useCases.run(mapper.toCommand(req)))`). **Declare** each step on its own line—command from the request, aggregate or result from the use case, then the response DTO—when there is more than one transformation or when nesting would obscure the flow.
- **One-line returns** are fine when the chain is **short and obvious**: typically one use-case call and one mapper to the response type (e.g. `return HeadQuarterWebMapper.toResponse(headquarterUseCases.getById(id));`), or a single delegate with a self-explanatory name. If in doubt, use locals.
- **Do not** return `Object`; use a concrete type or a bounded generic (`?` / wildcard) only when flexibility is required.

## Use case interface (application port)

- **Query** methods that return collections of existing entities should use the **`get`** prefix (e.g. `getBy(Pageable)`). **Missing** data is signaled by **exceptions** in the implementation, not by returning null for “not found” aggregates.
- **Delete** means **soft delete** unless explicitly documented otherwise; return **`void`**.
- **Statistics** are optional but useful for management; return a dedicated type when needed.
- Prefer **`List<T>`** only when the result is bounded (e.g. at most **20** items); for larger or unbounded lists use **`Page<T>`**.
- Return **`void`** or a **command-specific** / **read model** type as appropriate.

## Use case implementation (`*UseCasesImpl`)

- Name the class **`{InterfaceName}Impl`** (e.g. `HeadquarterUseCasesImpl`).
- Prefer the name **use cases** for this layer; reserve **service** for **domain services** inside the domain model.
- **Imports**: from **core** (application + domain) you may use concrete types. For **other modules** or **infrastructure** (repositories, external APIs), depend only on **ports** (**interfaces**), not adapters.
- Implementations may **throw** domain or application exceptions.
- **`var`** is allowed only in static methods with verbose naming (same as controller).
- **Get** flows return **domain** types; **commands** may return domain or a dedicated DTO if needed.
- Treat the class as an **orchestrator**: describe **what** happens, **not** low-level imperative plumbing.

## Domain

- Aggregate / entity class name matches the **business entity** name.
- Extend **`BaseDomain<ID>`** for shared id, timestamps, version.
- Fields are **`private`**; expose **getters**; **avoid setters** where possible—prefer **factory** and **behavior** methods.
- Prefer **private** constructors and **defaults** that avoid **NPEs** on internal state.
- Provide at least one **creating** static factory (e.g. **`create`**, **`hire`**, **`register`**) that validates business rules, and a **`reconstruct`** factory for loading from persistence **without** re-running aggregate validation.
- Add **`reconstruct(Reconstruct{Entity}Params)`** (or similarly named record) so repository mappers pass **one** object built from DB columns / JPA rows.
- For **any** static factory or static method with **more than two** parameters (after grouping value objects), introduce a **parameter record** (e.g. **`Create{Entity}Params`**, **`Hire{Entity}Params`**, **`Update{Entity}Params`**).
- Use **custom exceptions** (aligned with shared error handling) for rule violations.
- **Value objects** / embedded types validate themselves; aggregates may still validate nulls at boundaries.

## Port (repository interface in `core/port`)

- **Ports** are **interfaces** owned by the core module; **no** Spring/JPA types in the interface signature.
- **Queries** returning nothing use **`Optional<T>`** or **`Page<T>`** as appropriate; **save** returns the aggregate.
- Naming: **`find*`** for reads, **`save`** for persist; document **null** behavior in the implementation.

## Repository implementation (`*RepositoryImpl`)

- Name the class **`{EntityName}RepositoryImpl`** and implement the port **`{EntityName}Repository`**.
- **Fields** are **`private`**; inject dependencies via **constructor**.
- **Methods** are **`public`** and implement the port contract.
- **Always** map between **JPA entity** (or other persistence model) and **domain** via a **mapper**; never leak JPA entities outside the adapter.
- **Always** delegate persistence operations to the **Spring Data JPA** repository (or other client), not ad-hoc SQL in the adapter unless justified.
- **Query** methods may receive nullable inputs; return **`Optional`** where appropriate and **validate nulls** defensively.

## Related

- Project layout and package naming: see `pimienta-backend-project-structure` skill.
