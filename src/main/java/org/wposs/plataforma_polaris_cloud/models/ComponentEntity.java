package org.wposs.plataforma_polaris_cloud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "components")
public class ComponentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "component_name", nullable = false)
    private String componentName;

    @Column(name = "component_description", length = Integer.MAX_VALUE)
    private String componentDescription;

    @Column(name = "component_notes", length = Integer.MAX_VALUE)
    private String componentNotes;

    @Column(name = "component_parent_id")
    private Long componentParentId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_repository_id", nullable = false)
    private ComponentsRepositoryEntity componentRepository;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_type_id", nullable = false)
    private ComponentTypeEntity componentType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "environment_id", nullable = false)
    private EnvironmentEntity environment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tech_type_version_id", nullable = false)
    private TechTypeVersionEntity techTypeVersion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Size(max = 255)
    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "port_number", nullable = false)
    private Integer portNumber;

    @Size(max = 255)
    @Column(name = "dns")
    private String dns;

    @Size(max = 255)
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

}