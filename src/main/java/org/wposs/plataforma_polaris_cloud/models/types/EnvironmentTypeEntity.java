package org.wposs.plataforma_polaris_cloud.models.types;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "environment_types")
public class EnvironmentTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "environment_type_id",nullable = false)
    private Long id;

    @Column(name = "type_name", nullable = false)
    private String name;
}
