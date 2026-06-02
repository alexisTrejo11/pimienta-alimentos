-- Pimienta Alimentos — headquarters (physical branches / offices)

CREATE TABLE headquarters (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(512),
    address     VARCHAR(1024),
    description VARCHAR(4096),
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP,
    version     BIGINT       NOT NULL DEFAULT 1
);

CREATE INDEX idx_headquarters_name ON headquarters (name);
CREATE INDEX idx_headquarters_deleted_at ON headquarters (deleted_at);

COMMENT ON TABLE headquarters IS 'Physical branches / offices used by attendance and tasks.';
