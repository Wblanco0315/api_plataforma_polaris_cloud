package org.wposs.plataforma_polaris_cloud.security.services.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.wposs.plataforma_polaris_cloud.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTUtils {

    //Clave privada
    @Value("${security.jwt.key-private}")
    private String privateKey;

    //Nombre de usuario que genera el token
    @Value("${security.jwt.user.generator}")
    private String userGenerator;



    private final UserRepository userRepository;

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(userDetails.getUsername())
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);
    }

    // Desencriptación del JWT
    public DecodedJWT decodeToken(String token) {
        DecodedJWT decodedJWT; //Decodificador del token
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey); //Algoritmo de encriptación
        JWTVerifier verifier = JWT.require(algorithm) //Se pasa el mismo algoritmo que se usó al encriptar
                .withIssuer(this.userGenerator) //Se manda el usuario que genero el token
                .build();
        try {
            decodedJWT = verifier.verify(token);
        } catch (Exception ex) {
            log.error("Invalid Token: {}", ex.getMessage());
            return null;
        }
        return decodedJWT; //Devuelve el token ya desencriptado
    }

    //se obtienen el claim(atributos que contienen el token) especificado
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {

        try {
            return decodedJWT.getClaim(claimName);
        } catch (JWTDecodeException ex) {
            log.error("Error obtaining claim : {}", ex.getMessage());
            return null;
        }
    }

    //Validación del expiración del token
    public Boolean isTokenValid(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            return decodedJWT != null && decodedJWT.getExpiresAt().after(new Date());
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }

    public String getSubject(DecodedJWT decodedJWT) {
        try {
            return decodedJWT.getSubject();
        } catch (JWTDecodeException e) {
            log.error("Error decoding JWT: {}", e.getMessage());
            return null;
        }
    }

}