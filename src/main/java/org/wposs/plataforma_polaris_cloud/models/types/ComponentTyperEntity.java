package org.wposs.plataforma_polaris_cloud.models.types;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "component_types")
public class ComponentTyperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "component_type_id", nullable = false)
    private Long id;

    @Column(name = "type_name", nullable = false)
    private String name;
}
