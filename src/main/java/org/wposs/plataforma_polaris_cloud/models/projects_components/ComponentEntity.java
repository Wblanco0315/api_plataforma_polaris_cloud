package org.wposs.plataforma_polaris_cloud.models.projects_components;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.wposs.plataforma_polaris_cloud.models.types.ComponentTypeEntity;
import org.wposs.plataforma_polaris_cloud.models.types.TechnologyTypeEntity;
import org.wposs.plataforma_polaris_cloud.models.types.TechnologyTypeVersion;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "components")
public class ComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "component_id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "component_type_id", referencedColumnName = "component_type_id", nullable = false)
    private ComponentTypeEntity componentType;

    @Column(name = "component_name", nullable = false)
    @NotNull
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "environment_id", referencedColumnName = "environment_id", nullable = false)
    private EnvironmentEntity environment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "technology_type_version_id", referencedColumnName = "technology_type_version_id", nullable = false)
    private TechnologyTypeVersion technologyType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "component_repository_id", referencedColumnName = "component_repository_id", nullable = false)
    private ComponentRepositoryEntity repository;

    @Column(name = "port_number",nullable = false)
    @NotNull
    private Integer portNumber;

    @Column(name = "dns")
    private String dns;

    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    @NotNull
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
