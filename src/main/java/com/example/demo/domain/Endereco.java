package com.example.demo.domain;

import com.example.demo.DTO.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="endereco")
@Table(name="endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private Long numero;
    private String bairro;
    private String complemento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "barbeariaID")
    @JsonIgnoreProperties({"barbeiros", "servicos","contatos"})
    private Usuario barbearia;

    public Endereco(EnderecoDTO data, Usuario barbearia){
        this.cep = data.cep();
        this.logradouro = data.logradouro();
        this.numero = data.numero();
        this.bairro = data.bairro();
        this.complemento = data.complemento();
        this.barbearia = barbearia;
    }
}
