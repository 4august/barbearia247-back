package com.example.demo.domain;

import com.example.demo.DTO.ServicoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name="servicos")
@Table(name="servicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Number valor;

    @JsonIgnoreProperties({"barbeiros","servicos", "contatos", "atendimentos"})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "barbeariaID")
    private Usuario barbearia;

    @OneToMany(mappedBy = "servico")
    @JsonIgnore
    private List<Atendimento> atendimentos;

    public Servico(ServicoDTO data, Usuario barbearia) {
        this.nome = data.nome();
        this.valor = data.valor();
        this.barbearia = barbearia;
    }
}
