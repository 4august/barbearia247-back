package com.example.demo.DTO;

import com.example.demo.domain.UsuarioRole;

public record ClienteDTO(String nome, String cpf, String telefone, String email, String senha, UsuarioRole role){

}
