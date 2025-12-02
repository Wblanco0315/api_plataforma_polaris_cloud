package org.wposs.plataforma_polaris_cloud.models.projects_components;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "customer_projects", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<ProjectEntity> projects;

    @Column(name = "create_at", nullable = false)
    @NotNull
    @CreationTimestamp
    private String createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private String updateAt;
}