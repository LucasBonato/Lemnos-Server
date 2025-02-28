package com.lemnos.server.configurations.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfiguration {
    @Value("${jwt.spring.oauth2.private-key}")
    private RSAPrivateKey privateKey;

    @Value("${jwt.spring.oauth2.public-key}")
    private RSAPublicKey publicKey;

    @Bean
    public JwtEncoder encoder() {
        JWK jwk = new RSAKey
                .Builder(publicKey)
                .privateKey(privateKey)
                .build();
        ImmutableJWKSet<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder decoder() {
        return NimbusJwtDecoder
                .withPublicKey(publicKey)
                .build();
    }
}
