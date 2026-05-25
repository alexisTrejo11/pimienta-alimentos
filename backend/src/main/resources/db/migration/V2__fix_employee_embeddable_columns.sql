-- Align employees embeddable columns with Hibernate 7 naming:
-- explicit @Column(name) on @Embeddable fields are NOT prefixed; implicit fields are.

ALTER TABLE employees RENAME COLUMN personal_birth_date TO birth_date;
ALTER TABLE employees RENAME COLUMN official_ids_employee_number TO employee_number;
ALTER TABLE employees RENAME COLUMN employment_contract_type TO contract_type;
ALTER TABLE employees RENAME COLUMN employment_work_shift TO work_shift;
ALTER TABLE employees RENAME COLUMN employment_hire_date TO hire_date;
ALTER TABLE employees RENAME COLUMN employment_termination_date TO termination_date;
ALTER TABLE employees RENAME COLUMN compensation_salary_per_week TO salary_per_week;
ALTER TABLE employees RENAME COLUMN compensation_food_vouchers TO food_vouchers;
ALTER TABLE employees RENAME COLUMN benefits_integration_factor TO integration_factor;
ALTER TABLE employees RENAME COLUMN benefits_imss_worker_type TO imss_worker_type;
ALTER TABLE employees RENAME COLUMN benefits_imss_salary_type TO imss_salary_type;
ALTER TABLE employees RENAME COLUMN benefits_christmas_bonus_days TO christmas_bonus_days;
ALTER TABLE employees RENAME COLUMN benefits_vacation_days TO vacation_days;
ALTER TABLE employees RENAME COLUMN benefits_vacation_premium_percent TO vacation_premium_percent;

DROP INDEX IF EXISTS idx_employees_official_ids_employee_number;
CREATE INDEX idx_employees_employee_number ON employees (employee_number);
