package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Repositories.FuncionarioRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired private FuncionarioRepository funcionarioRepository;

    public ResponseEntity<List<Funcionario>> getAll() {
        return ResponseEntity.ok(funcionarioRepository.findAll());
    }

    public ResponseEntity<Funcionario> getOneById(Integer id) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        if(funcionarioOptional.isPresent()){
            return ResponseEntity.ok(funcionarioOptional.get());
        }
        throw new RuntimeException("Funcionário não encontrado!");
    }

    public ResponseEntity<Funcionario> updateFuncionario(Integer id, FuncionarioDTO funcionarioDTO){
        Funcionario funcionarioEncontrado = getOneById(id).getBody();
        Funcionario updatedFuncionario = insertData(funcionarioEncontrado, funcionarioDTO);

        Date dataAdmissao = convertData(funcionarioDTO.getDataAdmissao());
        Date dataNascimento = convertData(funcionarioDTO.getDataNascimento());

        updatedFuncionario.setDataAdmissao(dataAdmissao);
        updatedFuncionario.setDataNascimento(dataNascimento);
        funcionarioRepository.save(updatedFuncionario);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Funcionario> deleteById(Integer id){
        try{
            funcionarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex){
            throw new RuntimeException("Não foi possível deletar o funcionário");
        }
    }

    private Funcionario insertData(Funcionario funcionarioEncontrado, FuncionarioDTO funcionarioEnviado) {
        Funcionario updatedFuncionario = new Funcionario();
        updatedFuncionario.setId(funcionarioEncontrado.getId());

        if(StringUtils.isBlank(funcionarioEncontrado.getNome()) && StringUtils.isNotBlank(funcionarioEncontrado.getNome())){
            updatedFuncionario.setNome(funcionarioEncontrado.getNome());
        } else {
            updatedFuncionario.setNome(funcionarioEnviado.getNome());
        }
        if(StringUtils.isBlank(funcionarioEnviado.getCpf()) && StringUtils.isNotBlank(funcionarioEncontrado.getCpf())){
            updatedFuncionario.setCpf(funcionarioEncontrado.getCpf());
        } else {
            updatedFuncionario.setCpf(funcionarioEnviado.getCpf());
        }
        if(funcionarioEnviado.getNumeroLogradouro() == null){
            updatedFuncionario.setNumeroLogradouro(funcionarioEncontrado.getNumeroLogradouro());
        } else {
            updatedFuncionario.setNumeroLogradouro(funcionarioEnviado.getNumeroLogradouro());
        }
        if(funcionarioEnviado.getTelefone() == null){
            updatedFuncionario.setTelefone(funcionarioEncontrado.getTelefone());
        } else {
            updatedFuncionario.setTelefone(funcionarioEnviado.getTelefone());
        }
        updatedFuncionario.setCadastro(funcionarioEncontrado.getCadastro());
        updatedFuncionario.setEnderecos(funcionarioEncontrado.getEnderecos());

        return updatedFuncionario;
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

