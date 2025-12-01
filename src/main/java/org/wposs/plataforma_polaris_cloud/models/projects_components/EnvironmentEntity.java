package org.wposs.plataforma_polaris_cloud.models.projects_components;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "environments")
public class EnvironmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "environment_id")
    private Long id;

    @Column(name = "environment_name", nullable = false)
    private String name;

    @Column(name = "environment_url", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_component_id", nullable = false)
    private ProjectComponentEntity projectComponent;
}
