package org.wposs.plataforma_polaris_cloud.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wposs.plataforma_polaris_cloud.models.auth.UserEntity;
import org.wposs.plataforma_polaris_cloud.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Busca los roles del usuario
        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        // Busca los permisos de cada rol
        userEntity.getRoles().stream().flatMap(role -> role.getPermissions().stream()).forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList);
    }
}