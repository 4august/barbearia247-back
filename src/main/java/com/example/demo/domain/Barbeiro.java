package com.example.demo.domain;

import com.example.demo.DTO.BarbeiroDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
@Entity(name="barbeiros")
@Table(name="barbeiros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Barbeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String cpf;

    @JsonIgnoreProperties({"barbeiros", "servicos", "contatos"})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "barbeariaID")
    private Barbearia barbearia;

    @OneToMany(mappedBy = "barbeiro")
    @JsonIgnore
    private List<Atendimento> atendimentos;

    public Barbeiro(BarbeiroDTO data, Barbearia barbearia) {
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.barbearia = barbearia;
    }
}
