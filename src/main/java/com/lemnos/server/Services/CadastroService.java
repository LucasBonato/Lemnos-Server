package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.Exceptions.Cadastro.CadastroEmailAlreadyInUseException;
import com.lemnos.server.Exceptions.Cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.Models.Cadastro;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Repositories.CadastroRepository;
import com.lemnos.server.Repositories.ClienteRepository;
import com.lemnos.server.Repositories.FornecedorRepository;
import com.lemnos.server.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class CadastroService {
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private FornecedorRepository fornecedorRepository;
    @Autowired private CadastroRepository cadastroRepository;

    public ResponseEntity cadastrarCliente(ClienteDTO clienteDTO) {
        clienteDTO.setEmail(clienteDTO.getEmail().toLowerCase());
        verificarCamposCliente(clienteDTO);
        Cliente cliente = new Cliente(clienteDTO);
        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
        funcionarioDTO.setEmail(funcionarioDTO.getEmail().toLowerCase());
        verificarCamposFuncionario(funcionarioDTO);

        Date dataNascimento = convertData(funcionarioDTO.getDataNascimento());
        Date dataAdmissao = convertData(funcionarioDTO.getDataAdmissao());

        Funcionario funcionario = new Funcionario(funcionarioDTO, dataNascimento, dataAdmissao);
        funcionarioRepository.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity cadastrarFornecedor(FornecedorDTO fornecedorDTO) {
        verificarCamposFornecedor(fornecedorDTO);
        Fornecedor fornecedor = new Fornecedor(fornecedorDTO);
        fornecedorRepository.save(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void verificarCamposCliente(ClienteDTO clienteDTO) {
        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(clienteDTO.getEmail());
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(clienteDTO.getCpf());
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(clienteOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();
    }
    private void verificarCamposFuncionario(FuncionarioDTO funcionarioDTO) {
        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(funcionarioDTO.getEmail());
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(funcionarioDTO.getCpf());
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(funcionarioOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();
    }
    private void verificarCamposFornecedor(FornecedorDTO fornecedorDTO) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findByCnpj(fornecedorDTO.getCnpj());
        if(fornecedorOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();
    }

    private Date convertData(String data) {
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
