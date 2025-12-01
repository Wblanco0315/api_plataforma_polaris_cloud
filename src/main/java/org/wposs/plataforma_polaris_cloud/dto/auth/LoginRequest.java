package org.wposs.plataforma_polaris_cloud.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Schema (description = "Petición para iniciar sesión de un usuario en el sistema")
@Validated
public record LoginRequest(
        @Email(message = "el email debe tener un formato valido")
        @NotNull(message = "el email no puede ser nulo")
        @Schema(description = "Correo electrónico del usuario", example = "nuevo.usuario@ejemplo.com")
        String email,
        @NotNull(message = "la contraseña no puede estar vacia")
        @Size(min = 8,max = 16, message = "la contraseña debe tener minimo 8 caracteres")
        @Schema(description = "Contraseña (mínimo 8, máximo 16 caracteres)", example = "Password123*")
        String password
) {

}
