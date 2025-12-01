package org.wposs.plataforma_polaris_cloud.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wposs.plataforma_polaris_cloud.dto.auth.AuthResponse;
import org.wposs.plataforma_polaris_cloud.dto.auth.LoginRequest;
import org.wposs.plataforma_polaris_cloud.dto.auth.RegisterRequest;
import org.wposs.plataforma_polaris_cloud.services.AuthService;

@Controller
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
@Tag(name = "Autenticación", description = "Operaciones de usuarios y generación de tokens JWT.")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Inicio de sesión",
            description = "Autentica al usuario usando email y contraseña.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa. Devuelve el token JWT.",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Credenciales incorrectas o no autorizado."),
                    @ApiResponse(responseCode = "400", description = "Estructura de la petición inválida.")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @Operation(
            summary = "Registro de nuevo usuario",
            description = "Registra un nuevo usuario en el sistema con roles asignados.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario creado exitosamente.",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Errores de validación o roles inválidos.")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }
}
