package org.wposs.plataforma_polaris_cloud.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthResponse(
        boolean status,
        String message,
        String token
) {
    public static AuthResponse success(String token, String message) {
        return new AuthResponse(true, message, token);
    }

    public static AuthResponse success(String message) {
        return new AuthResponse(true, message, null);
    }

    public static AuthResponse error(String errorMessage) {
        return new AuthResponse(false, errorMessage, null);
    }
}
