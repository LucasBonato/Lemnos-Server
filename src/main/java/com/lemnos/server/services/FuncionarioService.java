package com.lemnos.server.services;

import com.lemnos.server.exceptions.cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.exceptions.endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.exceptions.global.UpdateNotValidException;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.enums.Situacao;
import com.lemnos.server.models.Funcionario;
import com.lemnos.server.models.dtos.responses.FuncionarioResponse;
import com.lemnos.server.repositories.FuncionarioRepository;
import com.lemnos.server.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FuncionarioService extends Util {

    @Autowired private FuncionarioRepository funcionarioRepository;

    public ResponseEntity<List<FuncionarioResponse>> getAll() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<FuncionarioResponse> dto = new ArrayList<>();
        for(Funcionario funcionario : funcionarios){
            dto.add(new FuncionarioResponse(
                    funcionario.getNome(),
                    funcionario.getCpf(),
                    funcionario.getDataNascimento(),
                    funcionario.getDataAdmissao(),
                    funcionario.getTelefone(),
                    funcionario.getCadastro().getEmail(),
                    funcionario.getCadastro().getSenha(),
                    getEnderecoRecords(funcionario)
            ));
        }
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<FuncionarioResponse> getOneById(Integer id) {
        Funcionario funcionario = getOneFuncionarioById(id);
        FuncionarioResponse record = new FuncionarioResponse(
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getDataNascimento(),
                funcionario.getDataAdmissao(),
                funcionario.getTelefone(),
                funcionario.getCadastro().getEmail(),
                funcionario.getCadastro().getSenha(),
                getEnderecoRecords(funcionario)
        );
        return ResponseEntity.ok(record);
    }

    public ResponseEntity<Void> createEndereco(Integer id, EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioById(id);

        Endereco endereco = getEndereco(enderecoRequest);

        Optional<FuncionarioPossuiEndereco> optionalFpe = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), id);
        if(optionalFpe.isPresent()){
            throw new EntityAlreadyHasEnderecoException("Funcionário");
        }

        FuncionarioPossuiEndereco funcionarioPossuiEndereco = new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento());
        funcionarioPossuiEnderecoRepository.save(funcionarioPossuiEndereco);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> updateFuncionario(Integer id, FuncionarioRequest funcionarioRequest){
        Funcionario updatedFuncionario = insertData(id, funcionarioRequest);
        funcionarioRepository.save(updatedFuncionario);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteById(Integer id){
        Funcionario funcionarioDeletado = getOneFuncionarioById(id);

        if(funcionarioDeletado.getSituacao() == Situacao.ATIVO) {
            funcionarioDeletado.setSituacao(Situacao.INATIVO);
            funcionarioRepository.save(funcionarioDeletado);
        }
        return ResponseEntity.noContent().build();
    }

    private Funcionario insertData(Integer id, FuncionarioRequest funcionarioEnviado) {
        Funcionario funcionarioEncontrado = getOneFuncionarioById(id);

        Date dataNasc;
        Date dataAdmi;

        if(StringUtils.isBlank(funcionarioEnviado.nome()) && StringUtils.isBlank(funcionarioEnviado.cpf()) && StringUtils.isBlank(funcionarioEnviado.dataAdmissao()) && StringUtils.isBlank(funcionarioEnviado.dataNascimento()) && StringUtils.isBlank(funcionarioEnviado.telefone())){
            throw new UpdateNotValidException("Funcionário");
        }
        if(StringUtils.isBlank(funcionarioEnviado.nome())){
            funcionarioEnviado = funcionarioEnviado.setNome(funcionarioEncontrado.getNome());
        }
        if(StringUtils.isBlank(funcionarioEnviado.cpf())){
            funcionarioEnviado = funcionarioEnviado.setCpf(funcionarioEncontrado.getCpf().toString());
        }
        Long cpf = convertStringToLong(funcionarioEnviado.cpf(), CPF);
        if(StringUtils.isBlank(funcionarioEnviado.dataNascimento())){
            dataNasc = funcionarioEncontrado.getDataNascimento();
        } else {
            dataNasc = convertData(funcionarioEnviado.dataNascimento());
        }
        if(StringUtils.isBlank(funcionarioEnviado.dataAdmissao())){
            dataAdmi = funcionarioEncontrado.getDataAdmissao();
        } else {
            dataAdmi = convertData(funcionarioEnviado.dataAdmissao());
        }
        if(StringUtils.isBlank(funcionarioEnviado.telefone())){
            funcionarioEnviado = funcionarioEnviado.setTelefone(funcionarioEncontrado.getTelefone().toString());
        }
        Long telefone = convertStringToLong(funcionarioEnviado.telefone(), TELEFONE);

        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(cpf);
        if(funcionarioOptional.isPresent() && !Objects.equals(funcionarioOptional.get().getId(), id)) throw new CadastroCpfAlreadyInUseException();

        Funcionario updatedFuncionario = new Funcionario();
        updatedFuncionario.setId(id);
        updatedFuncionario.setNome(funcionarioEnviado.nome());
        updatedFuncionario.setCpf(cpf);
        updatedFuncionario.setDataNascimento(dataNasc);
        updatedFuncionario.setDataAdmissao(dataAdmi);
        updatedFuncionario.setTelefone(telefone);
        updatedFuncionario.setCadastro(funcionarioEncontrado.getCadastro());
        updatedFuncionario.setEnderecos(funcionarioEncontrado.getEnderecos());

        return updatedFuncionario;
    }
}

