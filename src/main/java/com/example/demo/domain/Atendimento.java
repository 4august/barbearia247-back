package com.example.demo.domain;

import com.example.demo.DTO.AtendimentoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "atendimento")
@Table(name = "atendimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"barbeiros", "servicos", "contatos", "atendimentos, barbearia"})
    @ManyToOne(cascade = CascadeType.ALL)
    private Barbeiro barbeiro;
    @JsonIgnoreProperties({"barbeiros", "servicos", "contatos", "atendimentos, barbearia"})
    @ManyToOne(cascade = CascadeType.ALL)
    private Servico servico;

    @JsonIgnoreProperties({"barbeiros", "servicos", "contatos", "atendimentos, barbearia"})
    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario cliente;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dataHora;

    private Boolean isActive;

    public Atendimento(Usuario cliente, Servico servico, Barbeiro barbeiro, AtendimentoDTO data) {
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.servico = servico;
        this.dataHora = data.dataHora();
        this.isActive = data.isActive();
    }
}

