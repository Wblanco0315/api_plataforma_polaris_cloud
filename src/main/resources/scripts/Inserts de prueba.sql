-- =============================================================================
-- PARTE 2: DATOS DE PRUEBA (Escenario Medianet - Polaris Signature)
-- =============================================================================

-- 1. Catálogos Base (Tipos de Servidor, Entorno, Tecnología)
INSERT INTO server_types (server_type_id, type_name) VALUES
                                                         (1, 'AWS Cloud'),
                                                         (2, 'On-Premise Server');

INSERT INTO environment_types (environment_type_id, type_name, created_by, created_at) VALUES
                                                                                           (1, 'Desarrollo', 'admin', CURRENT_DATE),
                                                                                           (2, 'Certificación', 'admin', CURRENT_DATE),
                                                                                           (3, 'Producción', 'admin', CURRENT_DATE);

INSERT INTO technology_types (technology_type_id, type_name) VALUES
                                                                 (1, 'Java'),
                                                                 (2, 'PostgreSQL'),
                                                                 (3, 'Scriptcase');

INSERT INTO technology_type_versions (technology_type_version_id, technology_type_id, version) VALUES
                                                                                                   (10, 1, '17'), -- Java 17
                                                                                                   (11, 1, '8'),  -- Java 8
                                                                                                   (20, 2, '17'), -- Postgres 17
                                                                                                   (30, 3, '8');  -- Scriptcase 8

-- 2. Tipos de Componentes y Definición de Campos Dinámicos (EAV)
INSERT INTO component_types (component_type_id, type_name, created_by, created_at) VALUES
                                                                                       (100, 'Backend Service', 'admin', CURRENT_DATE),
                                                                                       (200, 'Database', 'admin', CURRENT_DATE),
                                                                                       (300, 'Web Platform', 'admin', CURRENT_DATE);

-- Definición de Campos (Lo que pediste: Build Tool, IPs, Tareas, etc.)
INSERT INTO component_fields (component_field_id, component_type_id, field_name, field_type, is_required, field_index) VALUES
-- Para Servicios Backend (Java)
(1001, 100, 'Build Tool', 'SELECT', true, 1),      -- Gradle o Maven
(1002, 100, 'Load Balancer IP', 'TEXT', false, 2), -- Puede ser No Aplica
(1003, 100, 'Load Balancer Port', 'NUMBER', false, 3),

-- Para Base de Datos
(2001, 200, 'Scheduled Tasks', 'BOOLEAN', true, 1),
(2002, 200, 'DB Users', 'TEXTAREA', true, 2);

-- 3. Cliente y Proyecto
INSERT INTO customers (customer_id, customer_name, create_at) VALUES
    (1, 'Medianet', '2023-01-01');

INSERT INTO projects (project_id, project_name, project_description, customer_id, start_date, status, created_by, created_at) VALUES
    (1, 'Polaris Signature', 'Plataforma de gestión de firmas', 1, CURRENT_DATE, 'ACTIVE', 'admin', CURRENT_DATE);

-- Relación Cliente-Proyecto
INSERT INTO customer_projects (customer_id, project_id) VALUES (1, 1);

-- 4. Entornos (Infraestructura)
-- Nota: La Web Platform (Scriptcase) es On-Premise, el resto AWS
INSERT INTO environments (environment_id, environment_name, environment_url, environment_type_id, server_type_id) VALUES
-- Entornos AWS para Backend y BD
(10, 'AWS Dev Cluster', 'dev.aws.medianet.com', 1, 1),
(11, 'AWS Cert Cluster', 'cert.aws.medianet.com', 2, 1),
(12, 'AWS Prod Cluster', 'prod.aws.medianet.com', 3, 1),
-- Entornos On-Premise para Scriptcase
(20, 'Local Server Dev', '192.168.1.10', 1, 2),
(21, 'Local Server Prod', '10.0.0.50', 3, 2);

-- Repositorios Dummy
INSERT INTO components_repositories (component_repository_id, repository_name, repository_url, created_by, created_at) VALUES
                                                                                                                           (1, 'polaris-backend-repo', 'git@github.com:medianet/backend.git', 'system', CURRENT_DATE),
                                                                                                                           (2, 'polaris-db-scripts', 'git@github.com:medianet/db.git', 'system', CURRENT_DATE),
                                                                                                                           (3, 'polaris-web-legacy', 'git@github.com:medianet/web.git', 'system', CURRENT_DATE);

-- 5. COMPONENTES (La parte central de tu lógica)

-- A) Servicio Java 17 (Backend Core) en AWS Dev
INSERT INTO components (component_id, component_type_id, environment_id, technology_type_version_id, component_repository_id, created_by, created_at, port_number) VALUES
    (500, 100, 10, 10, 1, 'devops', CURRENT_DATE, 8080);

-- B) Base de Datos Postgres 17 en AWS Dev
INSERT INTO components (component_id, component_type_id, environment_id, technology_type_version_id, component_repository_id, created_by, created_at, port_number) VALUES
    (501, 200, 10, 20, 2, 'devops', CURRENT_DATE, 5432);

-- C) Plataforma Web Scriptcase 8 en On-Premise Dev
INSERT INTO components (component_id, component_type_id, environment_id, technology_type_version_id, component_repository_id, created_by, created_at, port_number) VALUES
    (502, 300, 20, 30, 3, 'devops', CURRENT_DATE, 80);

-- D) Servicio Java 8 (Un legacy service) en AWS Dev
INSERT INTO components (component_id, component_type_id, environment_id, technology_type_version_id, component_repository_id, created_by, created_at, port_number) VALUES
    (503, 100, 10, 11, 1, 'devops', CURRENT_DATE, 8081);

-- Asociar componentes al proyecto
INSERT INTO project_components (project_id, component_id) VALUES
                                                              (1, 500), (1, 501), (1, 502), (1, 503);

-- 6. VALORES DE CAMPOS (Field Values - La data específica)

-- Valores para el Servicio Java 17 (ID 500)
INSERT INTO field_values (project_component_id, component_field_id, value) VALUES
                                                                               (500, 1001, 'Gradle'),       -- Build Tool
                                                                               (500, 1002, '10.20.30.40'),  -- IP Balancer
                                                                               (500, 1003, '443');          -- Port Balancer

-- Valores para el Servicio Java 8 Legacy (ID 503)
INSERT INTO field_values (project_component_id, component_field_id, value) VALUES
                                                                               (503, 1001, 'Maven'),        -- Build Tool
                                                                               (503, 1002, 'No Aplica'),    -- IP Balancer
                                                                               (503, 1003, '0');

-- Valores para la Base de Datos (ID 501)
INSERT INTO field_values (project_component_id, component_field_id, value) VALUES
                                                                               (501, 2001, 'true'),                     -- Tareas Programadas: Sí
                                                                               (501, 2002, 'app_user, readonly_user');  -- Usuarios

-- =============================================================================
-- 1. ACTUALIZAR CATÁLOGOS (Nuevas Tecnologías y Versiones)
-- =============================================================================

-- Agregamos nuevas tecnologías que Bancard usa
INSERT INTO technology_types (technology_type_id, type_name) VALUES
                                                                 (4, 'Oracle DB'),
                                                                 (5, 'React JS')
ON CONFLICT (technology_type_id) DO NOTHING;

-- Agregamos versiones específicas para esas tecnologías
INSERT INTO technology_type_versions (technology_type_version_id, technology_type_id, version) VALUES
                                                                                                   (40, 1, '21'),      -- Java 21 (Nueva versión para Java)
                                                                                                   (50, 4, '19c'),     -- Oracle 19c
                                                                                                   (60, 5, '18.2')     -- React 18
ON CONFLICT (technology_type_version_id) DO NOTHING;

-- =============================================================================
-- 2. CREAR CLIENTE Y PROYECTO
-- =============================================================================

INSERT INTO customers (customer_id, customer_name, create_at) VALUES
    (2, 'Bancard', CURRENT_DATE);

INSERT INTO projects (project_id, project_name, project_description, customer_id, start_date, status, created_by, created_at) VALUES
    (2, 'Switch Transaccional', 'Procesador de pagos en tiempo real', 2, CURRENT_DATE, 'IN_PROGRESS', 'admin', CURRENT_DATE);

-- Vincular Cliente con Proyecto
INSERT INTO customer_projects (customer_id, project_id) VALUES (2, 2);

-- =============================================================================
-- 3. INFRAESTRUCTURA (Ambientes exclusivos de Bancard)
-- =============================================================================

-- Bancard tiene su propia VPC en AWS
INSERT INTO environments (environment_id, environment_name, environment_url, environment_type_id, server_type_id) VALUES
                                                                                                                      (30, 'Bancard AWS Dev', 'dev.bancard.cloud', 1, 1),   -- Desarrollo en AWS
                                                                                                                      (31, 'Bancard AWS Prod', 'api.bancard.com', 3, 1);    -- Producción en AWS

-- Repositorios de código de Bancard
INSERT INTO components_repositories (component_repository_id, repository_name, repository_url, created_by, created_at) VALUES
                                                                                                                           (10, 'bancard-core-api', 'git@gitlab.com:bancard/core.git', 'dev_lead', CURRENT_DATE),
                                                                                                                           (11, 'bancard-frontend', 'git@gitlab.com:bancard/ui.git', 'frontend_lead', CURRENT_DATE);

-- =============================================================================
-- 4. COMPONENTES DEL SISTEMA
-- =============================================================================

-- A) Backend: Microservicio Core (Java 21)
INSERT INTO components (component_id, component_type_id, environment_id, technology_type_version_id, component_repository_id, created_by, created_at, port_number) VALUES
    (600, 100, 30, 40, 10, 'arquitecto', CURRENT_DATE, 9090); -- ID 100=Backend, Tech 40=Java 21

-- B) Base de Datos: Oracle 19c
INSERT INTO components (component_id, component_type_id, environment_id, technology_type_version_id, component_repository_id, created_by, created_at, port_number) VALUES
    (601, 200, 30, 50, 10, 'dba', CURRENT_DATE, 1521); -- ID 200=DB, Tech 50=Oracle. Reusamos repo 10 (monorepo) o null

-- C) Frontend: Portal Web (React)
INSERT INTO components (component_id, component_type_id, environment_id, technology_type_version_id, component_repository_id, created_by, created_at, port_number) VALUES
    (602, 300, 30, 60, 11, 'frontend_lead', CURRENT_DATE, 443); -- ID 300=Web, Tech 60=React

-- Asociar estos componentes al proyecto "Switch Transaccional" (ID 2)
INSERT INTO project_components (project_id, component_id) VALUES
                                                              (2, 600), (2, 601), (2, 602);

-- =============================================================================
-- 5. CONFIGURACIÓN TÉCNICA (Field Values)
-- =============================================================================

-- Configuración del Backend Java 21
INSERT INTO field_values (project_component_id, component_field_id, value) VALUES
                                                                               (600, 1001, 'Maven'),          -- Build Tool
                                                                               (600, 1002, '172.16.0.5'),     -- IP Load Balancer Interno
                                                                               (600, 1003, '80');             -- Puerto LB

-- Configuración de la Base de Datos Oracle
INSERT INTO field_values (project_component_id, component_field_id, value) VALUES
                                                                               (601, 2001, 'false'),          -- Tareas Programadas: No
                                                                               (601, 2002, 'sys as sysdba, app_user'); -- Usuarios