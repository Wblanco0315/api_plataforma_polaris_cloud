package org.wposs.plataforma_polaris_cloud.models.types;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "technology_type_versions")
public class TechnologyTypeVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "technology_type_version_id")
    private Long id;

    @Column(name = "version", nullable = false)
    private String version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_type_id", nullable = false)
    private TechnologyTypeEntity technologyType;
}
