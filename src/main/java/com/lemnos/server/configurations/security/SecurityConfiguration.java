package com.lemnos.server.configurations.security;

import com.lemnos.server.models.enums.Roles;
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
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger", "/swagger-ui/**", "/v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/produto", "/produto/desconto", "/produto/{id}", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/produto/find/**", "/auth/login", "/auth/login-firebase", "/auth/register", "/auth/register/verificar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cliente", "/cliente/find", "/endereco", "/pedido/**", "/produto/fav", "/carrinho").hasRole(Roles.CLIENTE.getRole())
                        .requestMatchers(HttpMethod.POST, "/endereco/**", "/pedido", "/produto/fav", "/produto/avaliar/**", "/carrinho").hasRole(Roles.CLIENTE.getRole())
                        .requestMatchers(HttpMethod.PUT, "/cliente", "/endereco", "/pedido").hasRole(Roles.CLIENTE.getRole())
                        .requestMatchers(HttpMethod.DELETE, "/endereco", "/produto/fav", "/carrinho/**").hasRole(Roles.CLIENTE.getRole())
                        .requestMatchers(HttpMethod.GET, "/fornecedor/**", "/funcionario/me").hasRole(Roles.FUNCIONARIO.getRole())
                        .requestMatchers(HttpMethod.POST, "/produto/**", "/auth/register/fornecedor/**").hasRole(Roles.FUNCIONARIO.getRole())
                        .requestMatchers(HttpMethod.PUT, "/produto/**", "/fornecedor").hasRole(Roles.FUNCIONARIO.getRole())
                        .requestMatchers(HttpMethod.DELETE, "/produto/**", "/fornecedor", "/cliente").hasRole(Roles.FUNCIONARIO.getRole())
                        .requestMatchers(HttpMethod.GET, "/funcionario/**").hasRole(Roles.ADMIN.getRole())
                        .requestMatchers(HttpMethod.POST, "/auth/register/funcionario/**").hasRole(Roles.ADMIN.getRole())
                        .requestMatchers(HttpMethod.PUT, "/funcionario/**").hasRole(Roles.ADMIN.getRole())
                        .requestMatchers(HttpMethod.DELETE, "/funcionario").hasRole(Roles.ADMIN.getRole())
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
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("FUNCIONARIO")
                .role("FUNCIONARIO").implies("CLIENTE")
                .build();
    }
    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy hierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(hierarchy);
        return expressionHandler;
    }
}
