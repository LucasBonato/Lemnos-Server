package com.lemnos.server.services;

import com.google.api.gax.rpc.StatusCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.lemnos.server.exceptions.cadastro.*;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.dtos.requests.FireBaseLoginRequest;
import com.lemnos.server.models.dtos.requests.auth.LoginRequest;
import com.lemnos.server.models.dtos.responses.auth.LoginReponse;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.dtos.requests.auth.RegisterRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.repositories.entidades.FornecedorRepository;
import com.lemnos.server.repositories.entidades.FuncionarioRepository;
import com.lemnos.server.repositories.produto.ProdutoRepository;
import com.lemnos.server.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends Util {
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private FornecedorRepository fornecedorRepository;
    @Autowired private CadastroRepository cadastroRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private TokenService tokenService;

    public ResponseEntity<LoginReponse> login(LoginRequest loginRequest) {
        UserDetails userDetails = verificarLogin(loginRequest);
        String token = tokenService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginReponse(token));
    }

    public ResponseEntity<LoginReponse> loginFirebase(FireBaseLoginRequest fbLoginRequest) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(fbLoginRequest.token());
            UserDetails userDetails = verificarLogin(decodedToken.getEmail(), decodedToken.getUid());
            if(userDetails == null) {
                userDetails = newClienteFirebase(decodedToken);
            }
            String token = tokenService.generateToken(userDetails);
            return ResponseEntity.ok(new LoginReponse(token));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private UserDetails newClienteFirebase(FirebaseToken decodedToken) {
        if (decodedToken.getEmail().equals("lucas.perez.bonato@gmail.com") || decodedToken.getEmail().equals("lucasatdriano@gmail.com") || decodedToken.getEmail().equals("leandrofamiliafox@gmail.com")) {
            return funcionarioRepository.save(new Funcionario(decodedToken, passwordEncoder.encode(decodedToken.getUid())));
        }
        return clienteRepository.save(new Cliente(decodedToken, passwordEncoder.encode(decodedToken.getEmail())));
    }

    public ResponseEntity<Void> register(RegisterRequest registerRequest) {
        Cliente cliente = verificarRegraDeNegocio(registerRequest);

        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> registerFuncionario(FuncionarioRequest funcionarioRequest) {
        Funcionario funcionario = verificarRegraDeNegocio(funcionarioRequest);

        funcionarioRepository.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> registerFornecedor(FornecedorRequest fornecedorRequest) {
        Fornecedor fornecedor = verificarRegraDeNegocio(fornecedorRequest);

        fornecedorRepository.save(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private UserDetails verificarLogin(LoginRequest loginRequest) {
        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(loginRequest.email());
        if (cadastroOptional.isEmpty() || !cadastroOptional.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new RuntimeException("Email ou senha inválidos");
        }
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
    private UserDetails verificarLogin(String email, String uid) {
        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(email);
        if (cadastroOptional.isEmpty() || !cadastroOptional.get().isLoginCorrect(uid, passwordEncoder)) {
            return null;
        }
        Optional<Cliente> clienteOptional = clienteRepository.findByCadastro(cadastroOptional.get());
        if (clienteOptional.isPresent()) {
            return clienteOptional.get();
        }
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCadastro(cadastroOptional.get());
        return funcionarioOptional.orElse(null);
    }

    private Cliente verificarRegraDeNegocio(RegisterRequest registerRequest) {

        if(StringUtils.isBlank(registerRequest.getNome())){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome é obrigatório!");
        }
        if(registerRequest.getNome().length() < 2 || registerRequest.getNome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(registerRequest.getCpf() == null || registerRequest.getCpf().isBlank()){
            throw new CadastroNotValidException(Codigo.CPF.ordinal(), "O CPF é obrigatório!");
        }

        Long cpf = convertStringToLong(registerRequest.getCpf(), Codigo.CPF);

        if(StringUtils.isBlank(registerRequest.getEmail())){
            throw new CadastroNotValidException(Codigo.EMAIL.ordinal(), "O Email é obrigatório!");
        }
        if(StringUtils.isBlank(registerRequest.getSenha())){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha é obrigatória!");
        }
        if(registerRequest.getSenha().length() < 8 || registerRequest.getSenha().length() > 16){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha precisa ter mínimo 8 caracteres!");
        }

        registerRequest.setEmail(registerRequest.getEmail().toLowerCase());

        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(registerRequest.getEmail());
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(clienteOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();

        registerRequest.setSenha(passwordEncoder.encode(registerRequest.getSenha()));

        return new Cliente(registerRequest);
    }
    private Funcionario verificarRegraDeNegocio(FuncionarioRequest funcionarioRequest) {

        if(StringUtils.isBlank(funcionarioRequest.nome())){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome é obrigatório!");
        }
        if(funcionarioRequest.nome().length() < 2 || funcionarioRequest.nome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(funcionarioRequest.cpf() == null || funcionarioRequest.cpf().isBlank()){
            throw new CadastroNotValidException(Codigo.CPF.ordinal(), "O CPF é obrigatório!");
        }

        Long cpf = convertStringToLong(funcionarioRequest.cpf(), Codigo.CPF);

        if(StringUtils.isBlank(funcionarioRequest.dataNascimento())){
            throw new CadastroNotValidException(Codigo.DATANASC.ordinal(), "A Data de Nascimento é obrigatória!");
        }
        if(StringUtils.isBlank(funcionarioRequest.dataAdmissao())){
            throw new CadastroNotValidException(Codigo.DATAADMI.ordinal(), "A Data de Admissão é obrigatória!");
        }
        if(funcionarioRequest.telefone() == null || funcionarioRequest.telefone().isBlank()){
            throw new CadastroNotValidException(Codigo.TELEFONE.ordinal(), "Telefone é obrigatório");
        }

        convertStringToLong(funcionarioRequest.telefone(), Codigo.TELEFONE);

        if(StringUtils.isBlank(funcionarioRequest.email())){
            throw new CadastroNotValidException(Codigo.EMAIL.ordinal(), "O Email é obrigatório!");
        }
        if(StringUtils.isBlank(funcionarioRequest.senha())){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha é obrigatória!");
        }
        if(funcionarioRequest.senha().length() < 8 || funcionarioRequest.senha().length() > 16){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha precisa ter mínimo 8 caracteres!");
        }

        funcionarioRequest = funcionarioRequest.setEmail(funcionarioRequest.email().toLowerCase());

        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(funcionarioRequest.email());
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(cpf);
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(funcionarioOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();

        return new Funcionario(
                funcionarioRequest,
                convertData(funcionarioRequest.dataNascimento()),
                convertData(funcionarioRequest.dataAdmissao())
        );
    }
    private Fornecedor verificarRegraDeNegocio(FornecedorRequest fornecedorRequest) {

        if(StringUtils.isBlank(fornecedorRequest.nome())){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome é obrigatório!");
        }
        if(fornecedorRequest.nome().length() < 2 || fornecedorRequest.nome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(fornecedorRequest.cnpj() == null || fornecedorRequest.cnpj().isBlank()){
            throw new CadastroNotValidException(Codigo.CNPJ.ordinal(), "O CNPJ é obrigatório!");
        }

        Long cnpj = convertStringToLong(fornecedorRequest.cnpj(), Codigo.CNPJ);

        if(fornecedorRequest.telefone() == null || fornecedorRequest.telefone().isBlank()){
            throw new CadastroNotValidException(Codigo.TELEFONE.ordinal(), "O Telefone é obrigatório!");
        }

        convertStringToLong(fornecedorRequest.telefone(), Codigo.TELEFONE);

        if(StringUtils.isBlank(fornecedorRequest.email())){
            throw new CadastroNotValidException(Codigo.EMAIL.ordinal(), "O Email é obrigatório!");
        }

        Optional<Fornecedor> Optionalmail = fornecedorRepository.findByEmail(fornecedorRequest.email());
        Optional<Fornecedor> OptionalCnpj = fornecedorRepository.findByCnpj(cnpj);
        if(Optionalmail.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(OptionalCnpj.isPresent()) throw new CadastroCnpjAlreadyInUseException();

        return new Fornecedor(fornecedorRequest);
    }
}