package org.wposs.plataforma_polaris_cloud.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import java.util.Set;

@Validated
@Schema(description = "Petición para registrar un nuevo usuario en el sistema")
public record RegisterRequest(
        @Email(message = "el email debe tener un formato valido")
        @NotNull(message = "el email no puede ser nulo")
        @Schema(description = "Correo electrónico del usuario", example = "nuevo.usuario@ejemplo.com")
        String email,
        @NotNull(message = "el nombre no puede ser nulo")
        @Schema(description = "Nombre de pila del usuario", example = "Carlos")
        String name,
        @NotNull(message = "el apellido no puede ser nulo")
        @Schema(description = "Apellido del usuario", example = "Perez")
        String lastname,
        @NotNull(message = "la contraseña no puede estar vacia")
        @Size(min = 8, max = 16, message = "la contraseña debe tener minimo 8 caracteres")
        @Schema(description = "Contraseña (mínimo 8, máximo 16 caracteres)", example = "Password123*")
        String password,
        @NotNull(message = "los roles no pueden ser nulos")
        @Schema(description = "Lista de roles a asignar por id del rol (ej: [\"1\"])")
        Set<Long> roles
){

}