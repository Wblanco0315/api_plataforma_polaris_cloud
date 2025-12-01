package org.wposs.plataforma_polaris_cloud.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta de autenticación o estado de la operación.")
public record AuthResponse(
        @Schema(description = "Estado de la operación: true si fue exitosa.")
        boolean status,
        @Schema(description = "Mensaje descriptivo del resultado.")
        String message,
        @Schema(description = "Token JWT. Presente solo en login exitoso o registro con login.", nullable = true)
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
