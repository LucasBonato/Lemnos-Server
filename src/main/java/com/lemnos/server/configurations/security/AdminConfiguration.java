package com.lemnos.server.configurations.security;

import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.models.enums.Roles;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.entidades.FuncionarioRepository;
import com.lemnos.server.shared.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Slf4j
@Configuration
public class AdminConfiguration implements CommandLineRunner {
    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private CadastroRepository cadastroRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Optional<Cadastro> adminOptional = cadastroRepository.findByEmail("admin@gmail.com");
        adminOptional.ifPresentOrElse(
            user -> log.info("[INFO] Admin já existe!"),
            () -> {
                Funcionario admin = new Funcionario();
                admin.setCadastro(new Cadastro("admin@gmail.com", passwordEncoder.encode("admin")));
                admin.setRole(Roles.ADMIN);
                admin.setCpf(23504391864L);
                admin.setTelefone(11972540380L);
                admin.setNome("admin");
                admin.setDataAdmissao(Convert.toData("16/05/2024"));
                admin.setDataNascimento(Convert.toData("01/01/2000"));

                funcionarioRepository.save(admin);
                log.info("[INFO] Admin cadastrado!");
            }
        );

    }
}
