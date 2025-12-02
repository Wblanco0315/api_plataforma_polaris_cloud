package org.wposs.plataforma_polaris_cloud.models.projects_components;

import jakarta.persistence.*;
import lombok.*;
import org.wposs.plataforma_polaris_cloud.models.types.EnvironmentTypeEntity;
import org.wposs.plataforma_polaris_cloud.models.types.ServerTypeEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "environments")
public class EnvironmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "environment_id")
    private Long id;

    @Column(name = "environment_name", nullable = false)
    private String name;

    @Column(name = "environment_url", nullable = false)
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_type_id", nullable = false)
    private EnvironmentTypeEntity environmentType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_type_id", nullable = false)
    private ServerTypeEntity serverType;
}
