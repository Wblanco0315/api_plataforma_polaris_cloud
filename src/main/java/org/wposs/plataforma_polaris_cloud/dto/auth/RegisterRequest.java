package org.wposs.plataforma_polaris_cloud.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

@Validated
public record RegisterRequest(
        @Email(message = "el email debe tener un formato valido")
        @NotNull(message = "el email no puede ser nulo")
        String email,
        @NotNull(message = "el nombre no puede ser nulo")
        String name,
        @NotNull(message = "el apellido no puede ser nulo")
        String lastname,
        @NotNull(message = "la contraseña no puede estar vacia")
        @Size(min = 8, max = 16, message = "la contraseña debe tener minimo 8 caracteres")
        String password,
        @NotNull(message = "los roles no pueden ser nulos")
        Set<Long> roles
){

}