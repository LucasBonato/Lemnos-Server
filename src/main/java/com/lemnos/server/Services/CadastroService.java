package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.*;
import com.lemnos.server.Models.Cadastro.Cadastro;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Repositories.CadastroRepository;
import com.lemnos.server.Repositories.ClienteRepository;
import com.lemnos.server.Repositories.FornecedorRepository;
import com.lemnos.server.Repositories.FuncionarioRepository;
import com.lemnos.server.Utils.Util;
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

    public ResponseEntity cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = verificarRegraDeNegocio(clienteDTO);

        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = verificarRegraDeNegocio(funcionarioDTO);

        funcionarioRepository.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity cadastrarFornecedor(FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = verificarRegraDeNegocio(fornecedorDTO);
        fornecedorRepository.save(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Cliente verificarRegraDeNegocio(ClienteDTO clienteDTO) {

        if(StringUtils.isBlank(clienteDTO.getNome())){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome é obrigatório!");
        }
        if(clienteDTO.getNome().length() < 2 || clienteDTO.getNome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(clienteDTO.getCpf() == null || clienteDTO.getCpf().isBlank()){
            throw new CadastroNotValidException(Codigo.CPF.ordinal(), "O CPF é obrigatório!");
        }

        Long cpf = convertStringToLong(clienteDTO.getCpf(), CPF);

        if(StringUtils.isBlank(clienteDTO.getEmail())){
            throw new CadastroNotValidException(Codigo.EMAIL.ordinal(), "O Email é obrigatório!");
        }
        if(StringUtils.isBlank(clienteDTO.getSenha())){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha é obrigatória!");
        }
        if(clienteDTO.getSenha().length() < 8 || clienteDTO.getNome().length() > 16){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha precisa ter mínimo 8 caracteres!");
        }

        clienteDTO.setEmail(clienteDTO.getEmail().toLowerCase());

        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(clienteDTO.getEmail());
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(clienteOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();

        return new Cliente(clienteDTO);
    }
    private Funcionario verificarRegraDeNegocio(FuncionarioDTO funcionarioDTO) {

        if(StringUtils.isBlank(funcionarioDTO.getNome())){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome é obrigatório!");
        }
        if(funcionarioDTO.getNome().length() < 2 || funcionarioDTO.getNome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(funcionarioDTO.getCpf() == null || funcionarioDTO.getCpf().isBlank()){
            throw new CadastroNotValidException(Codigo.CPF.ordinal(), "O CPF é obrigatório!");
        }

        Long cpf = convertStringToLong(funcionarioDTO.getCpf(), CPF);

        if(StringUtils.isBlank(funcionarioDTO.getDataNascimento())){
            throw new CadastroNotValidException(Codigo.DATANASC.ordinal(), "A Data de Nascimento é obrigatória!");
        }
        if(StringUtils.isBlank(funcionarioDTO.getDataAdmissao())){
            throw new CadastroNotValidException(Codigo.DATAADMI.ordinal(), "A Data de Admissão é obrigatória!");
        }
        if(funcionarioDTO.getTelefone() == null || funcionarioDTO.getTelefone().isBlank()){
            throw new CadastroNotValidException(Codigo.TELEFONE.ordinal(), "Telefone é obrigatório");
        }

        convertStringToLong(funcionarioDTO.getTelefone(), TELEFONE);

        if(StringUtils.isBlank(funcionarioDTO.getEmail())){
            throw new CadastroNotValidException(Codigo.EMAIL.ordinal(), "O Email é obrigatório!");
        }
        if(StringUtils.isBlank(funcionarioDTO.getSenha())){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha é obrigatória!");
        }
        if(funcionarioDTO.getSenha().length() < 8 || funcionarioDTO.getSenha().length() > 16){
            throw new CadastroNotValidException(Codigo.SENHA.ordinal(), "A Senha precisa ter mínimo 8 caracteres!");
        }
        
        funcionarioDTO.setEmail(funcionarioDTO.getEmail().toLowerCase());

        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(funcionarioDTO.getEmail());
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(cpf);
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(funcionarioOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();

        return new Funcionario(
                funcionarioDTO,
                convertData(funcionarioDTO.getDataNascimento()),
                convertData(funcionarioDTO.getDataAdmissao())
        );
    }
    private Fornecedor verificarRegraDeNegocio(FornecedorDTO fornecedorDTO) {

        if(StringUtils.isBlank(fornecedorDTO.getNome())){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome é obrigatório!");
        }
        if(fornecedorDTO.getNome().length() < 2 || fornecedorDTO.getNome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(fornecedorDTO.getCnpj() == null || fornecedorDTO.getCnpj().isBlank()){
            throw new CadastroNotValidException(Codigo.CNPJ.ordinal(), "O CNPJ é obrigatório!");
        }

        Long cnpj = convertStringToLong(fornecedorDTO.getCnpj(), CNPJ);

        if(fornecedorDTO.getTelefone() == null || fornecedorDTO.getTelefone().isBlank()){
            throw new CadastroNotValidException(Codigo.TELEFONE.ordinal(), "O Telefone é obrigatório!");
        }

        convertStringToLong(fornecedorDTO.getTelefone(), TELEFONE);

        if(fornecedorDTO.getNumeroLogradouro() == null){
            throw new CadastroNotValidException(Codigo.GLOBAL.ordinal(), "O Número do Logradouro é obrigatório!");
        }
        if(fornecedorDTO.getNumeroLogradouro() < 1 || fornecedorDTO.getNumeroLogradouro() > 9999) {
            throw new CadastroNotValidException(Codigo.GLOBAL.ordinal(), "Número do logradouro muito alto ou muito baixo! (1, 9999)");
        }
        if(StringUtils.isBlank(fornecedorDTO.getEmail())){
            throw new CadastroNotValidException(Codigo.EMAIL.ordinal(), "O Email é obrigatório!");
        }

        Optional<Fornecedor> Optionalmail = fornecedorRepository.findByEmail(fornecedorDTO.getEmail());
        Optional<Fornecedor> OptionalCnpj = fornecedorRepository.findByCnpj(cnpj);
        if(Optionalmail.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(OptionalCnpj.isPresent()) throw new CadastroCnpjAlreadyInUseException();

        return new Fornecedor(fornecedorDTO);
    }
}
