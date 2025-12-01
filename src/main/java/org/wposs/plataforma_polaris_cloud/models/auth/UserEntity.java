package org.wposs.plataforma_polaris_cloud.models.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.HashSet;
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

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "lastname")
    @NotNull
    private String lastname;

    @Column(name = "email")
    @NotNull
    @Email
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_enable")
    private boolean isEnabled;

    @Column(name = "account_no_expired")
    private boolean isAccountNonExpired;

    @Column(name = "account_no_locked")
    private boolean isAccountNonLocked;

    @Column(name = "credential_no_expired")
    private boolean isCredentialsNonExpired;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

}