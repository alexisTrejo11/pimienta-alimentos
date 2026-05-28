-- Company file-asset catalog (general-purpose file manager)
-- S3 prefix: pimienta/sources/{category}/{...}

CREATE TABLE file_assets (
    id                  UUID         PRIMARY KEY,
    category            VARCHAR(32)  NOT NULL,          -- TEMPLATE | COMPANY | EXTRAS | RESOURCE
    module              VARCHAR(64),                    -- only for RESOURCE category
    entity_type         VARCHAR(64),                    -- e.g. 'inventory-item', nullable
    entity_id           BIGINT,                         -- nullable
    s3_key              VARCHAR(1000) NOT NULL,
    original_name       VARCHAR(500)  NOT NULL,
    content_type        VARCHAR(120)  NOT NULL,
    file_size_bytes     BIGINT,
    description         VARCHAR(1000),
    uploaded_by_user_id BIGINT,
    created_at          TIMESTAMP    NOT NULL,
    updated_at          TIMESTAMP    NOT NULL,
    deleted_at          TIMESTAMP,
    version             BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_file_assets_category       ON file_assets (category);
CREATE INDEX idx_file_assets_module         ON file_assets (module);
CREATE INDEX idx_file_assets_entity         ON file_assets (entity_type, entity_id);
CREATE INDEX idx_file_assets_uploader       ON file_assets (uploaded_by_user_id);
CREATE INDEX idx_file_assets_deleted_at     ON file_assets (deleted_at);
CREATE INDEX idx_file_assets_created_at     ON file_assets (created_at);

COMMENT ON TABLE  file_assets                 IS 'Catalog of company-managed files stored in S3 (pimienta/sources/).';
COMMENT ON COLUMN file_assets.category        IS 'TEMPLATE | COMPANY | EXTRAS | RESOURCE';
COMMENT ON COLUMN file_assets.module          IS 'Owning module slug for RESOURCE category (e.g. inventory, crm).';
COMMENT ON COLUMN file_assets.entity_type     IS 'Optional domain entity type tag within the module.';
COMMENT ON COLUMN file_assets.entity_id       IS 'Optional FK-style entity id (not enforced by DB).';
COMMENT ON COLUMN file_assets.s3_key          IS 'Full S3 object key used for delete/presign.';
