package com.lemnos.server.Models.Cadastro;

import com.lemnos.server.Exceptions.Cadastro.*;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Repositories.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class VerificationUtils {
    @Autowired protected ClienteRepository clienteRepository;
    @Autowired protected FuncionarioRepository funcionarioRepository;
    @Autowired protected FornecedorRepository fornecedorRepository;
    @Autowired protected CadastroRepository cadastroRepository;

    protected Cliente verificarRegraDeNegocio(ClienteDTO clienteDTO) {
        if(StringUtils.isBlank(clienteDTO.getNome())){
            throw new CadastroNotValidException("O Nome é obrigatório!");
        }
        if(clienteDTO.getNome().length() < 2 || clienteDTO.getNome().length() > 40){
            throw new CadastroNotValidException("O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(StringUtils.isBlank(clienteDTO.getCpf())){
            throw new CadastroNotValidException("O CPF é obrigatório!");
        }
        if(StringUtils.isBlank(clienteDTO.getEmail())){
            throw new CadastroNotValidException("O Email é obrigatório!");
        }
        if(StringUtils.isBlank(clienteDTO.getSenha())){
            throw new CadastroNotValidException("A Senha é obrigatória!");
        }
        if(clienteDTO.getSenha().length() < 8 || clienteDTO.getNome().length() > 16){
            throw new CadastroNotValidException("O Nome precisa ter mínimo 8 caracteres!");
        }

        clienteDTO.setEmail(clienteDTO.getEmail().toLowerCase());

        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(clienteDTO.getEmail());
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(clienteDTO.getCpf());
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(clienteOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();

        return new Cliente(clienteDTO);
    }
    protected Funcionario verificarRegraDeNegocio(FuncionarioDTO funcionarioDTO) {
        if(StringUtils.isBlank(funcionarioDTO.getNome())){
            throw new CadastroNotValidException("O Nome é obrigatório!");
        }
        if(funcionarioDTO.getNome().length() < 2 || funcionarioDTO.getNome().length() > 40){
            throw new CadastroNotValidException("O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(StringUtils.isBlank(funcionarioDTO.getCpf())){
            throw new CadastroNotValidException("O CPF é obrigatório!");
        }
        if(StringUtils.isBlank(funcionarioDTO.getDataNascimento())){
            throw new CadastroNotValidException("A Data de Nascimento é obrigatória!");
        }
        if(StringUtils.isBlank(funcionarioDTO.getDataAdmissao())){
            throw new CadastroNotValidException("A Data de Admissão é obrigatória!");
        }
        if(funcionarioDTO.getTelefone().length() != 11){
            throw new CadastroNotValidException("Telefone inválido! (XX)XXXXX-XXXX");
        }
        if(funcionarioDTO.getCep().length() != 8){
            throw new CadastroNotValidException("CEP inválido! XXXXX-XXX");
        }
        if(funcionarioDTO.getNumeroLogradouro() == null){
            throw new CadastroNotValidException("O Número do Logradouro é obrigatório!");
        }
        if(funcionarioDTO.getNumeroLogradouro() < 1 || funcionarioDTO.getNumeroLogradouro() > 9999) {
            throw new CadastroNotValidException("Número do logradouro muito alto ou muito baixo! (1, 9999)");
        }
        if(StringUtils.isBlank(funcionarioDTO.getEmail())){
            throw new CadastroNotValidException("O Email é obrigatório!");
        }
        if(StringUtils.isBlank(funcionarioDTO.getSenha())){
            throw new CadastroNotValidException("A Senha é obrigatória!");
        }
        if(funcionarioDTO.getSenha().length() < 8 || funcionarioDTO.getSenha().length() > 16){
            throw new CadastroNotValidException("A Senha precisa ter mínimo 8 caracteres!");
        }
        funcionarioDTO.setEmail(funcionarioDTO.getEmail().toLowerCase());

        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(funcionarioDTO.getEmail());
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(funcionarioDTO.getCpf());
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(clienteOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();

        return new Funcionario(
                funcionarioDTO,
                convertData(funcionarioDTO.getDataNascimento()),
                convertData(funcionarioDTO.getDataAdmissao())
        );
    }
    protected Fornecedor verificarRegraDeNegocio(FornecedorDTO fornecedorDTO) {
        if(StringUtils.isBlank(fornecedorDTO.getNome())){
            throw new CadastroNotValidException("O Nome é obrigatório!");
        }
        if(fornecedorDTO.getNome().length() < 2 || fornecedorDTO.getNome().length() > 40){
            throw new CadastroNotValidException("O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(StringUtils.isBlank(fornecedorDTO.getCnpj())){
            throw new CadastroNotValidException("O CNPJ é obrigatório!");
        }
        if(fornecedorDTO.getTelefone().length() != 11){
            throw new CadastroNotValidException("Telefone inválido! (XX)XXXXX-XXXX");
        }
        if(fornecedorDTO.getNumeroLogradouro() == null){
            throw new CadastroNotValidException("O Número do Logradouro é obrigatório!");
        }
        if(fornecedorDTO.getNumeroLogradouro() < 1 || fornecedorDTO.getNumeroLogradouro() > 9999) {
            throw new CadastroNotValidException("Número do logradouro muito alto ou muito baixo! (1, 9999)");
        }
        if(StringUtils.isBlank(fornecedorDTO.getEmail())){
            throw new CadastroNotValidException("O Email é obrigatório!");
        }
        if(fornecedorDTO.getCep().length() != 8){
            throw new CadastroNotValidException("CEP inválido! XXXXX-XXX");
        }

        Optional<Fornecedor> email = fornecedorRepository.findByEmail(fornecedorDTO.getEmail());
        Optional<Fornecedor> cnpj = fornecedorRepository.findByCnpj(fornecedorDTO.getCnpj());
        if(email.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(cnpj.isPresent()) throw new CadastroCpfAlreadyInUseException();

        return new Fornecedor(fornecedorDTO);
    }

    protected Date convertData(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada;
        try{
            dataFormatada = formatter.parse(data);
        }catch (ParseException e){
            throw new CadastroWrongDataFormatException();
        }
        return dataFormatada;
    }
}
