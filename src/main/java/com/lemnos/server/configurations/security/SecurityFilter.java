package com.lemnos.server.configurations.security;

import com.lemnos.server.exceptions.auth.UsuarioNotFoundException;
import com.lemnos.server.exceptions.entidades.cliente.ClienteNotFoundException;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.repositories.entidades.FuncionarioRepository;
import com.lemnos.server.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
    @Autowired private CadastroRepository cadastroRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
        String email = tokenService.validateToken(token);

        if (email != null) {
            Jwt validatedToken = tokenService.jwtDecoder.decode(token);
            UserDetails userDetails = findByEmail(email);
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(validatedToken, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private UserDetails findByEmail(String email) {
        Optional<Cliente> clienteOptional = clienteRepository.findByCadastro(getCadastro(email));
        if (clienteOptional.isPresent()) {
            return clienteOptional.get();
        }
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCadastro(getCadastro(email));
        if(funcionarioOptional.isPresent()) {
            return funcionarioOptional.get();
        }
        throw new UsuarioNotFoundException();
    }

    private Cadastro getCadastro(String email) {
        return cadastroRepository.findByEmail(email).orElseThrow(ClienteNotFoundException::new);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        if(authHeader.startsWith("Bearer "))
            return authHeader.substring(7);
        return authHeader;
    }
}
