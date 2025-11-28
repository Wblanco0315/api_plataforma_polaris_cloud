package org.wposs.plataforma_polaris_cloud.dto.auth;

import org.springframework.validation.annotation.Validated;

@Validated
public record RegisterRequest(String email, String username, String password, RoleRequest roles) {
}