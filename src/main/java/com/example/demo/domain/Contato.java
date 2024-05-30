package com.example.demo.domain;

import com.example.demo.DTO.ContatoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="contato")
@Table(name="contato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String link;

    @JsonIgnoreProperties({"barbeiros", "servicos","contatos"})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "barbeariaID")
    private Barbearia barbearia;

    public Contato(ContatoDTO data, Barbearia barbearia) {
        this.descricao = data.descricao();
        this.link = data.link();
        this.barbearia = barbearia;
    }
}
