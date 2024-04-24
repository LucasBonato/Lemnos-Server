package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.Exceptions.Funcionario.FuncionarioNotFoundException;
import com.lemnos.server.Exceptions.Global.UpdateNotValidException;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Repositories.FuncionarioRepository;
import com.lemnos.server.Utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService extends Util {

    @Autowired private FuncionarioRepository funcionarioRepository;

    public ResponseEntity<List<Funcionario>> getAll() {
        return ResponseEntity.ok(funcionarioRepository.findAll());
    }

    public ResponseEntity<Funcionario> getOneById(Integer id) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        if(funcionarioOptional.isPresent()){
            return ResponseEntity.ok(funcionarioOptional.get());
        }
        throw new FuncionarioNotFoundException();
    }

    public ResponseEntity<Funcionario> updateFuncionario(Integer id, FuncionarioDTO funcionarioDTO){
        Funcionario updatedFuncionario = insertData(id, funcionarioDTO);
        funcionarioRepository.save(updatedFuncionario);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Funcionario> deleteById(Integer id){
        funcionarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Funcionario insertData(Integer id, FuncionarioDTO funcionarioEnviado) {
        Funcionario funcionarioEncontrado = getOneById(id).getBody();
        assert funcionarioEncontrado != null;

        Date dataNasc;
        Date dataAdmi;

        if(StringUtils.isBlank(funcionarioEnviado.getNome()) && StringUtils.isBlank(funcionarioEnviado.getCpf()) && StringUtils.isBlank(funcionarioEnviado.getDataAdmissao()) && StringUtils.isBlank(funcionarioEnviado.getDataNascimento()) && StringUtils.isBlank(funcionarioEnviado.getTelefone())){
            throw new UpdateNotValidException("Funcionário");
        }
        if(StringUtils.isBlank(funcionarioEnviado.getNome())){
            funcionarioEnviado.setNome(funcionarioEncontrado.getNome());
        }
        if(StringUtils.isBlank(funcionarioEnviado.getCpf())){
            funcionarioEnviado.setCpf(funcionarioEncontrado.getCpf());
        }
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
        if(StringUtils.isBlank(funcionarioEnviado.getTelefone())){
            funcionarioEnviado.setTelefone(funcionarioEncontrado.getTelefone());
        }

        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(funcionarioEnviado.getCpf());
        if(funcionarioOptional.isPresent() && !Objects.equals(funcionarioOptional.get().getId(), id)) throw new CadastroCpfAlreadyInUseException();

        Funcionario updatedFuncionario = new Funcionario();
        updatedFuncionario.setId(id);
        updatedFuncionario.setNome(funcionarioEnviado.getNome());
        updatedFuncionario.setCpf(funcionarioEnviado.getCpf());
        updatedFuncionario.setDataNascimento(dataNasc);
        updatedFuncionario.setDataAdmissao(dataAdmi);
        updatedFuncionario.setTelefone(funcionarioEnviado.getTelefone());
        updatedFuncionario.setNumeroLogradouro(funcionarioEncontrado.getNumeroLogradouro());
        updatedFuncionario.setCadastro(funcionarioEncontrado.getCadastro());
        updatedFuncionario.setEnderecos(funcionarioEncontrado.getEnderecos());

        return updatedFuncionario;
    }
}

