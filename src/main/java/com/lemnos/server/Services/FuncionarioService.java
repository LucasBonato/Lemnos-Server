package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.Exceptions.Global.UpdateNotValidException;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.Models.Enums.Situacao;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Models.Records.FuncionarioRecord;
import com.lemnos.server.Repositories.FuncionarioRepository;
import com.lemnos.server.Utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FuncionarioService extends Util {

    @Autowired private FuncionarioRepository funcionarioRepository;

    public ResponseEntity<List<FuncionarioRecord>> getAll() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<FuncionarioRecord> dto = new ArrayList<>();
        for(Funcionario funcionario : funcionarios){
            dto.add(new FuncionarioRecord(
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

    public ResponseEntity<FuncionarioRecord> getOneById(Integer id) {
        Funcionario funcionario = getOneFuncionarioById(id);
        FuncionarioRecord record = new FuncionarioRecord(
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

    public ResponseEntity<Void> createEndereco(Integer id, EnderecoDTO enderecoDTO) {
        Funcionario funcionario = getOneFuncionarioById(id);

        Endereco endereco = getEndereco(enderecoDTO);

        FuncionarioPossuiEndereco funcionarioPossuiEndereco = new FuncionarioPossuiEndereco(funcionario, endereco, enderecoDTO.getNumeroLogradouro());
        funcionarioPossuiEnderecoRepository.save(funcionarioPossuiEndereco);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> updateFuncionario(Integer id, FuncionarioDTO funcionarioDTO){
        Funcionario updatedFuncionario = insertData(id, funcionarioDTO);
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

    private Funcionario insertData(Integer id, FuncionarioDTO funcionarioEnviado) {
        Funcionario funcionarioEncontrado = getOneFuncionarioById(id);

        Date dataNasc;
        Date dataAdmi;

        if(StringUtils.isBlank(funcionarioEnviado.getNome()) && (funcionarioEnviado.getCpf() == null || funcionarioEnviado.getCpf().isBlank()) && StringUtils.isBlank(funcionarioEnviado.getDataAdmissao()) && StringUtils.isBlank(funcionarioEnviado.getDataNascimento()) && (funcionarioEnviado.getTelefone() == null || funcionarioEnviado.getTelefone().isBlank())){
            throw new UpdateNotValidException("Funcionário");
        }
        if(StringUtils.isBlank(funcionarioEnviado.getNome())){
            funcionarioEnviado.setNome(funcionarioEncontrado.getNome());
        }
        if(funcionarioEnviado.getCpf() == null || funcionarioEnviado.getCpf().isBlank()){
            funcionarioEnviado.setCpf(funcionarioEncontrado.getCpf().toString());
        }
        Long cpf = convertStringToLong(funcionarioEnviado.getCpf(), CPF);
        if(StringUtils.isBlank(funcionarioEnviado.getDataNascimento())){
            dataNasc = funcionarioEncontrado.getDataNascimento();
        } else {
            dataNasc = convertData(funcionarioEnviado.getDataNascimento());
        }
        if(StringUtils.isBlank(funcionarioEnviado.getDataAdmissao())){
            dataAdmi = funcionarioEncontrado.getDataAdmissao();
        } else {
            dataAdmi = convertData(funcionarioEnviado.getDataAdmissao());
        }
        if(funcionarioEnviado.getTelefone() == null || funcionarioEnviado.getTelefone().isBlank()){
            funcionarioEnviado.setTelefone(funcionarioEncontrado.getTelefone().toString());
        }
        Long telefone = convertStringToLong(funcionarioEnviado.getTelefone(), TELEFONE);

        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(cpf);
        if(funcionarioOptional.isPresent() && !Objects.equals(funcionarioOptional.get().getId(), id)) throw new CadastroCpfAlreadyInUseException();

        Funcionario updatedFuncionario = new Funcionario();
        updatedFuncionario.setId(id);
        updatedFuncionario.setNome(funcionarioEnviado.getNome());
        updatedFuncionario.setCpf(cpf);
        updatedFuncionario.setDataNascimento(dataNasc);
        updatedFuncionario.setDataAdmissao(dataAdmi);
        updatedFuncionario.setTelefone(telefone);
        updatedFuncionario.setCadastro(funcionarioEncontrado.getCadastro());
        updatedFuncionario.setEnderecos(funcionarioEncontrado.getEnderecos());

        return updatedFuncionario;
    }
}

