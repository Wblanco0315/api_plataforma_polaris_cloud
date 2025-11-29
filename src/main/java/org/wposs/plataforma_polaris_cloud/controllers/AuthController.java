package org.wposs.plataforma_polaris_cloud.controllers;

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
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
