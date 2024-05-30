package com.example.demo.repository;

import com.example.demo.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmailOrCpf(String email, String cpf);

}
