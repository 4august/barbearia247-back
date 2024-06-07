package com.example.demo.repository;

import com.example.demo.domain.Atendimento;
import com.example.demo.domain.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    Optional<Atendimento> findByBarbeiroAndDataHora(Barbeiro barbeiro, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dataHora);

}