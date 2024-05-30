package com.example.demo.DTO;

import com.example.demo.domain.UsuarioRole;

public record BarbeariaReqDTO(
        String cnpj,
        String email,
        String senha,
        String nome,
        String logo,
        String banner,
        Boolean isActive,
        UsuarioRole role
) {}
