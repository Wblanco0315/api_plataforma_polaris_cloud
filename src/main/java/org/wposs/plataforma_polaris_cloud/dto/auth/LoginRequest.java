package org.wposs.plataforma_polaris_cloud.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record LoginRequest(
        @Email(message = "el email debe tener un formato valido")
        @NotNull(message = "el email no puede ser nulo")
        String email,
        @NotNull(message = "la contraseña no puede estar vacia")
        @Size(min = 8, message = "la contraseña debe tener minimo 8 caracteres")
        String password
) {

}
