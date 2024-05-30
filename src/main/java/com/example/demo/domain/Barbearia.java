package com.example.demo.domain;

import com.example.demo.DTO.BarbeariaReqDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Entity(name = "barbearia")
@Table(name = "barbearia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "funcionarios"})
public class Barbearia extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barbeariaID")
    private Long id;
    @Column(unique = true)
    private String cnpj;
    private String nome;
    private String logo;
    private String banner;
    private Boolean isActive;


    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("barbearia")
    private List<Barbeiro> barbeiros;

    @OneToOne(mappedBy = "barbearia", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("barbearia")
    private Endereco endereco;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("barbearia")
    private List<Contato> contatos;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("barbearia")
    private List<Servico> servicos;
    public Barbearia(BarbeariaReqDTO data) {
        super(data.email(), data.senha(), data.role());
        this.cnpj = data.cnpj();
        this.nome = data.nome();
        this.logo = data.logo();
        this.banner = data.banner();
        this.isActive = data.isActive();
    }

}