package com.example.demo.domain;


import com.example.demo.DTO.ClienteDTO;
import jakarta.persistence.*;
import lombok.*;


@Entity(name="cliente")
@Table(name="cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Cliente extends Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "clienteID")
    private Long id;
    private String nome;
    @Column(unique = true)
    private String cpf;
    private String telefone;

    public Cliente(ClienteDTO data){
        super(data.email(), data.senha(), data.role());
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.telefone = data.telefone();
    }
}
