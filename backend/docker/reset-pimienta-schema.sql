-- Wipes Flyway history + all app tables in schema pimienta.
-- Use after squashing migrations (single V1) so Flyway runs from scratch on next startup.
DROP SCHEMA IF EXISTS pimienta CASCADE;
CREATE SCHEMA pimienta;
