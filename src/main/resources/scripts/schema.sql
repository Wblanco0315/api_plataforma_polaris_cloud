DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE TABLE customers
(
    customer_id   BIGSERIAL PRIMARY KEY,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    customer_name VARCHAR(255)                        NOT NULL
);

CREATE TABLE projects
(
    project_id          BIGSERIAL PRIMARY KEY,
    customer_id         BIGINT                              NOT NULL
        CONSTRAINT fk_projects_customers
            REFERENCES customers,
    project_name        VARCHAR(255)                        NOT NULL,
    project_description TEXT,
    status              VARCHAR(255)                        NOT NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by          VARCHAR(255)                        NOT NULL,
    end_date            DATE,
    start_date          DATE                                NOT NULL,
    updated_by          VARCHAR(255),
    updated_at          TIMESTAMP
);

-- Tipos
CREATE TABLE component_types
(
    component_type_id BIGSERIAL PRIMARY KEY,
    type_name         VARCHAR(255) NOT NULL
);

CREATE TABLE environment_types
(
    environment_type_id BIGSERIAL PRIMARY KEY,
    type_name           VARCHAR(255) NOT NULL
);

CREATE TABLE server_types
(
    server_type_id BIGSERIAL PRIMARY KEY,
    type_name      VARCHAR(255) NOT NULL
);

CREATE TABLE tech_types
(
    tech_type_id BIGSERIAL PRIMARY KEY,
    type_name    VARCHAR(255) NOT NULL
);

CREATE TABLE components_repositories
(
    component_repository_id BIGSERIAL                           NOT NULL PRIMARY KEY,
    repository_name         VARCHAR(255)                        NOT NULL,
    repository_url          TEXT                                NOT NULL,
    created_by              VARCHAR(255)                        NOT NULL,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at              TIMESTAMP,
    updated_by              VARCHAR(255)
);

CREATE TABLE environments
(
    environment_id      BIGSERIAL    NOT NULL PRIMARY KEY,
    environment_url     TEXT DEFAULT 'NOT_SET' NOT NULL,
    environment_type_id BIGINT       NOT NULL
        CONSTRAINT fk_env_type
            REFERENCES environment_types,
    server_type_id      BIGINT       NOT NULL
        CONSTRAINT fk_server_type
            REFERENCES server_types
);

CREATE TABLE tech_type_versions
(
    technology_type_version_id BIGSERIAL PRIMARY KEY NOT NULL,
    version                    VARCHAR(255)          NOT NULL,
    tech_type_id               BIGINT                NOT NULL
        CONSTRAINT fk_tech_type
            REFERENCES tech_types
);

CREATE TABLE components
(
    component_id            BIGSERIAL PRIMARY KEY               NOT NULL,
    component_name          VARCHAR(255)                        NOT NULL,
    component_description   TEXT,
    component_notes         TEXT,
    component_repository_id BIGINT                              NOT NULL
        CONSTRAINT fk_comp_repo REFERENCES components_repositories,
    component_type_id       BIGINT                              NOT NULL
        CONSTRAINT fk_component_type
            REFERENCES component_types,
    environment_id          BIGINT                              NOT NULL
        CONSTRAINT fk_environment
            REFERENCES environments,
    tech_type_version_id    BIGINT                              NOT NULL
        CONSTRAINT fk_tech_version
            REFERENCES tech_type_versions,
    project_id              BIGINT                              NOT NULL
        CONSTRAINT fk_component_project
            REFERENCES projects,
    created_by              VARCHAR(255)                        NOT NULL,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    port_number             INTEGER                             NOT NULL,
    dns                     VARCHAR(255),
    updated_by              VARCHAR(255),
    updated_at              TIMESTAMP
);

CREATE TABLE component_fields
(
    field_index        INTEGER      NOT NULL,
    is_required        BOOLEAN      NOT NULL,
    component_field_id BIGSERIAL PRIMARY KEY,
    component_type_id  BIGINT       NOT NULL
        CONSTRAINT fk_component_field_type
            REFERENCES component_types,
    default_value      VARCHAR(255),
    field_name         VARCHAR(255) NOT NULL,
    field_type         VARCHAR(255) NOT NULL
);

CREATE TABLE field_values
(
    field_value_id       BIGSERIAL PRIMARY KEY,
    value                VARCHAR(255) NOT NULL,
    component_field_id   BIGINT       NOT NULL
        CONSTRAINT fk_fv_component_field
            REFERENCES component_fields,
    project_component_id BIGINT       NOT NULL
        CONSTRAINT fk_fv_project_component
            REFERENCES components
);
