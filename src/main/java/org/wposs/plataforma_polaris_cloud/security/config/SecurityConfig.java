package org.wposs.plataforma_polaris_cloud.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.wposs.plataforma_polaris_cloud.security.filters.JwtTokenValidator;
import org.wposs.plataforma_polaris_cloud.security.services.utils.JWTUtils;
import org.wposs.plataforma_polaris_cloud.services.AuthService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {    //Inyección por constructor
    // - Permite construir el filtro de seguridad
    private final JWTUtils jwtUtils;

    // - Configuración CORS
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean //Configuración de seguridad
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean //Proveedor de autentificación
    public AuthenticationProvider authenticationProvider(AuthService authService) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(authService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean //Algoritmo de encriptación de Contraseñas
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

}