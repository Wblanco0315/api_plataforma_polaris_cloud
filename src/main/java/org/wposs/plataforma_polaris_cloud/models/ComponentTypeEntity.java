package org.wposs.plataforma_polaris_cloud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "component_types")
public class ComponentTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_type_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "type_name", nullable = false)
    private String typeName;

}