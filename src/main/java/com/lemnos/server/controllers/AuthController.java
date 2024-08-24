package com.lemnos.server.controllers;

import com.lemnos.server.configurations.swagger.AuthSwagger;
import com.lemnos.server.models.dtos.requests.FireBaseLoginRequest;
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
public class AuthController implements AuthSwagger {

    @Autowired private AuthService authService;

    @PostMapping("/login")
    private ResponseEntity<LoginReponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/login-firebase")
    private ResponseEntity<LoginReponse> loginFirebase(@RequestBody FireBaseLoginRequest fbLoginRequest) {
        return authService.loginFirebase(fbLoginRequest);
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

    @PostMapping("/register/verificar")
    public ResponseEntity<Void> verificarCliente(@RequestBody RegisterRequest registerRequest){
        return authService.verificarCliente(registerRequest);
    }

    @PostMapping("/register/funcionario/verificar")
    public ResponseEntity<Void> verificarFuncionario(@RequestBody FuncionarioRequest funcionarioRequest){
        return authService.verificarFuncionario(funcionarioRequest);
    }

    @PostMapping("/register/fornecedor/verificar")
    public ResponseEntity<Void> verificarFornecedor(@RequestBody FornecedorRequest fornecedorRequest){
        return authService.verificarFornecedor(fornecedorRequest);
    }
}
