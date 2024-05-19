package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.auth.LoginRequest;
import com.lemnos.server.models.dtos.requests.auth.RegisterRequest;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.responses.auth.LoginReponse;
import com.lemnos.server.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/login")
    private ResponseEntity<LoginReponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @PostMapping("/register/funcionario")
    public ResponseEntity<Void> cadastrarFuncionario(@RequestBody FuncionarioRequest funcionarioRequest){
        return authService.registerFuncionario(funcionarioRequest);
    }

    @PostMapping("/register/fornecedor")
    public ResponseEntity<Void> cadastrarFornecedor(@RequestBody FornecedorRequest fornecedorRequest){
        return authService.registerFornecedor(fornecedorRequest);
    }
}
