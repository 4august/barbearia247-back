package com.example.demo.repository;

import com.example.demo.domain.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {
    Optional<Barbearia> findByCnpjOrEmail(String cnpj, String email);

    /*@Query("SELECT b FROM barbearia WHERE usuarioid = :id ")
    Barbearia pesquisarPorID(Long id);*/

    @Query("SELECT b FROM barbearia b WHERE b.nome LIKE CONCAT('%', :termo, '%')")
    List<Barbearia> pesquisar(String termo);
}
