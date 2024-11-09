package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.UserResponseDTO;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.time.Instant;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(128);

    @Override
    public String generateJwt(UserResponseDTO userResponseDTO) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        return Jwt.issuer("ecommerce-jwt")
                .subject(userResponseDTO.email())
                .groups(userResponseDTO.role())
                .expiresAt(expiryDate)
                .sign();
    }

}
