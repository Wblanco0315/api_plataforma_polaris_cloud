package org.wposs.plataforma_polaris_cloud.models.projects_components;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "environment_ports")
public class EnvironmentPortEntity {

    @Id
    @Column(name = "port_number", nullable = false)
    private Integer portNumber;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_id", nullable = false)
    private EnvironmentEntity environment;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;
}