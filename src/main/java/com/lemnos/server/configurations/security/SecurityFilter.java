package com.lemnos.server.configurations.security;

import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.repositories.entidades.FuncionarioRepository;
import com.lemnos.server.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired private TokenService tokenService;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private FuncionarioRepository funcionarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
        String id = tokenService.validateToken(token);

        if(id != null) {
            Jwt validatedToken = tokenService.jwtDecoder.decode(token);
            UserDetails userDetails = findById(id);
            System.out.println(userDetails);
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(validatedToken, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private UserDetails findById(String id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(Integer.valueOf(id));
        if (clienteOptional.isPresent()) {
            return clienteOptional.get();
        }
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(Integer.valueOf(id));
        if(funcionarioOptional.isPresent()) {
            return funcionarioOptional.get();
        }
        throw new RuntimeException("Usuário não encontrado!");
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
