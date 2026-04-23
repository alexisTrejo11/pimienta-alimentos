# Task module — integration test follow-ups

## Notes from TaskManagerIntegrationTest (initial pass)

- **H2 / DDL:** `TaskJpaEntity.checklist` used `columnDefinition = "jsonb"`, so Hibernate did not create the `tasks` table on the H2 integration profile (`Table "TASKS" not found`). Mapping was adjusted to rely on `@JdbcTypeCode(SqlTypes.JSON)` without a PostgreSQL-only DDL fragment so tests and multi-dialect bootstrapping stay aligned.

- **Empty import file error shape:** `POST /api/v1/tasks/import` with an empty multipart file returns **400** with `ApiErrorResponse.errorCode` **`INVALID_ARGUMENT`** (via `ResponseStatusException` handling), not a task-specific code. If clients need to distinguish “empty file” from other bad requests, consider a dedicated error code or a structured field in the error body.
- **Assignee integrity:** `PATCH /api/v1/tasks/{id}/assign` persists any numeric `employeeId` without checking that the employee exists (no FK on `assigned_to_id`). That is flexible for tests but may be worth a product decision on referential validation.

No extra blockers from this pass.
