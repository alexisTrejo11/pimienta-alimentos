# Employees — attendance integration follow-ups

## Notes from attendance integration tests (2026-05-14)

- **No extra follow-ups from this pass.** Assertions matched current API contracts (`PagedResponse` for search, stable `errorCode` values for 400/404/409 paths).

## Notes from attendance “today” / by-employee endpoints (2026-05-14)

- **Breaking change for API clients:** `GET /api/v1/employees/attendances/by-headquarter/{headquarterId}/today` was replaced by `GET /api/v1/employees/attendances/for-today?headquarterId=` (optional) returning **`PagedResponse`** instead of a raw JSON array. Frontends and BFFs should migrate.

- Integration tests now cover **403** on `for-today`, `search`, `{id}/attendances`, and JSON `end-workday` without JWT, plus **happy path** for `listByEmployee` with `workDateFrom` / `workDateTo`.
