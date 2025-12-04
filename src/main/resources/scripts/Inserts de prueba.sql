-- 1. Insertar CLIENTES
INSERT INTO customers (customer_name) VALUES
                                          ('MEDIANET'),
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
INSERT INTO component_types (type_name) VALUES
                                            ('Servicio'),
                                            ('Plataforma Web'),
                                            ('Base de Datos');

-- 3. Insertar TIPOS DE AMBIENTE
-- ID 1: Desarrollo, ID 2: Certificacion, ID 3: Produccion
INSERT INTO environment_types (type_name) VALUES
                                              ('Desarrollo'),
                                              ('Certificacion'),
                                              ('Produccion');

-- 4. Insertar TIPOS DE SERVIDOR
-- ID 1: AWS, ID 2: Onpremise
INSERT INTO server_types (type_name) VALUES
                                         ('AWS'),
                                         ('Onpremise');

-- 5. Insertar TIPOS DE TECNOLOGÍA
-- Asumiendo IDs secuenciales: 1:ScriptCase, 2:Postgres, 3:SQLServer, 4:Java, 5:.NET, 6:Visual Basic
INSERT INTO tech_types (type_name) VALUES
                                       ('ScriptCase'),
                                       ('Postgres'),
                                       ('SQLServer'),
                                       ('Java'),
                                       ('.NET'),
                                       ('Visual Basic');

-- 6. Insertar VERSIONES DE TECNOLOGÍA (Ejemplos necesarios para crear componentes)
INSERT INTO tech_type_versions (version, tech_type_id) VALUES
                                                           ('v9.9', 1), -- ScriptCase
                                                           ('v14', 2),  -- Postgres
                                                           ('2019', 3), -- SQLServer
                                                           ('17', 4),   -- Java
                                                           ('21', 4),   -- Java
                                                           ('6.0', 5),  -- .NET
                                                           ('6.0', 6);  -- VB

-- 7. Insertar CAMPOS DINÁMICOS (Component Fields)
-- Lógica:
-- Para "Servicio" (ID 1): Build Tool, IP BALANCEADOR / PUERTO
-- Para "Plataforma Web" (ID 2): version Php
-- Para "Base de Datos" (ID 3): Usuarios, Tareas Programadas

INSERT INTO component_fields (component_type_id, field_name, field_type, is_required, field_index, default_value) VALUES
-- Campos para Servicio
(1, 'Build Tool', 'SELECT', true, 1, 'Maven'),
(1, 'IP BALANCEADOR / PUERTO', 'TEXT', false, 2, NULL),

-- Campos para Plataforma Web
(2, 'Version Php', 'TEXT', true, 1, '8.1'),

-- Campos para Base de Datos
(3, 'Usuarios', 'TEXTAREA', true, 1, 'admin'),
(3, 'Tareas Programadas', 'TEXTAREA', false, 2, NULL);

-- 8. Insertar REPOSITORIOS (Necesario para crear componentes)
INSERT INTO components_repositories (repository_name, repository_url, created_by) VALUES
                                                                                      ('Repo GitLab Principal', 'https://gitlab.com/company/main.git', 'admin'),
                                                                                      ('Repo GitHub Legacy', 'https://github.com/company/legacy.git', 'admin');

-- 9. Insertar AMBIENTES (Combinación de Tipo Ambiente y Servidor)
INSERT INTO environments (environment_url, environment_type_id, server_type_id) VALUES
                                                                                                      ('https://dev.aws.internal', 1, 1),      -- Dev en AWS
                                                                                                      ( 'https://qa.local', 2, 2),        -- QA en OnPremise
                                                                                                      ( 'https://prod.aws.com', 3, 1);          -- Prod en AWS

-- 10. Insertar PROYECTOS DE PRUEBA
-- Proyecto para MEDIANET (ID 1) y BANCO PICHINCHA (ID 3)
INSERT INTO projects (customer_id, project_name, project_description, status, created_by, start_date) VALUES
                                                                                                          (1, 'Pasarela de Pagos V2', 'Modernización de la pasarela', 'En Progreso', 'admin', '2024-01-01'),
                                                                                                          (3, 'Banca Móvil 2024', 'Nueva App Móvil', 'Iniciado', 'admin', '2024-02-15');

-- 11. Insertar COMPONENTES (Aquí se unen todas las relaciones)
-- Nota: project_id 1 es Medianet
INSERT INTO components (
    project_id,
    component_name,
    component_description,
    component_notes,
    component_repository_id,
    component_type_id,
    environment_id,
    tech_type_version_id,
    port_number,
    created_by
) VALUES
-- Componente 1: Un Servicio Java en Desarrollo AWS para Medianet
(1, 'Backend Transaccional', 'Microservicio Core', 'Nota: Reiniciar al desplegar', 1, 1, 1, 4, 8080, 'admin'),

-- Componente 2: Una BD Postgres en Producción AWS para Medianet
(1, 'BD Transaccional', 'Base de datos principal', NULL, 1, 3, 3, 2, 5432, 'admin'),

-- Componente 3: Una Plataforma Web ScriptCase para Banco Pichincha
(2, 'Backoffice Administrativo', 'Portal de administración', NULL, 1, 2, 2, 1, 80, 'admin');


-- 12. Insertar VALORES DE CAMPOS (Field Values)
-- Esto llena la info dinámica específica de cada componente creado arriba

-- Para Componente 1 (Backend Transaccional - Servicio):
-- Llenamos "Build Tool" y "IP BALANCEADOR"
INSERT INTO field_values (value, component_field_id, project_component_id) VALUES
                                                                               ('Gradle', 1, 1),           -- Build Tool
                                                                               ('10.0.0.55:80', 2, 1);     -- IP Balanceador

-- Para Componente 2 (BD Transaccional - Base de Datos):
-- Llenamos "Usuarios"
INSERT INTO field_values (value, component_field_id, project_component_id) VALUES
                                                                               ('app_user, read_only_user', 4, 2), -- Usuarios
                                                                               ('Backup diario a las 00:00', 5, 2); -- Tareas Programadas

-- Para Componente 3 (Backoffice - Plataforma Web):
-- Llenamos "Version Php"
INSERT INTO field_values (value, component_field_id, project_component_id) VALUES
    ('7.4', 3, 3);