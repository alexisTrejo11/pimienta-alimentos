-- Pimienta Alimentos — local/dev seed data (password for all users: "password")
-- BCrypt: $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW

-- headquarters (12)
INSERT INTO headquarters (id, name, address, description, created_at, updated_at, version) VALUES
(1,  'Sede Central CDMX',       'Av. Insurgentes 100, CDMX',           'Oficina principal',              '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(2,  'Planta Norte',            'Carretera 57 Km 12, Saltillo',        'Producción norte',               '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(3,  'Bodega Guadalajara',      'Zona industrial GDL',                 'Almacén occidente',              '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(4,  'Centro de Distribución',  'Querétaro Logístico',                 'Hub nacional',                   '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(5,  'Sucursal Monterrey',      'San Pedro, NL',                       'Ventas región norte',            '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(6,  'Laboratorio Calidad',     'CDMX Polanco',                        'Control de calidad',             '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(7,  'Punto Venta Puebla',      'Angelópolis, Puebla',                 'Retail local',                   '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(8,  'Planta Sur',              'Mérida, Yucatán',                     'Producción sureste',             '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(9,  'Oficina León',            'Boulevard López Mateos',              'Operaciones Bajío',              '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(10, 'Centro Capacitación',     'Toluca',                              'Capacitación empleados',         '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(11, 'Bodega Tijuana',          'Parque Industrial Tijuana',           'Exportación',                    '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1),
(12, 'Sucursal Cancún',         'Zona Hotelera',                       'Ventas turismo',                 '2025-01-01 08:00:00', '2025-01-01 08:00:00', 1);
SELECT setval(pg_get_serial_sequence('headquarters', 'id'), 12);

-- account_users (12) + roles
INSERT INTO account_users (id, email, password_hash, first_name, last_name, gender, phone, date_of_birth, account_status, created_at, updated_at, version) VALUES
(1,  'admin@pimienta.local',     '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Ana',      'Admin',    'FEMALE',              '+525511110001', '1985-03-15', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(2,  'manager@pimienta.local',   '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Carlos',   'Manager',  'MALE',                '+525511110002', '1990-07-22', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(3,  'support@pimienta.local',   '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Lucía',    'Support',  'FEMALE',              '+525511110003', '1992-11-08', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(4,  'user01@pimienta.local',    '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Diego',    'López',    'MALE',                '+525511110004', '1995-01-30', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(5,  'user02@pimienta.local',    '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Elena',    'Ruiz',     'FEMALE',              '+525511110005', '1993-05-12', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(6,  'user03@pimienta.local',    '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Fernando', 'García',   'MALE',                '+525511110006', '1988-09-03', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(7,  'user04@pimienta.local',    '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Gabriela', 'Martín',   'FEMALE',              '+525511110007', '1991-12-25', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(8,  'user05@pimienta.local',    '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Héctor',   'Sánchez',  'MALE',                '+525511110008', '1994-04-18', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(9,  'user06@pimienta.local',    '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Isabel',   'Torres',   'FEMALE',              '+525511110009', '1996-08-07', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(10, 'user07@pimienta.local',    '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Jorge',    'Herrera',  'MALE',                '+525511110010', '1987-06-14', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(11, 'pending@pimienta.local',   '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Karla',    'Nuevo',    'FEMALE',              '+525511110011', '1998-02-28', 'PENDING_APPROVAL', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1),
(12, 'sales@pimienta.local',     '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Luis',     'Ventas',   'MALE',                '+525511110012', '1989-10-10', 'ACTIVE', '2025-01-02 09:00:00', '2025-01-02 09:00:00', 1);
SELECT setval(pg_get_serial_sequence('account_users', 'id'), 12);

INSERT INTO account_user_roles (user_id, role) VALUES
(1, 'ADMIN'), (1, 'MANAGER'),
(2, 'MANAGER'), (2, 'USER'),
(3, 'SUPPORT'), (3, 'USER'),
(4, 'USER'), (5, 'USER'), (6, 'USER'), (7, 'USER'), (8, 'USER'),
(9, 'USER'), (10, 'USER'), (11, 'USER'), (12, 'USER'), (12, 'MANAGER');

-- employees (12)
INSERT INTO employees (id, first_name, last_name, email, phone, employee_number, position, department, contract_type, work_shift, hire_date, salary_per_week, status, christmas_bonus_days, vacation_days, created_at, updated_at, version) VALUES
(1,  'María',    'González',  'maria.g@pimienta.local',   '+525520000001', 'EMP-001', 'Operador producción', 'Producción',  'INDEFINITE', 'MORNING',   '2020-03-01', 2500.00, 'ACTIVE',       15, 12, '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(2,  'Pedro',    'Ramírez',   'pedro.r@pimienta.local',   '+525520000002', 'EMP-002', 'Supervisor',          'Producción',  'INDEFINITE', 'MORNING',   '2019-06-15', 3200.00, 'ACTIVE',       15, 14, '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(3,  'Rosa',     'Flores',    'rosa.f@pimienta.local',    '+525520000003', 'EMP-003', 'Almacenista',         'Logística',   'INDEFINITE', 'AFTERNOON', '2021-01-10', 2200.00, 'ACTIVE',       15, 10, '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(4,  'Miguel',   'Castro',    'miguel.c@pimienta.local',  '+525520000004', 'EMP-004', 'Chofer',              'Logística',   'FIXED_TERM', 'MORNING',   '2023-02-20', 2100.00, 'ACTIVE',       15, 6,  '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(5,  'Patricia', 'Mendoza',   'patricia.m@pimienta.local','+525520000005', 'EMP-005', 'Contador',            'Finanzas',    'INDEFINITE', 'MORNING',   '2018-09-01', 4500.00, 'ACTIVE',       15, 16, '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(6,  'Roberto',  'Vega',      'roberto.v@pimienta.local', '+525520000006', 'EMP-006', 'RRHH',                'Recursos Humanos', 'INDEFINITE', 'MORNING', '2017-04-12', 3800.00, 'ACTIVE', 15, 18, '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(7,  'Sandra',   'Ortiz',     'sandra.o@pimienta.local',  '+525520000007', 'EMP-007', 'Calidad',             'Calidad',     'INDEFINITE', 'MORNING',   '2022-07-05', 3000.00, 'ACTIVE',       15, 8,  '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(8,  'Andrés',   'Paredes',   'andres.p@pimienta.local',  '+525520000008', 'EMP-008', 'Ventas',              'Comercial',   'INDEFINITE', 'MIXED',     '2021-11-20', 2800.00, 'ACTIVE',       15, 10, '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(9,  'Claudia',  'Ríos',      'claudia.r@pimienta.local', '+525520000009', 'EMP-009', 'Asistente admin',     'Administración', 'TEMPORARY', 'MORNING', '2024-01-08', 2000.00, 'ACTIVE',  15, 4,  '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(10, 'Tomás',    'Aguilar',   'tomas.a@pimienta.local',   '+525520000010', 'EMP-010', 'Mantenimiento',       'Planta',      'INDEFINITE', 'NIGHT',     '2019-12-01', 2600.00, 'ON_VACATION',  15, 12, '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(11, 'Verónica', 'Campos',    'veronica.c@pimienta.local','+525520000011', 'EMP-011', 'Chef producción',     'Producción',  'INDEFINITE', 'MORNING',   '2016-05-18', 3500.00, 'ACTIVE',       15, 20, '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1),
(12, 'Óscar',    'Delgado',   'oscar.d@pimienta.local',   '+525520000012', 'EMP-012', 'Practicante',         'Producción',  'PROJECT_BASED', 'MORNING', '2025-01-15', 1500.00, 'DRAFT',     15, 0,  '2025-01-03 08:00:00', '2025-01-03 08:00:00', 1);
SELECT setval(pg_get_serial_sequence('employees', 'id'), 12);

-- employee_attendances (12)
INSERT INTO employee_attendances (id, employee_id, headquarter_id, work_date, check_in_time, check_out_time, status, created_at, updated_at, version) VALUES
(1,  1, 2, '2025-05-01', '2025-05-01 07:55:00', '2025-05-01 16:05:00', 'CHECKED_OUT', '2025-05-01 07:55:00', '2025-05-01 16:05:00', 1),
(2,  2, 2, '2025-05-01', '2025-05-01 07:50:00', '2025-05-01 16:00:00', 'CHECKED_OUT', '2025-05-01 07:50:00', '2025-05-01 16:00:00', 1),
(3,  3, 3, '2025-05-01', '2025-05-01 13:00:00', '2025-05-01 21:00:00', 'CHECKED_OUT', '2025-05-01 13:00:00', '2025-05-01 21:00:00', 1),
(4,  4, 4, '2025-05-01', '2025-05-01 08:00:00', '2025-05-01 17:00:00', 'CHECKED_OUT', '2025-05-01 08:00:00', '2025-05-01 17:00:00', 1),
(5,  5, 1, '2025-05-01', '2025-05-01 09:00:00', '2025-05-01 18:00:00', 'CHECKED_OUT', '2025-05-01 09:00:00', '2025-05-01 18:00:00', 1),
(6,  6, 1, '2025-05-01', '2025-05-01 09:05:00', '2025-05-01 18:05:00', 'CHECKED_OUT', '2025-05-01 09:05:00', '2025-05-01 18:05:00', 1),
(7,  7, 6, '2025-05-01', '2025-05-01 08:30:00', '2025-05-01 16:30:00', 'CHECKED_OUT', '2025-05-01 08:30:00', '2025-05-01 16:30:00', 1),
(8,  8, 5, '2025-05-02', '2025-05-02 10:00:00', '2025-05-02 19:00:00', 'CHECKED_OUT', '2025-05-02 10:00:00', '2025-05-02 19:00:00', 1),
(9,  9, 1, '2025-05-02', '2025-05-02 09:00:00', NULL,                  'CHECKED_IN',  '2025-05-02 09:00:00', '2025-05-02 09:00:00', 1),
(10, 11, 2, '2025-05-02', '2025-05-02 07:45:00', '2025-05-02 16:15:00', 'CHECKED_OUT', '2025-05-02 07:45:00', '2025-05-02 16:15:00', 1),
(11, 1, 2, '2025-05-02', '2025-05-02 08:00:00', '2025-05-02 16:00:00', 'CHECKED_OUT', '2025-05-02 08:00:00', '2025-05-02 16:00:00', 1),
(12, 3, 3, '2025-05-02', '2025-05-02 14:00:00', NULL,                  'CHECKED_IN',  '2025-05-02 14:00:00', '2025-05-02 14:00:00', 1);
SELECT setval(pg_get_serial_sequence('employee_attendances', 'id'), 12);

-- crm_opportunities (12)
INSERT INTO crm_opportunities (id, contact_name, contact_email, company_name, title, estimated_value, probability_percent, source, status, expected_close_date, assigned_salesman_id, created_at, updated_at, version) VALUES
(1,  'Juan Cliente',    'juan@hotelchain.mx',      'Hotel Chain MX',     'Salsa premium mensual',        120000.00, 60, 'INBOUND',     'PROPOSAL',    '2025-06-30', 12, '2025-02-01 10:00:00', '2025-02-01 10:00:00', 1),
(2,  'Laura Compras',   'laura@supermercado.mx',   'Super Ahorro',       'Aderezo línea propia',         85000.00,  40, 'OUTBOUND',    'DISCOVERY',   '2025-07-15', 12, '2025-02-02 10:00:00', '2025-02-02 10:00:00', 1),
(3,  'Ricardo Food',    'ricardo@catering.mx',     'Catering Deluxe',    'Kit condimentos eventos',      45000.00,  75, 'REFERRAL',    'NEGOTIATION', '2025-05-20', 8,  '2025-02-03 10:00:00', '2025-02-03 10:00:00', 1),
(4,  'Mónica Rest',     'monica@bistro.mx',        'Bistro Centro',      'Chile en polvo bulk',          22000.00,  30, 'COLD_CALL',   'NEW',         '2025-08-01', 8,  '2025-02-04 10:00:00', '2025-02-04 10:00:00', 1),
(5,  'Pablo Export',    'pablo@exportusa.com',     'US Spice Importers', 'Exportación habanero',         200000.00, 50, 'EVENT',       'PROPOSAL',    '2025-09-30', 12, '2025-02-05 10:00:00', '2025-02-05 10:00:00', 1),
(6,  'Sofía Retail',    'sofia@tienda.mx',         'Tienda Gourmet',     'Display pimienta negra',       15000.00,  90, 'SOCIAL_MEDIA','WON',         '2025-04-01', 8,  '2025-02-06 10:00:00', '2025-02-06 10:00:00', 1),
(7,  'Ernesto Corp',    'ernesto@corpfood.mx',     'Corp Food Services', 'Contrato anual salsas',        350000.00, 25, 'INBOUND',     'DISCOVERY',   '2025-12-31', 12, '2025-02-07 10:00:00', '2025-02-07 10:00:00', 1),
(8,  'Natalia Cafe',    'natalia@cafe.mx',         'Café Artesanal',     'Mezcla especias café',         8000.00,   20, 'OTHER',       'NEW',         '2025-06-15', 8,  '2025-02-08 10:00:00', '2025-02-08 10:00:00', 1),
(9,  'Hugo Dist',       'hugo@distribuidora.mx',   'Distribuidora Norte','Distribución regional',        95000.00,  55, 'OUTBOUND',    'NEGOTIATION', '2025-07-01', 12, '2025-02-09 10:00:00', '2025-02-09 10:00:00', 1),
(10, 'Inés Perdida',    'ines@oldclient.mx',       'Old Client SA',      'Renovación contrato',          50000.00,  0,  'REFERRAL',    'LOST',        '2025-03-01', 12, '2025-02-10 10:00:00', '2025-02-10 10:00:00', 1),
(11, 'Arturo Abandon',  'arturo@ghost.mx',         'Ghost Co',           'Muestras sin respuesta',       5000.00,   5,  'COLD_CALL',   'ABANDONED',   '2025-04-15', 8,  '2025-02-11 10:00:00', '2025-02-11 10:00:00', 1),
(12, 'Beatriz Won',     'beatriz@won.mx',          'Won Foods',          'Línea picante institucional',  180000.00, 100,'INBOUND',     'WON',         '2025-03-15', 12, '2025-02-12 10:00:00', '2025-02-12 10:00:00', 1);
SELECT setval(pg_get_serial_sequence('crm_opportunities', 'id'), 12);

-- crm_projects (12)
INSERT INTO crm_projects (id, origin_opportunity_id, project_code, project_name, type, status, priority, project_manager_id, assigned_salesman_id, planned_start_date, planned_end_date, contracted_value, progress_percent, created_at, updated_at, version) VALUES
(1,  6,  'PRJ-2025-001', 'Display Gourmet Tienda',     'IMPLEMENTATION', 'ACTIVE',    'MEDIUM',   2, 8,  '2025-04-10', '2025-05-30', 15000.00,  40, '2025-03-01 11:00:00', '2025-03-01 11:00:00', 1),
(2,  12, 'PRJ-2025-002', 'Línea picante Won Foods',    'CONSULTING',     'ACTIVE',    'HIGH',     2, 12, '2025-03-20', '2025-08-31', 180000.00, 25, '2025-03-15 11:00:00', '2025-03-15 11:00:00', 1),
(3,  3,  'PRJ-2025-003', 'Kit Catering Deluxe',        'SOFTWARE_DEVELOPMENT', 'PLANNING', 'MEDIUM', 2, 8,  '2025-05-01', '2025-06-30', 45000.00,  10, '2025-03-20 11:00:00', '2025-03-20 11:00:00', 1),
(4,  NULL,'PRJ-2025-004', 'Mantenimiento planta IT',    'MAINTENANCE',    'ACTIVE',    'LOW',      1, 12, '2025-01-01', '2025-12-31', 60000.00,  55, '2025-01-15 11:00:00', '2025-01-15 11:00:00', 1),
(5,  NULL,'PRJ-2025-005', 'Capacitación HACCP',       'TRAINING',       'COMPLETED', 'MEDIUM',   2, 8,  '2024-09-01', '2024-11-30', 25000.00,  100,'2024-08-01 11:00:00', '2024-12-01 11:00:00', 1),
(6,  1,  'PRJ-2025-006', 'Salsa premium Hotel Chain',  'IMPLEMENTATION', 'PLANNING',  'CRITICAL', 2, 12, '2025-07-01', '2025-09-30', 120000.00, 5,  '2025-03-25 11:00:00', '2025-03-25 11:00:00', 1),
(7,  NULL,'PRJ-2025-007', 'Investigación nuevo SKU',  'RESEARCH',       'ACTIVE',    'HIGH',     1, 8,  '2025-02-01', '2025-07-31', 40000.00,  35, '2025-02-01 11:00:00', '2025-02-01 11:00:00', 1),
(8,  NULL,'PRJ-2025-008', 'App inventario móvil',     'SOFTWARE_DEVELOPMENT', 'ON_HOLD', 'HIGH', 2, 12, '2025-04-01', '2025-10-31', 95000.00,  15, '2025-04-01 11:00:00', '2025-04-01 11:00:00', 1),
(9,  NULL,'PRJ-2025-009', 'Export habanero fase 1',     'OTHER',          'ACTIVE',    'CRITICAL', 2, 12, '2025-05-15', '2025-11-30', 200000.00, 8,  '2025-05-01 11:00:00', '2025-05-01 11:00:00', 1),
(10, NULL,'PRJ-2024-010', 'Proyecto archivado demo',  'OTHER',          'ARCHIVED',  'LOW',      1, 8,  '2023-01-01', '2023-06-30', 10000.00,  100,'2023-01-01 11:00:00', '2023-07-01 11:00:00', 1),
(11, NULL,'PRJ-2025-011', 'Cancelado piloto',         'IMPLEMENTATION', 'CANCELLED', 'LOW',      2, 8,  '2025-02-01', '2025-04-30', 5000.00,   0,  '2025-02-01 11:00:00', '2025-02-15 11:00:00', 1),
(12, 2,  'PRJ-2025-012', 'Aderezo Super Ahorro',     'IMPLEMENTATION', 'ACTIVE',    'MEDIUM',   2, 12, '2025-06-01', '2025-08-15', 85000.00,  12, '2025-03-10 11:00:00', '2025-03-10 11:00:00', 1);
SELECT setval(pg_get_serial_sequence('crm_projects', 'id'), 12);
UPDATE crm_opportunities SET converted_project_id = 1 WHERE id = 6;
UPDATE crm_opportunities SET converted_project_id = 2 WHERE id = 12;

-- crm_project_milestones (12)
INSERT INTO crm_project_milestones (id, project_id, name, status, planned_date, billing_amount, billed, sort_order, created_at, updated_at, version) VALUES
(1,  1, 'Diseño display',           'COMPLETED',   '2025-04-20', 5000.00,  true,  1, '2025-03-01 12:00:00', '2025-04-20 12:00:00', 1),
(2,  1, 'Entrega materiales',       'IN_PROGRESS', '2025-05-15', 5000.00,  false, 2, '2025-03-01 12:00:00', '2025-03-01 12:00:00', 1),
(3,  1, 'Instalación punto venta',  'PENDING',     '2025-05-30', 5000.00,  false, 3, '2025-03-01 12:00:00', '2025-03-01 12:00:00', 1),
(4,  2, 'Kickoff',                  'COMPLETED',   '2025-03-25', 36000.00, true,  1, '2025-03-15 12:00:00', '2025-03-25 12:00:00', 1),
(5,  2, 'Desarrollo fórmula',       'IN_PROGRESS', '2025-06-01', 72000.00, false, 2, '2025-03-15 12:00:00', '2025-03-15 12:00:00', 1),
(6,  2, 'Producción piloto',        'PENDING',     '2025-08-15', 72000.00, false, 3, '2025-03-15 12:00:00', '2025-03-15 12:00:00', 1),
(7,  4, 'Q1 revisión',              'COMPLETED',   '2025-03-31', 15000.00, true,  1, '2025-01-15 12:00:00', '2025-03-31 12:00:00', 1),
(8,  4, 'Q2 revisión',              'PENDING',     '2025-06-30', 15000.00, false, 2, '2025-01-15 12:00:00', '2025-01-15 12:00:00', 1),
(9,  7, 'Pruebas laboratorio',      'IN_PROGRESS', '2025-05-01', 20000.00, false, 1, '2025-02-01 12:00:00', '2025-02-01 12:00:00', 1),
(10, 7, 'Informe final',            'PENDING',     '2025-07-31', 20000.00, false, 2, '2025-02-01 12:00:00', '2025-02-01 12:00:00', 1),
(11, 5, 'Capacitación completada',  'COMPLETED',   '2024-11-15', 25000.00, true,  1, '2024-08-01 12:00:00', '2024-11-15 12:00:00', 1),
(12, 9, 'Certificación export',     'DELAYED',     '2025-06-01', 50000.00, false, 1, '2025-05-01 12:00:00', '2025-05-01 12:00:00', 1);
SELECT setval(pg_get_serial_sequence('crm_project_milestones', 'id'), 12);

-- business_contracts (12)
INSERT INTO business_contracts (id, name, category, employee_id, opportunity_id, project_id, term_kind, effective_start, effective_end, reference_code, agreed_value, currency_code, created_at, updated_at, version) VALUES
(1,  'Contrato María González',     'EMPLOYEE', 1,  NULL, NULL, 'INDEFINITE',  '2020-03-01', NULL,          'CTR-EMP-001', NULL,     'MXN', '2025-01-05 10:00:00', '2025-01-05 10:00:00', 1),
(2,  'Contrato Pedro Ramírez',      'EMPLOYEE', 2,  NULL, NULL, 'INDEFINITE',  '2019-06-15', NULL,          'CTR-EMP-002', NULL,     'MXN', '2025-01-05 10:00:00', '2025-01-05 10:00:00', 1),
(3,  'Acuerdo Hotel Chain',         'CUSTOMER', NULL, 1,    6,    'FIXED_TERM',  '2025-07-01', '2025-09-30',  'CTR-CUS-001', 120000.00,'MXN', '2025-03-01 10:00:00', '2025-03-01 10:00:00', 1),
(4,  'Acuerdo Won Foods',           'CUSTOMER', NULL, 12,   2,    'FIXED_TERM',  '2025-03-20', '2025-08-31',  'CTR-CUS-002', 180000.00,'MXN', '2025-03-15 10:00:00', '2025-03-15 10:00:00', 1),
(5,  'Proveedor envases SA',        'SUPPLIER', NULL, NULL, NULL, 'INDEFINITE',  '2024-01-01', NULL,          'CTR-SUP-001', 50000.00, 'MXN', '2025-01-05 10:00:00', '2025-01-05 10:00:00', 1),
(6,  'Partner logística Norte',     'PARTNER',  NULL, NULL, NULL, 'FIXED_TERM',  '2025-01-01', '2025-12-31',  'CTR-PAR-001', 30000.00, 'MXN', '2025-01-05 10:00:00', '2025-01-05 10:00:00', 1),
(7,  'Contrato Verónica Campos',    'EMPLOYEE', 11, NULL, NULL, 'INDEFINITE',  '2016-05-18', NULL,          'CTR-EMP-011', NULL,     'MXN', '2025-01-05 10:00:00', '2025-01-05 10:00:00', 1),
(8,  'Oportunidad Catering',        'CUSTOMER', NULL, 3,    3,    'FIXED_TERM',  '2025-05-01', '2025-06-30',  'CTR-CUS-003', 45000.00, 'MXN', '2025-03-20 10:00:00', '2025-03-20 10:00:00', 1),
(9,  'Mantenimiento IT anual',      'OTHER',    NULL, NULL, 4,    'FIXED_TERM',  '2025-01-01', '2025-12-31',  'CTR-OTH-001', 60000.00, 'MXN', '2025-01-15 10:00:00', '2025-01-15 10:00:00', 1),
(10, 'Contrato prácticante Óscar',  'EMPLOYEE', 12, NULL, NULL, 'FIXED_TERM',  '2025-01-15', '2025-06-15',  'CTR-EMP-012', 1500.00,  'MXN', '2025-01-20 10:00:00', '2025-01-20 10:00:00', 1),
(11, 'Export USA framework',        'CUSTOMER', NULL, 5,    9,    'FIXED_TERM',  '2025-05-15', '2025-11-30',  'CTR-CUS-004', 200000.00,'USD', '2025-05-01 10:00:00', '2025-05-01 10:00:00', 1),
(12, 'Contrato genérico demo',      'UNDEFINED',NULL, NULL, NULL, 'UNDEFINED',   NULL,         NULL,          'CTR-DEMO-01', NULL,     'MXN', '2025-01-05 10:00:00', '2025-01-05 10:00:00', 1);
SELECT setval(pg_get_serial_sequence('business_contracts', 'id'), 12);

-- tasks (12)
INSERT INTO tasks (id, title, description, status, priority, assigned_to_id, created_by_id, due_date, headquarter_id, project_id, opportunity_id, created_at, updated_at, version) VALUES
(1,  'Revisar inventario bodega GDL',  'Conteo semanal',           'PENDING',     'HIGH',   4, 2, '2025-06-05 18:00:00', 3,  NULL, NULL, '2025-05-20 08:00:00', '2025-05-20 08:00:00', 1),
(2,  'Llamar Hotel Chain',             'Seguimiento propuesta',    'IN_PROGRESS', 'URGENT', 12, 2, '2025-06-03 12:00:00', 1,  6,    1,    '2025-05-21 08:00:00', '2025-05-21 08:00:00', 1),
(3,  'Instalar display Gourmet',       'Coordinar con cliente',    'IN_PROGRESS', 'MEDIUM', 8, 2, '2025-05-28 17:00:00', 7,  1,    6,    '2025-05-15 08:00:00', '2025-05-15 08:00:00', 1),
(4,  'Capacitación HACCP nuevo batch', 'Grupo producción',         'COMPLETED',   'MEDIUM', 6, 1, '2025-04-30 10:00:00', 10, 5,    NULL, '2025-04-01 08:00:00', '2025-04-30 10:00:00', 1),
(5,  'Aprobar fórmula picante',        'Lab calidad',              'PENDING',     'HIGH',   7, 2, '2025-06-10 18:00:00', 6,  2,    12,   '2025-05-22 08:00:00', '2025-05-22 08:00:00', 1),
(6,  'Mantenimiento línea 2',          'Planta norte',             'DELAYED',     'HIGH',   10, 1, '2025-05-25 08:00:00', 2,  4,    NULL, '2025-05-10 08:00:00', '2025-05-10 08:00:00', 1),
(7,  'Preparar muestras export',       'Habanero dried',           'IN_PROGRESS', 'URGENT', 11, 2, '2025-06-01 16:00:00', 2,  9,    5,    '2025-05-18 08:00:00', '2025-05-18 08:00:00', 1),
(8,  'Onboarding practicante',         'Documentación RH',         'PENDING',     'LOW',    6,  6, '2025-06-08 17:00:00', 1,  NULL, NULL, '2025-05-23 08:00:00', '2025-05-23 08:00:00', 1),
(9,  'Check-in auditoría',             'ISO prep',                 'ON_HOLD',     'MEDIUM', 5,  1, '2025-06-15 09:00:00', 6,  NULL, NULL, '2025-05-19 08:00:00', '2025-05-19 08:00:00', 1),
(10, 'Entrega kit catering',           'Empaque especial',         'PENDING',     'MEDIUM', 3,  2, '2025-06-12 14:00:00', 4,  3,    3,    '2025-05-24 08:00:00', '2025-05-24 08:00:00', 1),
(11, 'Revisar app inventario',         'Bloqueado por cliente',    'ON_HOLD',     'LOW',    4,  2, '2025-07-01 18:00:00', 1,  8,    NULL, '2025-04-05 08:00:00', '2025-04-05 08:00:00', 1),
(12, 'Cierre oportunidad Super Ahorro', 'Negociación final',       'IN_PROGRESS', 'HIGH',   12, 2, '2025-06-07 12:00:00', 5,  12,   2,    '2025-05-25 08:00:00', '2025-05-25 08:00:00', 1);
SELECT setval(pg_get_serial_sequence('tasks', 'id'), 12);

-- inventory_items (12)
INSERT INTO inventory_items (id, sku, name, category, unit, brand, cost_price, sale_price, reorder_point, reorder_quantity, status, created_at, updated_at, version) VALUES
(1,  'SKU-CHILE-001', 'Chile habanero entero',     'RAW_MATERIAL',   'KG',    'Pimienta', 45.00,  85.00,  50,  100, 'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(2,  'SKU-SALSA-001', 'Salsa roja 500ml',          'FINISHED_GOOD',  'PIECE', 'Pimienta', 12.00,  28.00,  200, 500, 'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(3,  'SKU-SALSA-002', 'Salsa verde 500ml',         'FINISHED_GOOD',  'PIECE', 'Pimienta', 12.00,  28.00,  200, 500, 'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(4,  'SKU-PIM-001',   'Pimienta negra molida 1kg', 'FINISHED_GOOD',  'KG',    'Pimienta', 80.00,  150.00, 30,  80,  'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(5,  'SKU-ENV-001',   'Frasco vidrio 500ml',       'PACKAGING',      'PIECE', 'EnvasesMX', 3.50, 0.00,   500, 2000,'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(6,  'SKU-CONS-001',  'Guantes nitrilo caja',      'CONSUMABLE',     'BOX',   'SafePro',  120.00, 0.00,   10,  20,  'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(7,  'SKU-TOOL-001',  'Báscula industrial',        'TOOL',           'PIECE', 'Mettler',  8500.00,0.00,   1,   1,   'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(8,  'SKU-SPARE-001', 'Motor mezcladora',          'SPARE_PART',     'PIECE', 'MixCo',    12000.00,0.00,  2,   2,   'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(9,  'SKU-OLD-001',   'Salsa legacy 250ml',        'FINISHED_GOOD',  'PIECE', 'Pimienta', 8.00,  0.00,   0,   0,   'DISCONTINUED', '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(10, 'SKU-RAW-002',   'Vinagre blanco',            'RAW_MATERIAL',   'LITER', 'Química',  8.00,  0.00,   100, 200, 'ACTIVE',       '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(11, 'SKU-BOX-001',   'Caja cartón 12 pzas',       'PACKAGING',      'PIECE', 'CartoPack',1.20, 0.00,   300, 1000,'OUT_OF_STOCK', '2025-01-10 08:00:00', '2025-01-10 08:00:00', 1),
(12, 'SKU-NEW-001',   'Aderezo ranch piloto',      'FINISHED_GOOD',  'PIECE', 'Pimienta', 15.00, 32.00,  0,   100, 'PENDING_APPROVAL','2025-01-10 08:00:00', '2025-01-10 08:00:00', 1);
SELECT setval(pg_get_serial_sequence('inventory_items', 'id'), 12);

-- storage_locations (12): 1 warehouse + zones/bins
INSERT INTO storage_locations (id, code, name, type, parent_id, max_capacity, occupied_capacity, status, created_at, updated_at, version) VALUES
(1,  'WH-CDMX',     'Almacén Central CDMX',  'WAREHOUSE', NULL, 10000, 4500, 'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(2,  'WH-GDL',      'Almacén Guadalajara',   'WAREHOUSE', NULL, 5000,  2100, 'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(3,  'ZN-CDMX-A',   'Zona A materia prima',  'ZONE',      1,    3000,  1200, 'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(4,  'ZN-CDMX-B',   'Zona B producto terminado','ZONE',   1,    4000,  2000, 'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(5,  'BIN-A-01',    'Bin A-01',              'BIN',       3,    200,   150,  'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(6,  'BIN-A-02',    'Bin A-02',              'BIN',       3,    200,   80,   'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(7,  'BIN-B-01',    'Bin B-01 salsas',       'BIN',       4,    500,   480,  'FULL',   '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(8,  'BIN-B-02',    'Bin B-02 salsas',       'BIN',       4,    500,   200,  'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(9,  'ZN-GDL-1',    'Zona GDL principal',    'ZONE',      2,    2500,  900,  'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(10, 'BIN-GDL-01',  'Bin GDL 01',            'BIN',       9,    300,   120,  'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(11, 'AISLE-CDMX-1','Pasillo 1',             'AISLE',     3,    1000,  400,  'ACTIVE', '2025-01-11 08:00:00', '2025-01-11 08:00:00', 1),
(12, 'SHELF-B-1',   'Estante B-1',           'SHELF',     4,    800,   300,  'BLOCKED','2025-01-11 08:00:00', '2025-01-11 08:00:00', 1);
SELECT setval(pg_get_serial_sequence('storage_locations', 'id'), 12);

-- inventory_stock (12)
INSERT INTO inventory_stock (id, item_id, location_id, available_quantity, reserved_quantity, in_transit_quantity, status, created_at, updated_at, version) VALUES
(1,  1, 5,  120, 10,  0,   'NORMAL',       '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(2,  2, 7,  450, 50,  100, 'NORMAL',       '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(3,  3, 8,  380, 20,  0,   'NORMAL',       '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(4,  4, 10, 45,  5,   0,   'NORMAL',       '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(5,  5, 6,  800, 100, 200, 'NORMAL',       '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(6,  6, 5,  8,   0,   0,   'LOW_STOCK',    '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(7,  10, 5, 250, 0,   50,  'NORMAL',       '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(8,  2, 10, 120, 0,   0,   'NORMAL',       '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(9,  1, 6,  30,  0,   0,   'LOW_STOCK',    '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(10, 11, 7, 0,   0,   0,   'OUT_OF_STOCK', '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(11, 4,  8,  15,  0,   0,   'LOW_STOCK',    '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1),
(12, 3,  7,  600, 0,   0,   'OVERSTOCKED',  '2025-01-12 08:00:00', '2025-01-12 08:00:00', 1);
SELECT setval(pg_get_serial_sequence('inventory_stock', 'id'), 12);

-- inventory_transactions (12)
INSERT INTO inventory_transactions (id, transaction_number, type, status, notes, initiated_by_id, created_at, updated_at, version) VALUES
(1,  'TXN-2025-0001', 'PURCHASE_RECEIPT',     'COMPLETED', 'Recepción chile',        4, '2025-04-01 10:00:00', '2025-04-01 14:00:00', 1),
(2,  'TXN-2025-0002', 'INTERNAL_TRANSFER',  'COMPLETED', 'CDMX a GDL salsas',      3, '2025-04-05 10:00:00', '2025-04-05 16:00:00', 1),
(3,  'TXN-2025-0003', 'SALE_DISPATCH',        'COMPLETED', 'Pedido Gourmet',         4, '2025-04-10 09:00:00', '2025-04-10 15:00:00', 1),
(4,  'TXN-2025-0004', 'PHYSICAL_COUNT',       'COMPLETED', 'Conteo abril',           5, '2025-04-15 08:00:00', '2025-04-15 18:00:00', 1),
(5,  'TXN-2025-0005', 'PURCHASE_RECEIPT',     'APPROVED',  'Frascos pendiente',      3, '2025-05-01 10:00:00', '2025-05-01 11:00:00', 1),
(6,  'TXN-2025-0006', 'INTERNAL_TRANSFER',    'IN_PROGRESS','Traslado habanero',     4, '2025-05-10 10:00:00', '2025-05-10 12:00:00', 1),
(7,  'TXN-2025-0007', 'RETURN_FROM_CLIENT',   'PENDING',   'Devolución parcial',     4, '2025-05-15 10:00:00', '2025-05-15 10:00:00', 1),
(8,  'TXN-2025-0008', 'PRODUCTION_ISSUE',     'COMPLETED', 'Consumo producción',     3, '2025-05-18 07:00:00', '2025-05-18 09:00:00', 1),
(9,  'TXN-2025-0009', 'SCRAP_WRITE_OFF',      'COMPLETED', 'Merma vinagre',          3, '2025-05-20 10:00:00', '2025-05-20 11:00:00', 1),
(10, 'TXN-2025-0010', 'SALE_DISPATCH',        'DRAFT',     'Pedido Won Foods',       4, '2025-05-22 10:00:00', '2025-05-22 10:00:00', 1),
(11, 'TXN-2025-0011', 'RETURN_TO_SUPPLIER',   'CANCELLED', 'Cancelado proveedor',    3, '2025-05-01 10:00:00', '2025-05-02 10:00:00', 1),
(12, 'TXN-2025-0012', 'PURCHASE_RECEIPT',     'PENDING',   'Guantes seguridad',      3, '2025-05-25 10:00:00', '2025-05-25 10:00:00', 1);
SELECT setval(pg_get_serial_sequence('inventory_transactions', 'id'), 12);

-- inventory_movements (12)
INSERT INTO inventory_movements (id, item_id, source_location_id, destination_location_id, transaction_id, quantity, unit_cost, type, direction, description, performed_by_id, stock_after_movement, created_at, updated_at, version) VALUES
(1,  1,  NULL, 5, 1,  100, 45.00, 'PURCHASE',         'IN',      'Recepción lote 1',     4, 100, '2025-04-01 14:00:00', '2025-04-01 14:00:00', 1),
(2,  2,  7,    10, 2,  50,  12.00, 'TRANSFER',         'NEUTRAL', 'Traslado a GDL',       3, 400, '2025-04-05 16:00:00', '2025-04-05 16:00:00', 1),
(3,  2,  7,    NULL, 3,  30,  28.00, 'SALE',             'OUT',     'Venta Gourmet',        4, 420, '2025-04-10 15:00:00', '2025-04-10 15:00:00', 1),
(4,  4,  NULL, 10, 1,  20,  80.00, 'INITIAL_STOCK',    'IN',      'Stock inicial pimienta',4, 45, '2025-04-01 14:30:00', '2025-04-01 14:30:00', 1),
(5,  10, 5,    NULL, 8,  15,  8.00,  'USAGE',            'OUT',     'Producción salsa',     3, 235, '2025-05-18 09:00:00', '2025-05-18 09:00:00', 1),
(6,  10, 5,    NULL, 9,  5,   8.00,  'SCRAP',            'OUT',     'Merma calidad',        3, 230, '2025-05-20 11:00:00', '2025-05-20 11:00:00', 1),
(7,  5,  NULL, 6, 5,  500, 3.50,  'PURCHASE',         'IN',      'Frascos (aprobado)',   3, 800, '2025-05-01 11:00:00', '2025-05-01 11:00:00', 1),
(8,  1,  5,    6,  6,  20,  45.00, 'TRANSFER',         'NEUTRAL', 'Reubicación chile',    4, 30,  '2025-05-10 12:00:00', '2025-05-10 12:00:00', 1),
(9,  3,  NULL, 7,  NULL, 200, 12.00, 'ADJUSTMENT_PLUS',  'IN',      'Ajuste conteo +',      5, 600, '2025-04-15 18:00:00', '2025-04-15 18:00:00', 1),
(10, 2,  7,    NULL, NULL, 10,  12.00, 'ADJUSTMENT_MINUS','OUT',     'Ajuste conteo -',      5, 440, '2025-04-15 18:05:00', '2025-04-15 18:05:00', 1),
(11, 6,  NULL, 5,  12, 20,  120.00,'PURCHASE',         'IN',      'Guantes pendiente',    3, 28,  '2025-05-25 10:00:00', '2025-05-25 10:00:00', 1),
(12, 11, 7,    NULL, NULL, 5,   1.20,  'SCRAP',            'OUT',     'Cajas dañadas',        3, 0,   '2025-05-20 11:30:00', '2025-05-20 11:30:00', 1);
SELECT setval(pg_get_serial_sequence('inventory_movements', 'id'), 12);

-- payroll_periods (12)
INSERT INTO payroll_periods (id, frequency, start_date, end_date, status, created_at, updated_at, version) VALUES
(1,  'WEEKLY',   '2025-04-28', '2025-05-04', 'PAID',     '2025-05-05 08:00:00', '2025-05-05 18:00:00', 1),
(2,  'WEEKLY',   '2025-05-05', '2025-05-11', 'PAID',     '2025-05-12 08:00:00', '2025-05-12 18:00:00', 1),
(3,  'WEEKLY',   '2025-05-12', '2025-05-18', 'PAID',     '2025-05-19 08:00:00', '2025-05-19 18:00:00', 1),
(4,  'WEEKLY',   '2025-05-19', '2025-05-25', 'PENDING',  '2025-05-26 08:00:00', '2025-05-26 08:00:00', 1),
(5,  'BIWEEKLY', '2025-05-01', '2025-05-15', 'PAID',     '2025-05-16 08:00:00', '2025-05-16 18:00:00', 1),
(6,  'BIWEEKLY', '2025-05-16', '2025-05-31', 'PENDING',  '2025-06-01 08:00:00', '2025-06-01 08:00:00', 1),
(7,  'MONTHLY',  '2025-04-01', '2025-04-30', 'PAID',     '2025-05-01 08:00:00', '2025-05-03 18:00:00', 1),
(8,  'MONTHLY',  '2025-05-01', '2025-05-31', 'PENDING',  '2025-06-01 08:00:00', '2025-06-01 08:00:00', 1),
(9,  'WEEKLY',   '2025-03-31', '2025-04-06', 'PAID',     '2025-04-07 08:00:00', '2025-04-07 18:00:00', 1),
(10, 'WEEKLY',   '2025-03-24', '2025-03-30', 'PAID',     '2025-03-31 08:00:00', '2025-03-31 18:00:00', 1),
(11, 'CUSTOM',   '2025-05-10', '2025-05-20', 'PAID',     '2025-05-21 08:00:00', '2025-05-21 18:00:00', 1),
(12, 'WEEKLY',   '2025-05-26', '2025-06-01', 'PENDING',  '2025-06-02 08:00:00', '2025-06-02 08:00:00', 1);
SELECT setval(pg_get_serial_sequence('payroll_periods', 'id'), 12);

-- payroll_records (12)
INSERT INTO payroll_records (id, employee_id, period_id, worked_days_start, worked_days_end, gross_amount, total_discounts, total_bonuses, net_amount, status, created_at, updated_at, version) VALUES
(1,  1, 1, '2025-04-28', '2025-05-04', 2500.00, 200.00, 100.00, 2400.00, 'PAID',    '2025-05-05 09:00:00', '2025-05-05 18:00:00', 1),
(2,  2, 1, '2025-04-28', '2025-05-04', 3200.00, 250.00, 150.00, 3100.00, 'PAID',    '2025-05-05 09:00:00', '2025-05-05 18:00:00', 1),
(3,  3, 2, '2025-05-05', '2025-05-11', 2200.00, 180.00, 0.00,   2020.00, 'PAID',    '2025-05-12 09:00:00', '2025-05-12 18:00:00', 1),
(4,  5, 7, '2025-04-01', '2025-04-30', 18000.00,1500.00,500.00, 17000.00,'PAID',    '2025-05-01 09:00:00', '2025-05-03 18:00:00', 1),
(5,  6, 7, '2025-04-01', '2025-04-30', 15200.00,1200.00,300.00, 14300.00,'PAID',    '2025-05-01 09:00:00', '2025-05-03 18:00:00', 1),
(6,  11, 3, '2025-05-12', '2025-05-18', 3500.00, 280.00, 200.00, 3420.00, 'PAID',    '2025-05-19 09:00:00', '2025-05-19 18:00:00', 1),
(7,  4, 4, '2025-05-19', '2025-05-25', 2100.00, 170.00, 0.00,   1930.00, 'PENDING', '2025-05-26 09:00:00', '2025-05-26 09:00:00', 1),
(8,  7, 4, '2025-05-19', '2025-05-25', 3000.00, 240.00, 50.00,  2810.00, 'PENDING', '2025-05-26 09:00:00', '2025-05-26 09:00:00', 1),
(9,  8, 5, '2025-05-01', '2025-05-15', 5600.00, 450.00, 300.00, 5450.00, 'PAID',    '2025-05-16 09:00:00', '2025-05-16 18:00:00', 1),
(10, 10, 2, '2025-05-05', '2025-05-11', 2600.00, 0.00,   0.00,   2600.00, 'DEFERRED','2025-05-12 09:00:00', '2025-05-12 09:00:00', 1),
(11, 9, 6, '2025-05-16', '2025-05-31', 4000.00, 320.00, 0.00,   3680.00, 'PARTIAL', '2025-06-01 09:00:00', '2025-06-01 09:00:00', 1),
(12, 12, 11,'2025-05-10', '2025-05-20', 900.00,  50.00,  0.00,   850.00,  'PAID',    '2025-05-21 09:00:00', '2025-05-21 18:00:00', 1);
SELECT setval(pg_get_serial_sequence('payroll_records', 'id'), 12);

-- payroll_adjustments (12)
INSERT INTO payroll_adjustments (id, payroll_record_id, type, amount, reason, created_at, updated_at, version) VALUES
(1,  1, 'DISCOUNT', 150.00, 'Préstamo personal',       '2025-05-05 09:30:00', '2025-05-05 09:30:00', 1),
(2,  1, 'BONUS',    100.00, 'Bono puntualidad',        '2025-05-05 09:30:00', '2025-05-05 09:30:00', 1),
(3,  2, 'DISCOUNT', 250.00, 'IMSS',                    '2025-05-05 09:30:00', '2025-05-05 09:30:00', 1),
(4,  2, 'BONUS',    150.00, 'Bono supervisor',         '2025-05-05 09:30:00', '2025-05-05 09:30:00', 1),
(5,  4, 'DISCOUNT', 1500.00,'ISR mensual',             '2025-05-01 09:30:00', '2025-05-01 09:30:00', 1),
(6,  4, 'BONUS',    500.00, 'Bono desempeño',          '2025-05-01 09:30:00', '2025-05-01 09:30:00', 1),
(7,  6, 'DISCOUNT', 280.00, 'Descuentos varios',       '2025-05-19 09:30:00', '2025-05-19 09:30:00', 1),
(8,  6, 'BONUS',    200.00, 'Bono producción',         '2025-05-19 09:30:00', '2025-05-19 09:30:00', 1),
(9,  7, 'DISCOUNT', 170.00, 'Faltante caja',           '2025-05-26 09:30:00', '2025-05-26 09:30:00', 1),
(10, 9, 'BONUS',    300.00, 'Comisión ventas',         '2025-05-16 09:30:00', '2025-05-16 09:30:00', 1),
(11, 11,'DISCOUNT', 320.00, 'Adelanto quincenal',      '2025-06-01 09:30:00', '2025-06-01 09:30:00', 1),
(12, 12,'DISCOUNT', 50.00,  'Uniforme',                '2025-05-21 09:30:00', '2025-05-21 09:30:00', 1);
SELECT setval(pg_get_serial_sequence('payroll_adjustments', 'id'), 12);

-- payroll_payments (12)
INSERT INTO payroll_payments (id, payroll_record_id, employee_id, frequency, worked_days_start, worked_days_end, gross_amount, net_amount, destination_account, transaction_id, status, pending_amount, created_at, updated_at, version) VALUES
(1,  1, 1,  'WEEKLY',   '2025-04-28', '2025-05-04', 2500.00, 2400.00, '012345678901234567', 'SPEI-2025-00001', 'PAID',    0.00, '2025-05-05 18:00:00', '2025-05-05 18:00:00', 1),
(2,  2, 2,  'WEEKLY',   '2025-04-28', '2025-05-04', 3200.00, 3100.00, '012345678901234568', 'SPEI-2025-00002', 'PAID',    0.00, '2025-05-05 18:00:00', '2025-05-05 18:00:00', 1),
(3,  3, 3,  'WEEKLY',   '2025-05-05', '2025-05-11', 2200.00, 2020.00, '012345678901234569', 'SPEI-2025-00003', 'PAID',    0.00, '2025-05-12 18:00:00', '2025-05-12 18:00:00', 1),
(4,  4, 5,  'MONTHLY',  '2025-04-01', '2025-04-30', 18000.00,17000.00,'012345678901234570', 'SPEI-2025-00004', 'PAID',    0.00, '2025-05-03 18:00:00', '2025-05-03 18:00:00', 1),
(5,  5, 6,  'MONTHLY',  '2025-04-01', '2025-04-30', 15200.00,14300.00,'012345678901234571', 'SPEI-2025-00005', 'PAID',    0.00, '2025-05-03 18:00:00', '2025-05-03 18:00:00', 1),
(6,  6, 11, 'WEEKLY',   '2025-05-12', '2025-05-18', 3500.00, 3420.00, '012345678901234572', 'SPEI-2025-00006', 'PAID',    0.00, '2025-05-19 18:00:00', '2025-05-19 18:00:00', 1),
(7,  7, 4,  'WEEKLY',   '2025-05-19', '2025-05-25', 2100.00, 0.00,    '012345678901234573', 'SPEI-PENDING-07', 'PENDING', 1930.00,'2025-05-26 09:00:00', '2025-05-26 09:00:00', 1),
(8,  8, 7,  'WEEKLY',   '2025-05-19', '2025-05-25', 3000.00, 0.00,    '012345678901234574', 'SPEI-PENDING-08', 'PENDING', 2810.00,'2025-05-26 09:00:00', '2025-05-26 09:00:00', 1),
(9,  9, 8,  'BIWEEKLY', '2025-05-01', '2025-05-15', 5600.00, 5450.00, '012345678901234575', 'SPEI-2025-00009', 'PAID',    0.00, '2025-05-16 18:00:00', '2025-05-16 18:00:00', 1),
(10, 10,10, 'WEEKLY',   '2025-05-05', '2025-05-11', 2600.00, 0.00,    '012345678901234576', 'SPEI-DEFERRED-10','DEFERRED',2600.00,'2025-05-12 09:00:00', '2025-05-12 09:00:00', 1),
(11, 11,9,  'BIWEEKLY', '2025-05-16', '2025-05-31', 4000.00, 2000.00, '012345678901234577', 'SPEI-2025-00011', 'PARTIAL', 1680.00,'2025-06-01 18:00:00', '2025-06-01 18:00:00', 1),
(12, 12,12, 'CUSTOM',   '2025-05-10', '2025-05-20', 900.00,  850.00,  '012345678901234578', 'SPEI-2025-00012', 'PAID',    0.00, '2025-05-21 18:00:00', '2025-05-21 18:00:00', 1);
SELECT setval(pg_get_serial_sequence('payroll_payments', 'id'), 12);

-- payroll_debts (12)
INSERT INTO payroll_debts (id, employee_id, payroll_record_id, amount_owed, reason, settled, settled_at, created_at, updated_at, version) VALUES
(1,  4,  NULL, 500.00,  'Adelanto combustible',     false, NULL,                  '2025-04-01 10:00:00', '2025-04-01 10:00:00', 1),
(2,  9,  NULL, 1200.00, 'Préstamo personal',        false, NULL,                  '2025-03-15 10:00:00', '2025-03-15 10:00:00', 1),
(3,  1,  1,    150.00,  'Descuento préstamo sem 1', true,  '2025-05-05 18:00:00', '2025-05-01 10:00:00', '2025-05-05 18:00:00', 1),
(4,  10, NULL, 800.00,  'Uniforme dañado',          false, NULL,                  '2025-05-10 10:00:00', '2025-05-10 10:00:00', 1),
(5,  3,  NULL, 300.00,  'Herramienta extraviada',   true,  '2025-05-12 18:00:00', '2025-04-20 10:00:00', '2025-05-12 18:00:00', 1),
(6,  8,  9,    200.00,  'Comisión adelantada',      false, NULL,                  '2025-05-16 10:00:00', '2025-05-16 10:00:00', 1),
(7,  12, 12,   50.00,   'Uniforme practicante',     true,  '2025-05-21 18:00:00', '2025-05-20 10:00:00', '2025-05-21 18:00:00', 1),
(8,  2,  NULL, 1000.00, 'Capacitación externa',     false, NULL,                  '2025-02-01 10:00:00', '2025-02-01 10:00:00', 1),
(9,  6,  NULL, 2500.00, 'Curso certificación',      false, NULL,                  '2025-01-10 10:00:00', '2025-01-10 10:00:00', 1),
(10, 7,  NULL, 400.00,  'Equipo laboratorio',       false, NULL,                  '2025-05-01 10:00:00', '2025-05-01 10:00:00', 1),
(11, 11, NULL, 600.00,  'Utensilios cocina',        false, NULL,                  '2025-04-15 10:00:00', '2025-04-15 10:00:00', 1),
(12, 5,  NULL, 0.00,    'Registro placeholder',     true,  '2025-01-01 10:00:00', '2025-01-01 10:00:00', '2025-01-01 10:00:00', 1);
SELECT setval(pg_get_serial_sequence('payroll_debts', 'id'), 12);

-- entity_comments (12)
INSERT INTO entity_comments (id, target_type, target_id, author_id, body, created_at, updated_at, version) VALUES
(1,  'TASK',                 1,  2, 'Recordar contar también zona fría.',           '2025-05-20 10:00:00', '2025-05-20 10:00:00', 1),
(2,  'TASK',                 2,  12,'Cliente pidió cotización actualizada.',       '2025-05-21 11:00:00', '2025-05-21 11:00:00', 1),
(3,  'OPPORTUNITY',          1,  12,'Buen fit para línea premium.',                 '2025-05-22 09:00:00', '2025-05-22 09:00:00', 1),
(4,  'PROJECT',              2,  2, 'Priorizar fórmula antes de junio.',            '2025-05-18 14:00:00', '2025-05-18 14:00:00', 1),
(5,  'PROJECT_MILESTONE',    5,  2, 'Lab necesita 3 días más.',                     '2025-05-19 09:00:00', '2025-05-19 09:00:00', 1),
(6,  'CONTRACT',             3,  1, 'Revisar cláusula de entrega con legal.',       '2025-05-10 16:00:00', '2025-05-10 16:00:00', 1),
(7,  'EMPLOYEE',             12, 6, 'Documentación incompleta, seguir lunes.',      '2025-05-23 08:00:00', '2025-05-23 08:00:00', 1),
(8,  'HEADQUARTER',          2,  3, 'Mantenimiento programado puerta 3.',           '2025-05-15 12:00:00', '2025-05-15 12:00:00', 1),
(9,  'INVENTORY_ITEM',       1,  4, 'Próximo lote con certificado orgánico.',       '2025-05-12 10:00:00', '2025-05-12 10:00:00', 1),
(10, 'INVENTORY_TRANSACTION',5,  3, 'Proveedor confirmó entrega viernes.',          '2025-05-02 11:00:00', '2025-05-02 11:00:00', 1),
(11, 'PAYROLL_RECORD',       7,  5, 'Pendiente validar días trabajados chofer.',    '2025-05-26 10:00:00', '2025-05-26 10:00:00', 1),
(12, 'OPPORTUNITY',          12, 12,'Proyecto derivado ya en marcha.',              '2025-05-24 09:00:00', '2025-05-24 09:00:00', 1);
SELECT setval(pg_get_serial_sequence('entity_comments', 'id'), 12);

-- notifications (12)
INSERT INTO notifications (id, channel, type, status, recipient_email, recipient_display_name, recipient_user_id, subject, body, locale, attempt_count, sent_at, created_at, updated_at, version) VALUES
('a0000001-0000-4000-8000-000000000001', 'EMAIL', 'ACCOUNT_PENDING_APPROVAL', 'SENT',    'pending@pimienta.local', 'Karla Nuevo',  11, 'Cuenta pendiente de aprobación', 'Tu registro está en revisión.', 'es', 1, '2025-05-01 10:00:00', '2025-05-01 09:00:00', '2025-05-01 10:00:00', 1),
('a0000002-0000-4000-8000-000000000002', 'EMAIL', 'ACCOUNT_PENDING_APPROVAL', 'PENDING', 'pending@pimienta.local', 'Karla Nuevo',  11, 'Recordatorio aprobación',        NULL,                            'es', 0, NULL,                  '2025-05-10 09:00:00', '2025-05-10 09:00:00', 1),
('a0000003-0000-4000-8000-000000000003', 'LOG',   'UNDEFINED',                'SENT',    NULL,                     'Sistema',      NULL, NULL,                             'Usuario admin inició sesión',   'en', 1, '2025-05-15 08:00:00', '2025-05-15 08:00:00', '2025-05-15 08:00:00', 1),
('a0000004-0000-4000-8000-000000000004', 'EMAIL', 'UNDEFINED',                'FAILED',  'user01@pimienta.local',  'Diego López',  4, 'Notificación fallida',           'SMTP timeout simulado',         'es', 3, NULL,                  '2025-05-16 10:00:00', '2025-05-16 12:00:00', 1),
('a0000005-0000-4000-8000-000000000005', 'SMS',   'UNDEFINED',                'SKIPPED', NULL,                     '+525511110005',5, NULL,                             'SMS deshabilitado en dev',      'es', 0, NULL,                  '2025-05-17 10:00:00', '2025-05-17 10:00:00', 1),
('a0000006-0000-4000-8000-000000000006', 'EMAIL', 'ACCOUNT_PENDING_APPROVAL', 'SENT',    'admin@pimienta.local',   'Ana Admin',     1, 'Nuevo registro por aprobar',     'Usuario Karla Nuevo registrado.','es', 1, '2025-05-01 09:05:00', '2025-05-01 09:05:00', '2025-05-01 09:05:00', 1),
('a0000007-0000-4000-8000-000000000007', 'LOG',   'ACCOUNT_PENDING_APPROVAL', 'SENT',    NULL,                     'Audit',        NULL, NULL,                             'Dispatch OK correlation=reg-11', 'en', 1, '2025-05-01 09:00:00', '2025-05-01 09:00:00', '2025-05-01 09:00:00', 1),
('a0000008-0000-4000-8000-000000000008', 'EMAIL', 'UNDEFINED',                'SENT',    'manager@pimienta.local', 'Carlos Manager',2, 'Reporte semanal',                'Resumen operaciones adjunto.',  'es', 1, '2025-05-20 07:00:00', '2025-05-20 07:00:00', '2025-05-20 07:00:00', 1),
('a0000009-0000-4000-8000-000000000009', 'EMAIL', 'UNDEFINED',                'PENDING', 'sales@pimienta.local',   'Luis Ventas',  12, 'Seguimiento oportunidad',        'Recordatorio llamada Hotel Chain','es', 0, NULL,                  '2025-05-25 08:00:00', '2025-05-25 08:00:00', 1),
('a000000a-0000-4000-8000-00000000000a', 'LOG',   'UNDEFINED',                'SENT',    NULL,                     'Payroll',      NULL, NULL,                             'Nómina semana 19 procesada',    'es', 1, '2025-05-26 18:00:00', '2025-05-26 18:00:00', '2025-05-26 18:00:00', 1),
('a000000b-0000-4000-8000-00000000000b', 'EMAIL', 'ACCOUNT_PENDING_APPROVAL', 'FAILED',  'pending@pimienta.local', 'Karla Nuevo',  11, 'Reintento aprobación',           'Mailbox full (seed)',           'es', 2, NULL,                  '2025-05-18 10:00:00', '2025-05-18 11:00:00', 1),
('a000000c-0000-4000-8000-00000000000c', 'SMS',   'UNDEFINED',                'SENT',    NULL,                     '+525511110012',12, NULL,                             'OTP demo 123456',               'es', 1, '2025-05-22 14:00:00', '2025-05-22 14:00:00', '2025-05-22 14:00:00', 1);

-- file_assets (12)
INSERT INTO file_assets (id, category, module, entity_type, entity_id, s3_key, original_name, content_type, file_size_bytes, description, uploaded_by_user_id, created_at, updated_at, version) VALUES
('b0000001-0000-4000-8000-000000000001', 'COMPANY',  NULL,       NULL,       NULL, 'pimienta/sources/company/logo.png',           'logo.png',           'image/png',       20480,  'Logo corporativo',              1, '2025-01-15 10:00:00', '2025-01-15 10:00:00', 1),
('b0000002-0000-4000-8000-000000000002', 'TEMPLATE', NULL,       NULL,       NULL, 'pimienta/sources/templates/contrato-emp.docx',  'contrato-emp.docx',  'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 45000, 'Plantilla contrato', 1, '2025-01-15 10:00:00', '2025-01-15 10:00:00', 1),
('b0000003-0000-4000-8000-000000000003', 'EXTRAS',   NULL,       NULL,       NULL, 'pimienta/sources/extras/manual-haccp.pdf',      'manual-haccp.pdf',   'application/pdf', 512000, 'Manual HACCP',                  1, '2025-01-16 10:00:00', '2025-01-16 10:00:00', 1),
('b0000004-0000-4000-8000-000000000004', 'RESOURCE', 'crm',      'PROJECT',  2,    'pimienta/sources/crm/project-2/brief.pdf',      'brief.pdf',          'application/pdf', 128000, 'Brief Won Foods',               12,'2025-03-20 11:00:00', '2025-03-20 11:00:00', 1),
('b0000005-0000-4000-8000-000000000005', 'RESOURCE', 'employee', 'EMPLOYEE', 1,    'pimienta/sources/employee/1/photo.jpg',         'photo.jpg',          'image/jpeg',      89000,  'Foto empleado',                 6, '2025-01-20 10:00:00', '2025-01-20 10:00:00', 1),
('b0000006-0000-4000-8000-000000000006', 'RESOURCE', 'inventory','ITEM',     2,    'pimienta/sources/inventory/item-2/label.pdf',   'label.pdf',          'application/pdf', 12000,  'Etiqueta salsa roja',           4, '2025-02-01 10:00:00', '2025-02-01 10:00:00', 1),
('b0000007-0000-4000-8000-000000000007', 'RESOURCE', 'contract', 'CONTRACT', 3,    'pimienta/sources/contract/3/signed.pdf',        'signed.pdf',         'application/pdf', 256000, 'Contrato firmado Hotel',       2, '2025-03-01 10:00:00', '2025-03-01 10:00:00', 1),
('b0000008-0000-4000-8000-000000000008', 'TEMPLATE', NULL,       NULL,       NULL, 'pimienta/sources/templates/nomina-semanal.xlsx','nomina-semanal.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 22000, 'Plantilla nómina', 5, '2025-01-15 10:00:00', '2025-01-15 10:00:00', 1),
('b0000009-0000-4000-8000-000000000009', 'RESOURCE', 'task',     'TASK',     7,    'pimienta/sources/task/7/spec-habanero.pdf',     'spec-habanero.pdf',  'application/pdf', 98000,  'Especificación export',         11,'2025-05-18 10:00:00', '2025-05-18 10:00:00', 1),
('b000000a-0000-4000-8000-00000000000a', 'COMPANY',  NULL,       NULL,       NULL, 'pimienta/sources/company/org-chart.pdf',        'org-chart.pdf',      'application/pdf', 64000,  'Organigrama',                   1, '2025-02-10 10:00:00', '2025-02-10 10:00:00', 1),
('b000000b-0000-4000-8000-00000000000b', 'EXTRAS',   NULL,       NULL,       NULL, 'pimienta/sources/extras/brand-guidelines.pdf',  'brand-guidelines.pdf','application/pdf',180000,'Guía de marca',                1, '2025-02-15 10:00:00', '2025-02-15 10:00:00', 1),
('b000000c-0000-4000-8000-00000000000c', 'RESOURCE', 'payroll',  'RECORD',   4,    'pimienta/sources/payroll/record-4/recibo.pdf',  'recibo.pdf',         'application/pdf', 15000,  'Recibo nómina abril',           5, '2025-05-03 18:00:00', '2025-05-03 18:00:00', 1);
