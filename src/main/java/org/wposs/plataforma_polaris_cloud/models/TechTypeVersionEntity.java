package org.wposs.plataforma_polaris_cloud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tech_type_versions")
public class TechTypeVersionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technology_type_version_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tech_type_id", nullable = false)
    private TechTypeEntity techType;

}