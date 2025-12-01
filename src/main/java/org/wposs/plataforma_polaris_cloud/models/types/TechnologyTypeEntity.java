package org.wposs.plataforma_polaris_cloud.models.types;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "technology_types")
public class TechnologyTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technology_type_id",nullable = false)
    private Long id;

    @Column(name = "type_name", nullable = false)
    private String name;

}
