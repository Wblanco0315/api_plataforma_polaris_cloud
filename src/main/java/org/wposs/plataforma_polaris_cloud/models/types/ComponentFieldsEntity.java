package org.wposs.plataforma_polaris_cloud.models.types;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "component_fields")
public class ComponentFieldsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_field_id", nullable = false)
    private Long id;

    @Column(name = "field_name", nullable = false)
    private String name;

    @Column(name = "field_type", nullable = false)
    private String type;

    @Column(name = "field_index", nullable = false)
    private Integer index;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    @Column(name = "default_value")
    private String defaultValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_type_id", nullable = false)
    private ComponentTypeEntity componentType;

}
