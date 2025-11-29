package org.wposs.plataforma_polaris_cloud.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthResponse(
        String token,
        boolean status,
        String message
) {
    public static AuthResponse success(String token, String message) {
        return new AuthResponse(token, true, message);
    }

    public static AuthResponse success(String message) {
        return new AuthResponse(null, true, message);
    }

    public static AuthResponse error(String errorMessage) {
        return new AuthResponse(null, false, errorMessage);
    }
}
