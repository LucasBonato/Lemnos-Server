package com.lemnos.server.services;

import com.lemnos.server.exceptions.cadastro.*;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.Cliente;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.Fornecedor;
import com.lemnos.server.models.Funcionario;
import com.lemnos.server.repositories.CadastroRepository;
import com.lemnos.server.repositories.ClienteRepository;
import com.lemnos.server.repositories.FornecedorRepository;
import com.lemnos.server.repositories.FuncionarioRepository;
import com.lemnos.server.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroService extends Util {
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private FornecedorRepository fornecedorRepository;
    @Autowired private CadastroRepository cadastroRepository;

    public ResponseEntity<Void> cadastrarCliente(ClienteRequest clienteRequest) {
        Cliente cliente = verificarRegraDeNegocio(clienteRequest);

        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> cadastrarFuncionario(FuncionarioRequest funcionarioRequest) {
        Funcionario funcionario = verificarRegraDeNegocio(funcionarioRequest);

        funcionarioRepository.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> cadastrarFornecedor(FornecedorRequest fornecedorRequest) {
        Fornecedor fornecedor = verificarRegraDeNegocio(fornecedorRequest);
        fornecedorRepository.save(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Cliente verificarRegraDeNegocio(ClienteRequest clienteRequest) {

        if(StringUtils.isBlank(clienteRequest.nome())){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome é obrigatório!");
        }
        if(clienteRequest.nome().length() < 2 || clienteRequest.nome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(clienteRequest.cpf() == null || clienteRequest.cpf().isBlank()){
            throw new CadastroNotValidException(Codigo.CPF.ordinal(), "O CPF é obrigatório!");
        }

        Long cpf = convertStringToLong(clienteRequest.cpf(), CPF);

        if(StringUtils.isBlank(clienteRequest.email())){
            throw new CadastroNotValidException(Codigo.EMAIL.ordinal(), "O Email é obrigatório!");
        }
        if(StringUtils.isBlank(clienteRequest.senha())){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha é obrigatória!");
        }
        if(clienteRequest.senha().length() < 8 || clienteRequest.nome().length() > 16){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha precisa ter mínimo 8 caracteres!");
        }

        clienteRequest = clienteRequest.setEmail(clienteRequest.email().toLowerCase());

        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(clienteRequest.email());
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(clienteOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();

        return new Cliente(clienteRequest);
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

        Long cpf = convertStringToLong(funcionarioRequest.cpf(), CPF);

        if(StringUtils.isBlank(funcionarioRequest.dataNascimento())){
            throw new CadastroNotValidException(Codigo.DATANASC.ordinal(), "A Data de Nascimento é obrigatória!");
        }
        if(StringUtils.isBlank(funcionarioRequest.dataAdmissao())){
            throw new CadastroNotValidException(Codigo.DATAADMI.ordinal(), "A Data de Admissão é obrigatória!");
        }
        if(funcionarioRequest.telefone() == null || funcionarioRequest.telefone().isBlank()){
            throw new CadastroNotValidException(Codigo.TELEFONE.ordinal(), "Telefone é obrigatório");
        }

        convertStringToLong(funcionarioRequest.telefone(), TELEFONE);

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

        Long cnpj = convertStringToLong(fornecedorRequest.cnpj(), CNPJ);

        if(fornecedorRequest.telefone() == null || fornecedorRequest.telefone().isBlank()){
            throw new CadastroNotValidException(Codigo.TELEFONE.ordinal(), "O Telefone é obrigatório!");
        }

        convertStringToLong(fornecedorRequest.telefone(), TELEFONE);

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
