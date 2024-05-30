package com.example.demo.controller;

import com.example.demo.DTO.EnderecoDTO;
import com.example.demo.domain.Barbearia;
import com.example.demo.domain.Endereco;
import com.example.demo.domain.Usuario;
import com.example.demo.repository.BarbeariaRepository;
import com.example.demo.repository.EnderecoRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private BarbeariaRepository barbeariaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/listar")
    public ResponseEntity listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody @Valid EnderecoDTO data) {
        Usuario barbearia = usuarioRepository.findById(data.barbeariaID())
                .orElseThrow(() -> new RuntimeException("barbearia nao encontrada"));

        return ResponseEntity.ok(repository.save(new Endereco(data, barbearia)));
    }

//    @PutMapping("{id}")
//    public ResponseEntity editar (){
//        return null;
//    }
}
