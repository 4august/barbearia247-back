package com.example.demo.repository;

import com.example.demo.domain.Usuario;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByUsername(String email);
    //Usuario findById(Long id)
}
