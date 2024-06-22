package com.lemnos.server.configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(HttpMethod.GET, "/produto", "/produto/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/produto/find", "/auth/login", "/auth/login-firebase", "/auth/register", "/auth/register/verificar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cliente", "/cliente/find", "/endereco", "/pedido/**", "/produto/fav", "/carrinho").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/endereco/**", "/pedido", "/produto/fav", "/produto/avaliar/**", "/carrinho").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/cliente", "/endereco", "/pedido").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/endereco", "/produto/fav", "/carrinho/**").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/fornecedor/**", "/funcionario/me").hasAuthority("FUNCIONARIO")
                        .requestMatchers(HttpMethod.POST, "/produto/**", "/auth/register/fornecedor/**").hasAuthority("FUNCIONARIO")
                        .requestMatchers(HttpMethod.PUT, "/produto/**", "/fornecedor").hasAuthority("FUNCIONARIO")
                        .requestMatchers(HttpMethod.DELETE, "/produto/**", "/fornecedor", "/cliente").hasAuthority("FUNCIONARIO")
                        .requestMatchers(HttpMethod.GET, "/funcionario/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/auth/register/funcionario/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/funcionario/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/funcionario").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("""
                ADMIN > FUNCIONARIO
                FUNCIONARIO > CLIENTE
            """);
        return hierarchy;
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy hierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(hierarchy);
        return expressionHandler;
    }
}
