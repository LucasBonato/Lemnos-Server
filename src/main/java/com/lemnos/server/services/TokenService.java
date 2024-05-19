package com.lemnos.server.services;

import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Autowired public JwtEncoder jwtEncoder;
    @Autowired public JwtDecoder jwtDecoder;

    public String generateToken(UserDetails userDetails) {
        try {
            String id = "";
            String role = "";

            if(userDetails instanceof Cliente cliente) {
                id = cliente.getId().toString();
                role = cliente.getRole().getRole();
            } else if(userDetails instanceof Funcionario funcionario) {
                id = funcionario.getId().toString();
                role = funcionario.getRole().getRole();
            }

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("Lemnos-Server")
                    .subject(id)
                    .issuedAt(Instant.now())
                    .expiresAt(generateExpirationDate())
                    .claim("role", role)
                    .build();
            return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        } catch (BadJwtException e) {
            throw new RuntimeException("Erro ao criar token!");
        }
    }

    public String validateToken(String token) {
        try {
            return jwtDecoder
                    .decode(token)
                    .getClaim("sub")
                    .toString();
        } catch (BadJwtException e) {
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime
                .now()
                .plusMinutes(5)
                .toInstant(
                    ZoneOffset.of("-03:00")
                );
    }
}
