package com.example.demo.DTO;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.UsuarioRole;
import org.springframework.http.ResponseEntity;

public record AuthDTO (String token, UsuarioRole userRole, Long usuarioID){
}
