package org.wposs.plataforma_polaris_cloud.models.projects_components;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "create_at", nullable = false)
    @NotNull
    @CreationTimestamp
    private String createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private String updateAt;
}