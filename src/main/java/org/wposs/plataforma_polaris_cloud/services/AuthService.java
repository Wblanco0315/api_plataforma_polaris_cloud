package org.wposs.plataforma_polaris_cloud.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {

        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new AuthException("Usuario No encontrado"));
        } catch (UsernameNotFoundException | AuthException e) {//No encontro al usuario, arroja la exeption
            throw new RuntimeException(e.getMessage());
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>(); //Carga la lista de permisos

        try {
            // Busca los roles asignados en el registro
            userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

            //Envia como respuesta al usuario con sus atributos
            return new User(userEntity.getEmail(), userEntity.getPassword(), userEntity.isEnabled(), userEntity.isAccountNonExpired(), userEntity.isCredentialsNonExpired(), userEntity.isAccountNonLocked(), authorityList);
        } catch (IllegalArgumentException e) {// Atrapa la exception al no encontrar rol/roles asignados
            throw new RuntimeException("Rol/roles no existentes");
        }

    }

    //Autenticacion
    public Authentication authentication(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);
        if (userDetails == null) {//Si la cedula no esta registrada genera una exception
            throw new BadCredentialsException("El usuario " + email + "no existe");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {//si las contraseñas no coincides arroja la exception
            throw new BadCredentialsException("Password incorrecto");
        }

        //Genera la autorizacion
        return new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
    }

    //Inicio de sesion
    public AuthResponse loginUser(LoginRequest request) {
        String email = request.email();
        String password = request.password();
        String accessToken = "";
        Authentication authentication = this.authentication(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication); //Crea la sesion autorizando al usuario.

        accessToken = jwtUtils.createToken(authentication);//Crea el token de autentificacion con los datos del usuario
        AuthResponse response = new AuthResponse(accessToken, true);

        return response;
    }


    public AuthResponse register(RegisterRequest request) {

        List<String> roleRequest = request.roles().roleListName();
        System.out.println("Roles:" + roleRequest);
        Set<RoleEntity> roleEntityList;

        try {//Prueba si la lista de roles existe
            roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());//guarda los roles del usuario en una lista
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Rol/Roles invalidos ");
        }
        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        } //Verifica si se asignaron roles

        UserEntity newUser = UserEntity.builder().email(request.email()).name(request.username()).password(passwordEncoder.encode(request.password())).roles(roleEntityList).isEnabled(true).isAccountNonExpired(true).isCredentialsNonExpired(true).isAccountNonLocked(true).build();

        userRepository.save(newUser);

        Authentication authentication = this.authentication(request.email(), request.password());
        SecurityContextHolder.getContext().setAuthentication(authentication); //Crea la sesion autorizando al usuario.
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponse response = new AuthResponse(accessToken, true);

        return response;
    }

    public AuthResponse restorePassword(LoginRequest request) {
        AuthResponse response = null;
        Optional<UserEntity> userEntity = null;
        String encodedNewPassword = passwordEncoder.encode(request.password());

        try {
            userEntity = userRepository.findUserEntityByEmail(request.email());
            if (userEntity.isEmpty()) {
                System.out.println("Usuario no encontrado");
                response = new AuthResponse(null, false);
                return response;
            }

            userEntity.get().setPassword(encodedNewPassword);
            userRepository.save(userEntity.get());
            response = loginUser(request);

        } catch (Exception e) {
            System.out.println("Error al restaurar password: " + e.getMessage());
            return new AuthResponse(null, false);
        }
        return response;
    }

    public ResponseEntity<AuthResponse> logout(String bearerToken) {
        String token = bearerToken.substring(7);
        AuthResponse response = null;
        try {
            DecodedJWT decodedJWT = jwtUtils.validateToken(token);
            if (decodedJWT == null) {
                return ResponseEntity.badRequest().body(new AuthResponse("Invalid token", false));
            }
        } catch (Exception e) {
            System.out.println("Error al validar el token: " + e.getMessage());
        } finally {
            response = new AuthResponse("Logout successful", true);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return ResponseEntity.ok(response);
    }

    public boolean validateToken(String token) {
        try {
            return jwtUtils.isTokenValid(token);
        } catch (Exception e) {
            return false;
        }
    }
}
