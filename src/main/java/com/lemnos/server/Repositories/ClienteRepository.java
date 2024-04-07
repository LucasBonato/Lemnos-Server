package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> { }
