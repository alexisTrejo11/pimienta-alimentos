# CRM module — integration test follow-ups

## Notes from CrmIntegrationTest (initial pass)

- **Opportunity import empty file:** Same pattern as tasks — `POST /api/v1/opportunities/import` with an empty multipart file surfaces **`INVALID_ARGUMENT`** via `ResponseStatusException`, not a CRM-specific `errorCode`.
- **Illegal pipeline transitions:** Re-applying `POST .../pipeline/discovery` when the opportunity is no longer `NEW` returns **400** with **`INVALID_ARGUMENT`** (`IllegalStateException` path). Clients that need machine-readable transition rules may want richer context than the generic message.
- **Win / project payloads:** `WinOpportunityRequest` and standalone `CreateProjectRequest` accept arbitrary numeric `clientId` (no existence check in the exercised paths), mirroring other modules’ relaxed referential checks.

No extra blockers from this pass.
