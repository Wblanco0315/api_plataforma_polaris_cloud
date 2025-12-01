package org.wposs.plataforma_polaris_cloud.models.projects_components;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects_components")
public class ProjectComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_component_id", nullable = false)
    private Long id;


}
