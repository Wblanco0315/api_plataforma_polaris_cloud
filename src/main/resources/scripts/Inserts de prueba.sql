--Usuarios
INSERT INTO public.users (id, name, email, email_verified_at, password, remember_token, current_team_id, profile_photo_path, created_at, updated_at, two_factor_secret, two_factor_recovery_codes, two_factor_confirmed_at, team_role_type_id, team_id) VALUES (1, 'Wilson Blanco', 'wilsonblanco@wposs.com', null, '$2y$12$CZ4obn8YSUT7UyXutDplh.AApp03lGhTN13FsfzxhaYmgzcx6uOwq', null, null, null, '2025-12-05 20:55:44', '2025-12-05 20:55:44', null, null, null, null, null);


-- 1. Insertar CLIENTES
INSERT INTO customers (customer_name)
VALUES ('MEDIANET'),
       ('BANCARD'),
       ('BANCO PICHINCHA'),
       ('CAJA PIURA'),
       ('MATRIX TECH'),
       ('DATAFAST'),
       ('LINKSER'),
       ('BANCO GUAYAQUIL'),
       ('BANCO PROMERICA COSTA RICA'),
       ('BANCO PROMERICA EL SALVADOR'),
       ('AUSTRORED'),
       ('REVAL');

-- 2. Insertar TIPOS DE COMPONENTE
-- ID 1: Servicio, ID 2: Plataforma Web, ID 3: Base de Datos
INSERT INTO component_types (type_name)
VALUES ('Servicio'),
       ('Plataforma Web'),
       ('Base de Datos');

-- 3. Insertar TIPOS DE AMBIENTE
-- ID 1: Desarrollo, ID 2: Certificacion, ID 3: Produccion
INSERT INTO environment_types (type_name)
VALUES ('Desarrollo'),
       ('Certificacion'),
       ('Produccion');

-- 4. Insertar TIPOS DE SERVIDOR
-- ID 1: AWS, ID 2: Onpremise
INSERT INTO server_types (type_name)
VALUES ('AWS'),
       ('Onpremise');

-- 5. Insertar TIPOS DE TECNOLOGÍA
INSERT INTO tech_types (type_name)
VALUES ('ScriptCase'),
       ('Postgres'),
       ('SQLServer'),
       ('Java'),
       ('.NET'),
       ('Visual Basic');

-- 6. Insertar VERSIONES DE TECNOLOGÍA (Ejemplos necesarios para crear componentes)
INSERT INTO tech_type_versions (version, tech_type_id)
VALUES ('v9.9', 1), -- ScriptCase
       ('17', 2),   -- Postgres
       ('2019', 3), -- SQLServer
       ('17', 4),   -- Java
       ('21', 4),   -- Java
       ('8', 4),    --Java
       ('6.0', 5),  -- .NET
       ('6.0', 6);
-- VB

-- 7. Insertar CAMPOS DINÁMICOS (Component Fields)
-- Lógica:
-- Para "Servicio" (ID 1): Build Tool, IP BALANCEADOR / PUERTO
-- Para "Plataforma Web" (ID 2): version Php
-- Para "Base de Datos" (ID 3): Usuarios, Tareas Programadas

INSERT INTO component_fields (component_type_id, field_name, field_type, is_required, field_index, default_value)
VALUES
-- Campos para Servicio
(1, 'Build Tool', 'SELECT', true, 1, 'Maven'),
(1, 'IP BALANCEADOR / PUERTO', 'TEXT', false, 2, NULL),

-- Campos para Plataforma Web
(2, 'Version Php', 'TEXT', true, 1, '8.1'),

-- Campos para Base de Datos
(3, 'Usuarios', 'TEXTAREA', true, 1, 'admin'),
(3, 'Tareas Programadas', 'TEXTAREA', false, 2, NULL);

-- 8. Insertar REPOSITORIOS (Necesario para crear componentes)
INSERT INTO components_repositories (repository_name, repository_url, created_by)
VALUES ('databaseschemasignaturemnet', 'https://bitbucket.org/WPOSS/databaseschemasignaturemnet/master',
        'system'),                                                                            --DB signature MNET
       ('databaseschemasignaturemnet_historico',
        'https://bitbucket.org/WPOSS/databaseschemasignaturemnet_historico/master', 'system'),--DB signature MNET Historico
       ('DB_Medianet', 'https://bitbucket.org/WPOSS/db_medianet/master', 'system'); --DB Polaris Cloud MNET
;

-- 9. Insertar AMBIENTES (Combinación de Tipo Ambiente y Servidor)
INSERT INTO environments (environment_url, environment_type_id, server_type_id)
VALUES ('https://prod.aws.internal', 3, 2),-- Prod en AWS
       ('https://dev.local', 1, 2), -- Dev en OnPremise
       ('https://qa.aws.localinternal', 2, 1), -- QA en AWS
       ('https://prod.local.com', 3, 1);
-- Prod en OnPremise

-- 10. Insertar PROYECTOS DE PRUEBA
-- Proyectos para MEDIANET (ID 1)
INSERT INTO projects (customer_id, project_name, project_description, status, created_by, start_date)
VALUES (1, 'Polaris Signature', 'Sistema Transaccional', 'ACTIVO', 'system', '2024-01-01'),
       (1, 'Polaris Cloud', 'Descarga de apks y gestion de terminales', 'ACTIVO', 'syste,', '2024-02-01');

-- 11. Insertar COMPONENTES/Productos
--Componente/Producto para MEDIANET (ID 1)
INSERT INTO components (project_id,
                        component_name,
                        component_description,
                        component_notes,
                        component_repository_id,
                        component_type_id,
                        environment_id,
                        tech_type_version_id,
                        port_number,
                        created_by)
VALUES
-- Componente 1: Una BD SQL Server para Polaris Signature en Producción AWS para Medianet
(1, 'PolarisSigantureMedianet', 'Base de datos transaccional', NULL, 1, 3, 3, 2, 1433, 'system'),

-- Componente 2: Una BD SQL Server Polaris Signature Historica en Producción AWS para Medianet
(1, 'PolarisSigantureMedianet', 'Base de datos historico transaccional', NULL, 2, 3, 3, 2, 1433, 'system'),

-- Componente 3: BD SQL Server para Polaris Cloud en Producción AWS para Medianet
(2, 'HiveTrxServerMedianet', 'Portal de administración', NULL, 3, 3, 3, 2, 1433, 'system');


-- 12. Insertar VALORES DE CAMPOS
-- Para Componente 1
-- Llenamos "Usuarios"
INSERT INTO field_values (value, component_field_id, project_component_id)
VALUES ('app_user, read_only_user', 4, 1),
       ('Backup diario a las 00:00', 5, 1),
-- Para Componente 2
       ('app_user_historico, read_only_user', 4, 2),
       ('Backup semanal a las 02:00 AM cada domingo', 5, 2),
-- Para Componente 3
       ('cloud_admin, cloud_readonly', 4, 3),
       ('No aplica', 5, 3)
;