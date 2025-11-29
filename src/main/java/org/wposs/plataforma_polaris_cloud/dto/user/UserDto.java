package org.wposs.plataforma_polaris_cloud.dto.user;

import java.sql.Timestamp;
import java.util.List;

public record UserDto(
        Long id,
        String name,
        String lastname,
        String email,
        boolean isEnabled,
        Timestamp createdAt,
        Timestamp updatedAt,
        List<RoleDto> roles
) {
}
