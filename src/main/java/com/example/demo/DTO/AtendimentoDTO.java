package com.example.demo.DTO;

import java.time.LocalDateTime;

public record AtendimentoDTO(Long clienteID, Long barbeiroID, Long servicoID, LocalDateTime dataHora, Boolean isActive) {}

