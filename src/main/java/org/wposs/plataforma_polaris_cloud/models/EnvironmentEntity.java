package org.wposs.plataforma_polaris_cloud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "environments")
public class EnvironmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "environment_id", nullable = false)
    private Long id;

    @NotNull
    @ColumnDefault("'NOT_SET'")
    @Column(name = "environment_url", nullable = false, length = Integer.MAX_VALUE)
    private String environmentUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "environment_type_id", nullable = false)
    private EnvironmentTypeEntity environmentType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "server_type_id", nullable = false)
    private ServerTypeEntity serverType;

}