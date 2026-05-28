-- Pimienta Alimentos — initial PostgreSQL schema (synced with JPA entities)

-- ---------------------------------------------------------------------------
-- Headquarters
-- ---------------------------------------------------------------------------
CREATE TABLE headquarters (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(512),
    address     VARCHAR(1024),
    description VARCHAR(4096),
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL,
    deleted_at  TIMESTAMP,
    version     BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_headquarters_name ON headquarters (name);
CREATE INDEX idx_headquarters_deleted_at ON headquarters (deleted_at);

COMMENT ON TABLE headquarters IS 'Physical branches / offices used by attendance and tasks.';

-- ---------------------------------------------------------------------------
-- Account users
-- ---------------------------------------------------------------------------
CREATE TABLE account_users (
    id             BIGSERIAL PRIMARY KEY,
    email          VARCHAR(320) NOT NULL,
    password_hash  VARCHAR(200) NOT NULL,
    first_name     VARCHAR(120) NOT NULL,
    last_name      VARCHAR(120) NOT NULL,
    gender         VARCHAR(32)  NOT NULL,
    phone          VARCHAR(32)  NOT NULL,
    date_of_birth  DATE         NOT NULL,
    account_status VARCHAR(32)  NOT NULL,
    banned_reason  VARCHAR(500),
    banned_at      TIMESTAMP,
    created_at     TIMESTAMP    NOT NULL,
    updated_at     TIMESTAMP    NOT NULL,
    deleted_at     TIMESTAMP,
    version        BIGINT       NOT NULL DEFAULT 0,
    CONSTRAINT uk_account_users_email UNIQUE (email)
);

CREATE INDEX idx_account_users_phone ON account_users (phone);
CREATE INDEX idx_account_users_deleted_at ON account_users (deleted_at);
CREATE INDEX idx_account_users_account_status ON account_users (account_status);

COMMENT ON TABLE account_users IS 'Application login accounts (distinct from HR employee records).';

CREATE TABLE account_user_roles (
    user_id BIGINT      NOT NULL REFERENCES account_users (id) ON DELETE CASCADE,
    role    VARCHAR(32) NOT NULL,
    PRIMARY KEY (user_id, role)
);

CREATE INDEX idx_account_user_roles_user_id ON account_user_roles (user_id);

COMMENT ON TABLE account_user_roles IS 'Role assignments for account_users (ElementCollection).';

-- ---------------------------------------------------------------------------
-- Employees
-- ---------------------------------------------------------------------------
CREATE TABLE employees (
    id                              BIGSERIAL PRIMARY KEY,
    personal_first_name             VARCHAR(100),
    personal_last_name              VARCHAR(100),
    personal_photo_url              VARCHAR(255),
    personal_email                  VARCHAR(320),
    personal_phone                  VARCHAR(40),
    personal_address                VARCHAR(500),
    birth_date                      DATE,
    personal_nationality            VARCHAR(80),
    official_ids_curp               VARCHAR(18),
    official_ids_rfc                VARCHAR(13),
    official_ids_nss                VARCHAR(11),
    official_ids_clabe              VARCHAR(18),
    employee_number                 VARCHAR(32),
    employment_position             VARCHAR(120),
    employment_department           VARCHAR(120),
    contract_type                   VARCHAR(32),
    work_shift                      VARCHAR(32),
    hire_date                       DATE,
    termination_date                DATE,
    salary_per_week                 NUMERIC(19, 6),
    compensation_bonuses            NUMERIC(19, 6),
    food_vouchers                   NUMERIC(19, 6),
    integration_factor              NUMERIC(19, 6),
    imss_worker_type                VARCHAR(32),
    imss_salary_type                VARCHAR(32),
    christmas_bonus_days            INTEGER      NOT NULL DEFAULT 0,
    vacation_days                   INTEGER      NOT NULL DEFAULT 0,
    vacation_premium_percent        NUMERIC(19, 6),
    status                          VARCHAR(32),
    work_schedule                   JSONB,
    created_at                      TIMESTAMP    NOT NULL,
    updated_at                      TIMESTAMP    NOT NULL,
    deleted_at                      TIMESTAMP,
    version                         BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_employees_status ON employees (status);
CREATE INDEX idx_employees_deleted_at ON employees (deleted_at);
CREATE INDEX idx_employees_personal_email ON employees (personal_email);
CREATE INDEX idx_employees_employee_number ON employees (employee_number);
CREATE INDEX idx_employees_employment_department ON employees (employment_department);

COMMENT ON TABLE employees IS 'HR employee master data with embedded personal, employment, and payroll fields.';

-- ---------------------------------------------------------------------------
-- Employee attendances
-- ---------------------------------------------------------------------------
CREATE TABLE employee_attendances (
    id                           BIGSERIAL PRIMARY KEY,
    employee_id                  BIGINT       NOT NULL REFERENCES employees (id),
    headquarter_id               BIGINT       NOT NULL REFERENCES headquarters (id),
    work_date                    DATE         NOT NULL,
    check_in_time                TIMESTAMP,
    check_out_time               TIMESTAMP,
    status                       VARCHAR(48)  NOT NULL,
    check_in_evidence_photo_url  VARCHAR(2000),
    check_out_evidence_photo_url VARCHAR(2000),
    created_at                   TIMESTAMP    NOT NULL,
    updated_at                   TIMESTAMP    NOT NULL,
    deleted_at                   TIMESTAMP,
    version                      BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_employee_attendances_employee_work_date ON employee_attendances (employee_id, work_date);
CREATE INDEX idx_employee_attendances_headquarter_work_date ON employee_attendances (headquarter_id, work_date);
CREATE INDEX idx_employee_attendances_work_date ON employee_attendances (work_date);
CREATE INDEX idx_employee_attendances_deleted_at ON employee_attendances (deleted_at);

COMMENT ON TABLE employee_attendances IS 'Daily check-in/out records per employee and headquarter.';

-- ---------------------------------------------------------------------------
-- CRM — opportunities
-- ---------------------------------------------------------------------------
CREATE TABLE crm_opportunities (
    id                    BIGSERIAL PRIMARY KEY,
    contact_name          VARCHAR(255),
    contact_email         VARCHAR(320),
    contact_phone         VARCHAR(64),
    company_name          VARCHAR(255),
    company_location      VARCHAR(255),
    industry              VARCHAR(255),
    title                 VARCHAR(500),
    description           VARCHAR(4000),
    estimated_value       NUMERIC(19, 4),
    probability_percent   INTEGER      NOT NULL DEFAULT 0,
    source                VARCHAR(32)  NOT NULL,
    status                VARCHAR(32)  NOT NULL,
    expected_close_date   DATE,
    actual_close_date     DATE,
    assigned_salesman_id  BIGINT,
    lost_reason           VARCHAR(2000),
    converted_project_id  BIGINT,
    created_at            TIMESTAMP    NOT NULL,
    updated_at            TIMESTAMP    NOT NULL,
    deleted_at            TIMESTAMP,
    version               BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_crm_opportunities_status ON crm_opportunities (status);
CREATE INDEX idx_crm_opportunities_assigned_salesman_id ON crm_opportunities (assigned_salesman_id);
CREATE INDEX idx_crm_opportunities_expected_close_date ON crm_opportunities (expected_close_date);
CREATE INDEX idx_crm_opportunities_deleted_at ON crm_opportunities (deleted_at);

COMMENT ON TABLE crm_opportunities IS 'Sales pipeline opportunities before project conversion.';

-- ---------------------------------------------------------------------------
-- CRM — projects
-- ---------------------------------------------------------------------------
CREATE TABLE crm_projects (
    id                    BIGSERIAL PRIMARY KEY,
    client_id             BIGINT,
    origin_opportunity_id BIGINT REFERENCES crm_opportunities (id) ON DELETE SET NULL,
    project_code          VARCHAR(64)  NOT NULL,
    project_name          VARCHAR(500) NOT NULL,
    description           VARCHAR(4000),
    type                  VARCHAR(32)  NOT NULL,
    status                VARCHAR(32)  NOT NULL,
    priority              VARCHAR(32)  NOT NULL,
    project_manager_id    BIGINT,
    assigned_salesman_id  BIGINT,
    planned_start_date    DATE,
    planned_end_date      DATE,
    actual_start_date     DATE,
    actual_end_date       DATE,
    on_hold_reason        VARCHAR(2000),
    contracted_value      NUMERIC(19, 4),
    estimated_cost        NUMERIC(19, 4),
    actual_cost           NUMERIC(19, 4),
    progress_percent      INTEGER      NOT NULL DEFAULT 0,
    cancellation_reason   VARCHAR(2000),
    created_at            TIMESTAMP    NOT NULL,
    updated_at            TIMESTAMP    NOT NULL,
    deleted_at            TIMESTAMP,
    version               BIGINT       NOT NULL DEFAULT 0,
    CONSTRAINT uk_crm_projects_project_code UNIQUE (project_code)
);

CREATE INDEX idx_crm_projects_status ON crm_projects (status);
CREATE INDEX idx_crm_projects_client_id ON crm_projects (client_id);
CREATE INDEX idx_crm_projects_origin_opportunity_id ON crm_projects (origin_opportunity_id);
CREATE INDEX idx_crm_projects_deleted_at ON crm_projects (deleted_at);

COMMENT ON TABLE crm_projects IS 'Active or historical client projects linked to opportunities.';

-- ---------------------------------------------------------------------------
-- CRM — project milestones
-- ---------------------------------------------------------------------------
CREATE TABLE crm_project_milestones (
    id              BIGSERIAL PRIMARY KEY,
    project_id      BIGINT       NOT NULL REFERENCES crm_projects (id) ON DELETE CASCADE,
    name            VARCHAR(500) NOT NULL,
    description     VARCHAR(4000),
    status          VARCHAR(32)  NOT NULL,
    planned_date    DATE,
    actual_date     DATE,
    billing_amount  NUMERIC(19, 4),
    billed          BOOLEAN      NOT NULL DEFAULT FALSE,
    sort_order      INTEGER      NOT NULL DEFAULT 0,
    created_at      TIMESTAMP    NOT NULL,
    updated_at      TIMESTAMP    NOT NULL,
    deleted_at      TIMESTAMP,
    version         BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_crm_project_milestones_project_sort ON crm_project_milestones (project_id, sort_order);
CREATE INDEX idx_crm_project_milestones_deleted_at ON crm_project_milestones (deleted_at);

COMMENT ON TABLE crm_project_milestones IS 'Ordered billing/delivery milestones within a project.';

-- ---------------------------------------------------------------------------
-- Business contracts
-- ---------------------------------------------------------------------------
CREATE TABLE business_contracts (
    id                     BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(400),
    description            VARCHAR(4000),
    category               VARCHAR(32),
    employee_id            BIGINT REFERENCES employees (id) ON DELETE SET NULL,
    opportunity_id         BIGINT REFERENCES crm_opportunities (id) ON DELETE SET NULL,
    project_id             BIGINT REFERENCES crm_projects (id) ON DELETE SET NULL,
    term_kind              VARCHAR(32),
    effective_start        DATE,
    effective_end          DATE,
    document_url           VARCHAR(2000),
    terms_and_conditions   VARCHAR(8000),
    reference_code         VARCHAR(80),
    renewal_cycle_months   INTEGER,
    agreed_value           NUMERIC(19, 4),
    currency_code          VARCHAR(3),
    extension_count        INTEGER      NOT NULL DEFAULT 0,
    last_renewed_at        TIMESTAMP,
    created_at             TIMESTAMP    NOT NULL,
    updated_at             TIMESTAMP    NOT NULL,
    deleted_at             TIMESTAMP,
    version                BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_business_contracts_employee_id ON business_contracts (employee_id);
CREATE INDEX idx_business_contracts_opportunity_id ON business_contracts (opportunity_id);
CREATE INDEX idx_business_contracts_project_id ON business_contracts (project_id);
CREATE INDEX idx_business_contracts_reference_code ON business_contracts (reference_code);
CREATE INDEX idx_business_contracts_deleted_at ON business_contracts (deleted_at);

COMMENT ON TABLE business_contracts IS 'Legal/business agreements tied to employees, opportunities, or projects.';

-- ---------------------------------------------------------------------------
-- Tasks
-- ---------------------------------------------------------------------------
CREATE TABLE tasks (
    id              BIGSERIAL PRIMARY KEY,
    title           VARCHAR(300),
    description     VARCHAR(4000),
    status          VARCHAR(32),
    priority        VARCHAR(32),
    assigned_to_id  BIGINT,
    assigned_by_id  BIGINT,
    created_by_id   BIGINT,
    assigned_at     TIMESTAMP,
    completed_at    TIMESTAMP,
    due_date        TIMESTAMP,
    headquarter_id  BIGINT REFERENCES headquarters (id) ON DELETE SET NULL,
    project_id      BIGINT REFERENCES crm_projects (id) ON DELETE SET NULL,
    opportunity_id  BIGINT REFERENCES crm_opportunities (id) ON DELETE SET NULL,
    checklist       JSONB,
    created_at      TIMESTAMP    NOT NULL,
    updated_at      TIMESTAMP    NOT NULL,
    deleted_at      TIMESTAMP,
    version         BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_tasks_assigned_to_id ON tasks (assigned_to_id);
CREATE INDEX idx_tasks_status ON tasks (status);
CREATE INDEX idx_tasks_project_id ON tasks (project_id);
CREATE INDEX idx_tasks_opportunity_id ON tasks (opportunity_id);
CREATE INDEX idx_tasks_headquarter_id ON tasks (headquarter_id);
CREATE INDEX idx_tasks_due_date ON tasks (due_date);
CREATE INDEX idx_tasks_deleted_at ON tasks (deleted_at);

COMMENT ON TABLE tasks IS 'Operational tasks optionally linked to projects, opportunities, or headquarters.';

-- ---------------------------------------------------------------------------
-- Inventory — items
-- ---------------------------------------------------------------------------
CREATE TABLE inventory_items (
    id               BIGSERIAL PRIMARY KEY,
    sku              VARCHAR(64)  NOT NULL,
    name             VARCHAR(300) NOT NULL,
    description      VARCHAR(4000),
    category         VARCHAR(32)  NOT NULL,
    unit             VARCHAR(32)  NOT NULL,
    brand            VARCHAR(120),
    barcode          VARCHAR(64),
    cost_price       NUMERIC(19, 6) NOT NULL DEFAULT 0,
    sale_price       NUMERIC(19, 6) NOT NULL DEFAULT 0,
    reorder_point    INTEGER      NOT NULL DEFAULT 0,
    reorder_quantity INTEGER      NOT NULL DEFAULT 0,
    status           VARCHAR(32)  NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    updated_at       TIMESTAMP    NOT NULL,
    deleted_at       TIMESTAMP,
    version          BIGINT       NOT NULL DEFAULT 0,
    CONSTRAINT uk_inventory_items_sku UNIQUE (sku)
);

CREATE INDEX idx_inventory_items_barcode ON inventory_items (barcode);
CREATE INDEX idx_inventory_items_status ON inventory_items (status);
CREATE INDEX idx_inventory_items_deleted_at ON inventory_items (deleted_at);

COMMENT ON TABLE inventory_items IS 'Catalog of stock-keeping units (SKU) and sale metadata.';

-- ---------------------------------------------------------------------------
-- Inventory — storage locations
-- ---------------------------------------------------------------------------
CREATE TABLE storage_locations (
    id                BIGSERIAL PRIMARY KEY,
    code              VARCHAR(64)  NOT NULL,
    name              VARCHAR(200) NOT NULL,
    description       VARCHAR(2000),
    type              VARCHAR(32)  NOT NULL,
    parent_id         BIGINT REFERENCES storage_locations (id) ON DELETE SET NULL,
    max_capacity      INTEGER      NOT NULL DEFAULT 0,
    occupied_capacity INTEGER      NOT NULL DEFAULT 0,
    status            VARCHAR(32)  NOT NULL,
    created_at        TIMESTAMP    NOT NULL,
    updated_at        TIMESTAMP    NOT NULL,
    deleted_at        TIMESTAMP,
    version           BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_storage_locations_code ON storage_locations (code);
CREATE INDEX idx_storage_locations_parent_id ON storage_locations (parent_id);
CREATE INDEX idx_storage_locations_deleted_at ON storage_locations (deleted_at);

COMMENT ON TABLE storage_locations IS 'Warehouses, zones, and bins that hold inventory stock.';

-- ---------------------------------------------------------------------------
-- Inventory — stock levels
-- ---------------------------------------------------------------------------
CREATE TABLE inventory_stock (
    id                  BIGSERIAL PRIMARY KEY,
    item_id             BIGINT       NOT NULL REFERENCES inventory_items (id),
    location_id         BIGINT       NOT NULL REFERENCES storage_locations (id),
    available_quantity  INTEGER      NOT NULL DEFAULT 0,
    reserved_quantity   INTEGER      NOT NULL DEFAULT 0,
    in_transit_quantity INTEGER      NOT NULL DEFAULT 0,
    status              VARCHAR(32)  NOT NULL,
    created_at          TIMESTAMP    NOT NULL,
    updated_at          TIMESTAMP    NOT NULL,
    deleted_at          TIMESTAMP,
    version             BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_inventory_stock_item_location ON inventory_stock (item_id, location_id);
CREATE INDEX idx_inventory_stock_item_id ON inventory_stock (item_id);
CREATE INDEX idx_inventory_stock_location_id ON inventory_stock (location_id);
CREATE INDEX idx_inventory_stock_deleted_at ON inventory_stock (deleted_at);

CREATE UNIQUE INDEX uk_inventory_stock_item_location_active
    ON inventory_stock (item_id, location_id)
    WHERE deleted_at IS NULL;

COMMENT ON TABLE inventory_stock IS 'Quantity on hand per item and storage location.';

-- ---------------------------------------------------------------------------
-- Inventory — transactions
-- ---------------------------------------------------------------------------
CREATE TABLE inventory_transactions (
    id                  BIGSERIAL PRIMARY KEY,
    transaction_number  VARCHAR(64)  NOT NULL,
    type                VARCHAR(40)  NOT NULL,
    status              VARCHAR(32)  NOT NULL,
    external_reference  VARCHAR(120),
    notes               VARCHAR(4000),
    initiated_by_id     BIGINT,
    approved_by_id      BIGINT,
    approved_at         TIMESTAMP,
    completed_at        TIMESTAMP,
    created_at          TIMESTAMP    NOT NULL,
    updated_at          TIMESTAMP    NOT NULL,
    deleted_at          TIMESTAMP,
    version             BIGINT       NOT NULL DEFAULT 0,
    CONSTRAINT uk_inventory_transactions_transaction_number UNIQUE (transaction_number)
);

CREATE INDEX idx_inventory_transactions_status ON inventory_transactions (status);
CREATE INDEX idx_inventory_transactions_type ON inventory_transactions (type);
CREATE INDEX idx_inventory_transactions_deleted_at ON inventory_transactions (deleted_at);

COMMENT ON TABLE inventory_transactions IS 'Grouped inventory operations (receipts, transfers, adjustments).';

-- ---------------------------------------------------------------------------
-- Inventory — movements (immutable audit trail)
-- ---------------------------------------------------------------------------
CREATE TABLE inventory_movements (
    id                      BIGSERIAL PRIMARY KEY,
    item_id                 BIGINT         NOT NULL REFERENCES inventory_items (id),
    source_location_id      BIGINT REFERENCES storage_locations (id) ON DELETE SET NULL,
    destination_location_id BIGINT REFERENCES storage_locations (id) ON DELETE SET NULL,
    transaction_id          BIGINT REFERENCES inventory_transactions (id) ON DELETE SET NULL,
    quantity                INTEGER        NOT NULL,
    unit_cost               NUMERIC(19, 6) NOT NULL DEFAULT 0,
    type                    VARCHAR(32)    NOT NULL,
    direction               VARCHAR(32)    NOT NULL,
    description             VARCHAR(2000),
    reference_number        VARCHAR(120),
    performed_by_id         BIGINT,
    stock_after_movement    INTEGER        NOT NULL DEFAULT 0,
    created_at              TIMESTAMP      NOT NULL,
    updated_at              TIMESTAMP      NOT NULL,
    version                 BIGINT         NOT NULL DEFAULT 0
);

CREATE INDEX idx_inventory_movements_item_id ON inventory_movements (item_id);
CREATE INDEX idx_inventory_movements_transaction_id ON inventory_movements (transaction_id);
CREATE INDEX idx_inventory_movements_reference_number ON inventory_movements (reference_number);
CREATE INDEX idx_inventory_movements_source_location_id ON inventory_movements (source_location_id);
CREATE INDEX idx_inventory_movements_destination_location_id ON inventory_movements (destination_location_id);
CREATE INDEX idx_inventory_movements_created_at ON inventory_movements (created_at);

COMMENT ON TABLE inventory_movements IS 'Append-only ledger of quantity changes per item and location.';

-- ---------------------------------------------------------------------------
-- Payroll
-- ---------------------------------------------------------------------------
CREATE TABLE payroll_periods (
    id         BIGSERIAL PRIMARY KEY,
    frequency  VARCHAR(32) NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NOT NULL,
    status     VARCHAR(32) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL,
    deleted_at TIMESTAMP,
    version    BIGINT      NOT NULL DEFAULT 0
);

CREATE INDEX idx_payroll_periods_dates ON payroll_periods (start_date, end_date);
CREATE INDEX idx_payroll_periods_status ON payroll_periods (status);
CREATE INDEX idx_payroll_periods_deleted_at ON payroll_periods (deleted_at);

COMMENT ON TABLE payroll_periods IS 'Payroll calculation windows (weekly, bi-weekly, etc.).';

CREATE TABLE payroll_records (
    id                BIGSERIAL PRIMARY KEY,
    employee_id       BIGINT         NOT NULL REFERENCES employees (id),
    period_id         BIGINT REFERENCES payroll_periods (id) ON DELETE SET NULL,
    worked_days_start DATE           NOT NULL,
    worked_days_end   DATE           NOT NULL,
    gross_amount      NUMERIC(19, 4) NOT NULL DEFAULT 0,
    total_discounts   NUMERIC(19, 4) NOT NULL DEFAULT 0,
    total_bonuses     NUMERIC(19, 4) NOT NULL DEFAULT 0,
    net_amount        NUMERIC(19, 4) NOT NULL DEFAULT 0,
    status            VARCHAR(32)    NOT NULL,
    created_at        TIMESTAMP      NOT NULL,
    updated_at        TIMESTAMP      NOT NULL,
    deleted_at        TIMESTAMP,
    version           BIGINT         NOT NULL DEFAULT 0
);

CREATE INDEX idx_payroll_records_employee_id ON payroll_records (employee_id);
CREATE INDEX idx_payroll_records_period_id ON payroll_records (period_id);
CREATE INDEX idx_payroll_records_status ON payroll_records (status);
CREATE INDEX idx_payroll_records_deleted_at ON payroll_records (deleted_at);

COMMENT ON TABLE payroll_records IS 'Calculated payroll run per employee and period.';

CREATE TABLE payroll_adjustments (
    id                BIGSERIAL PRIMARY KEY,
    payroll_record_id BIGINT         NOT NULL REFERENCES payroll_records (id) ON DELETE CASCADE,
    type              VARCHAR(16)    NOT NULL,
    amount            NUMERIC(19, 4) NOT NULL DEFAULT 0,
    reason            VARCHAR(500)   NOT NULL,
    created_at        TIMESTAMP      NOT NULL,
    updated_at        TIMESTAMP      NOT NULL,
    deleted_at        TIMESTAMP,
    version           BIGINT         NOT NULL DEFAULT 0
);

CREATE INDEX idx_payroll_adjustments_payroll_record_id ON payroll_adjustments (payroll_record_id);
CREATE INDEX idx_payroll_adjustments_deleted_at ON payroll_adjustments (deleted_at);

COMMENT ON TABLE payroll_adjustments IS 'Bonuses and deductions applied to a payroll record.';

CREATE TABLE payroll_payments (
    id                  BIGSERIAL PRIMARY KEY,
    payroll_record_id   BIGINT REFERENCES payroll_records (id) ON DELETE SET NULL,
    employee_id         BIGINT         NOT NULL REFERENCES employees (id),
    frequency           VARCHAR(32)    NOT NULL,
    worked_days_start   DATE           NOT NULL,
    worked_days_end     DATE           NOT NULL,
    gross_amount        NUMERIC(19, 4) NOT NULL DEFAULT 0,
    net_amount          NUMERIC(19, 4) NOT NULL DEFAULT 0,
    destination_account VARCHAR(128)   NOT NULL,
    transaction_id      VARCHAR(128)   NOT NULL,
    status              VARCHAR(32)    NOT NULL,
    pending_amount      NUMERIC(19, 4) NOT NULL DEFAULT 0,
    created_at          TIMESTAMP      NOT NULL,
    updated_at          TIMESTAMP      NOT NULL,
    deleted_at          TIMESTAMP,
    version             BIGINT         NOT NULL DEFAULT 0
);

CREATE INDEX idx_payroll_payments_payroll_record_id ON payroll_payments (payroll_record_id);
CREATE INDEX idx_payroll_payments_employee_id ON payroll_payments (employee_id);
CREATE INDEX idx_payroll_payments_status ON payroll_payments (status);
CREATE INDEX idx_payroll_payments_deleted_at ON payroll_payments (deleted_at);

COMMENT ON TABLE payroll_payments IS 'Bank disbursements linked to payroll records.';

CREATE TABLE payroll_debts (
    id                BIGSERIAL PRIMARY KEY,
    employee_id       BIGINT         NOT NULL REFERENCES employees (id),
    payroll_record_id BIGINT REFERENCES payroll_records (id) ON DELETE SET NULL,
    amount_owed       NUMERIC(19, 4) NOT NULL DEFAULT 0,
    reason            VARCHAR(500),
    settled           BOOLEAN        NOT NULL DEFAULT FALSE,
    settled_at        TIMESTAMP,
    created_at        TIMESTAMP      NOT NULL,
    updated_at        TIMESTAMP      NOT NULL,
    deleted_at        TIMESTAMP,
    version           BIGINT         NOT NULL DEFAULT 0
);

CREATE INDEX idx_payroll_debts_employee_id ON payroll_debts (employee_id);
CREATE INDEX idx_payroll_debts_settled ON payroll_debts (settled);
CREATE INDEX idx_payroll_debts_deleted_at ON payroll_debts (deleted_at);

COMMENT ON TABLE payroll_debts IS 'Outstanding amounts owed by employees to the company.';

-- ---------------------------------------------------------------------------
-- Polymorphic comments (tasks, projects, opportunities, contracts, etc.)
-- ---------------------------------------------------------------------------
CREATE TABLE entity_comments (
    id          BIGSERIAL PRIMARY KEY,
    target_type VARCHAR(32)  NOT NULL,
    target_id   BIGINT       NOT NULL,
    author_id   BIGINT       NOT NULL REFERENCES account_users (id),
    body        VARCHAR(4000) NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL,
    deleted_at  TIMESTAMP,
    version     BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_entity_comments_target ON entity_comments (target_type, target_id);
CREATE INDEX idx_entity_comments_author_id ON entity_comments (author_id);
CREATE INDEX idx_entity_comments_deleted_at ON entity_comments (deleted_at);

COMMENT ON TABLE entity_comments IS 'User-authored notes attached to any supported business entity.';
