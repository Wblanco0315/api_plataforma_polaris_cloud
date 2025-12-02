package org.wposs.plataforma_polaris_cloud.models.types;

import jakarta.persistence.*;
import lombok.*;
import org.wposs.plataforma_polaris_cloud.models.projects_components.ComponentEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "field_values")
public class FieldValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "field_value_id")
    private Long id;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_field_id", nullable = false)
    private ComponentFieldsEntity componentField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_component_id" , nullable = false)
    private ComponentEntity projectComponent;
}

