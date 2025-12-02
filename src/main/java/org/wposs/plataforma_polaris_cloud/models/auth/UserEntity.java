package org.wposs.plataforma_polaris_cloud.models.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Users")
public class UserEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", length = 50)
    @NotNull
    private String name;

    @Column(name = "lastname", length = 50)
    @NotNull
    private String lastname;

    @Column(name = "email", unique = true, length = 100)
    @NotNull
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    @Column(name = "is_enable", nullable = false)
    @NotNull
    private boolean isEnabled;

    @NotNull
    @Column(name = "account_no_expired", nullable = false)
    private boolean isAccountNonExpired;

    @NotNull
    @Column(name = "account_no_locked", nullable = false)
    private boolean isAccountNonLocked;

    @NotNull
    @Column(name = "credential_no_expired", nullable = false)
    private boolean isCredentialsNonExpired;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

}