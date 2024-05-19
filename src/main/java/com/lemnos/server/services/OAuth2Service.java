package com.lemnos.server.services;

import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.dtos.responses.auth.LoginReponse;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.repositories.entidades.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2Service {
    @Autowired private TokenService tokenService;
    @Autowired private CadastroRepository cadastroRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired protected ClienteRepository clienteRepository;
    @Autowired protected FuncionarioRepository funcionarioRepository;

    public ResponseEntity<LoginReponse> login(OAuth2AuthenticationToken OAuthToken) {
        String token = "";
        if(OAuthToken.getAuthorizedClientRegistrationId().equals("google")){
            UserDetails userOAuthGoogle = verificarLogin(OAuthToken);
            token = tokenService.generateToken(userOAuthGoogle);
        }

        return ResponseEntity.ok(new LoginReponse(token));
    }

    private UserDetails verificarLogin(OAuth2AuthenticationToken OAuthToken) {
        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(OAuthToken.getPrincipal().getAttributes().get("email").toString());
        String email = OAuthToken.getPrincipal().getAttributes().get("email").toString();
        if (cadastroOptional.isEmpty() && (email.equals("lucas.perez.bonato@gmail.com") || email.equals("lucasatdriano@gmail.com") || email.equals("leandrofamiliafox@gmail.com"))) {
            funcionarioRepository.save(new Funcionario(OAuthToken, passwordEncoder.encode(OAuthToken.getPrincipal().getAttribute("sub"))));
        } else {
            clienteRepository.save(new Cliente(OAuthToken, passwordEncoder.encode(OAuthToken.getPrincipal().getAttribute("sub"))));
        }
        if (!cadastroOptional.get().isLoginCorrect(OAuthToken.getPrincipal().getAttributes().get("sub").toString(), passwordEncoder)) {
            throw new RuntimeException("Email ou senha errados!");
        }

        cadastroOptional = cadastroRepository.findByEmail(email);

        Optional<Cliente> clienteOptional = clienteRepository.findByCadastro(cadastroOptional.get());
        if (clienteOptional.isPresent()) {
            return clienteOptional.get();
        }
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCadastro(cadastroOptional.get());
        if(funcionarioOptional.isPresent()) {
            return funcionarioOptional.get();
        }
        throw new RuntimeException("Usuário não encontrado");
    }
}
