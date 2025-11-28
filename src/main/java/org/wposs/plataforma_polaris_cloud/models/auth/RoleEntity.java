package org.wposs.plataforma_polaris_cloud.models.auth;


import jakarta.persistence.*;
import lombok.*;
import org.wposs.plataforma_polaris_cloud.models.auth.enums.RolesEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RolesEnum roleEnum;
}