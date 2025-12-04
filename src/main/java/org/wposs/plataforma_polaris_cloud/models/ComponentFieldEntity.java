package org.wposs.plataforma_polaris_cloud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "component_fields")
public class ComponentFieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_field_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "field_index", nullable = false)
    private Integer fieldIndex;

    @NotNull
    @Column(name = "is_required", nullable = false)
    private Boolean isRequired = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_type_id", nullable = false)
    private ComponentTypeEntity componentType;

    @Size(max = 255)
    @Column(name = "default_value")
    private String defaultValue;

    @Size(max = 255)
    @NotNull
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Size(max = 255)
    @NotNull
    @Column(name = "field_type", nullable = false)
    private String fieldType;

}