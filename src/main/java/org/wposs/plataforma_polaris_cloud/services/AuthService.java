package org.wposs.plataforma_polaris_cloud.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wposs.plataforma_polaris_cloud.dto.auth.AuthResponse;
import org.wposs.plataforma_polaris_cloud.dto.auth.LoginRequest;
import org.wposs.plataforma_polaris_cloud.dto.auth.RegisterRequest;
import org.wposs.plataforma_polaris_cloud.models.auth.RoleEntity;
import org.wposs.plataforma_polaris_cloud.models.auth.UserEntity;
import org.wposs.plataforma_polaris_cloud.repositories.RoleRepository;
import org.wposs.plataforma_polaris_cloud.repositories.UserRepository;
import org.wposs.plataforma_polaris_cloud.security.services.utils.JWTUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity userEntity = null;
        try {
            log.info("Buscando usuario por email: {}", email);
            userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new AuthException("usuario no registrado"));
        } catch (UsernameNotFoundException | AuthException e) {//No encontro al usuario, arroja la exeption
            log.error("Error al buscar al usuario: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>(); //Carga la lista de permisos

        try {
            // Busca los roles asignados en el registro
            userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        } catch (IllegalArgumentException e) {// Atrapa la exception al no encontrar rol/roles asignados
            log.error("Roles Invalidos: {}", e.getMessage());
            throw new RuntimeException("Rol/roles no existentes");
        }

        log.info("Usuario encontrado: {}", userEntity.getEmail());
        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList
        );

    }

    //Inicio de sesion
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        String accessToken = "";
        String rspMessage = "";
        log.info("Intento de autenticacion con email: {}", request.email());
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessToken = jwtUtils.createToken(authentication);
        } catch (Exception e) {
            rspMessage = "Error de autenticacion";
            log.error("Error de autenticacion: {}", e.getMessage());
            return new ResponseEntity<>(AuthResponse.error(rspMessage), HttpStatus.UNAUTHORIZED);
        }

        rspMessage = "Usuario autenticado con exito";
        log.info("Usuario autenticado con exito: {}", request.email());
        return new ResponseEntity<>(AuthResponse.success(accessToken, rspMessage), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        String rspMessage = "";
        Set<RoleEntity> roleEntityList;
        log.info("Intento de registro de usuario con email: {}", request.email());
        try {
            roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(request.roles()).stream().collect(Collectors.toSet());
        } catch (IllegalArgumentException ex) {
            rspMessage = "Roles Invalidos";
            log.error("Roles Invalidos: {}", ex.getMessage());
            return new ResponseEntity<>(AuthResponse.error(rspMessage), HttpStatus.BAD_REQUEST);
        }

        if (roleEntityList.isEmpty()) {
            rspMessage = "El usuario no tiene roles asignados";
            log.error(rspMessage);
            return new ResponseEntity<>(AuthResponse.error(rspMessage), HttpStatus.BAD_REQUEST);
        }

        UserEntity newUser = UserEntity.builder()
                .name(request.name())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .roles(roleEntityList)
                .build();

        userRepository.save(newUser);
        rspMessage = "Usuario registrado con exito";
        log.info("Usuario registrado con exito");
        return new ResponseEntity<>(AuthResponse.success(rspMessage), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<AuthResponse> restorePassword(LoginRequest request) {
        String rpsMessage = "";
        String encodedNewPassword = passwordEncoder.encode(request.password());
        log.info("Intento de restauracion de contraseña con email: {}", request.email());
        try {
            Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(request.email());
            if (userEntity.isEmpty()) {
                rpsMessage = "El usuario no esta registrado";
                log.warn(rpsMessage);
                return new ResponseEntity<>(AuthResponse.error(rpsMessage), HttpStatus.NOT_FOUND);
            }
            userEntity.get().setPassword(encodedNewPassword);
            userRepository.save(userEntity.get());

        } catch (Exception e) {
            rpsMessage = "Error al restablecer la contraseña:" + e.getMessage();
            log.error(rpsMessage);
            return new ResponseEntity<>(AuthResponse.error(rpsMessage), HttpStatus.BAD_REQUEST);
        }
        rpsMessage = "Contraseña restablecida con exito";
        log.info(rpsMessage);
        return new ResponseEntity<>(AuthResponse.success(rpsMessage), HttpStatus.OK);
    }

    public ResponseEntity<AuthResponse> logout(String bearerToken) {
        String token = bearerToken.substring(7);
        String rspMessage = "";
        try {
            if (jwtUtils.isTokenValid(token)) {
                log.warn("Token invalido o expirado");
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            rspMessage = "Cierre de sesión exitoso";
            log.info("Cierre de sesión exitoso");
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return new ResponseEntity<>(AuthResponse.success(rspMessage), HttpStatus.OK);
    }

}
