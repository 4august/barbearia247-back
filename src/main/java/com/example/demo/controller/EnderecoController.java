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

    @PutMapping("{id}")
    public ResponseEntity editar(@PathVariable @Valid long id, @RequestBody @Valid EnderecoDTO data) {
        var endereco = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("endereco não encontrada"));

        var barbearia = barbeariaRepository.findById(data.barbeariaID())
                        .orElseThrow(()-> new RuntimeException("barbearia não encontrada"));

        endereco.setCep(data.cep());
        endereco.setLogradouro(data.logradouro());
        endereco.setNumero(data.numero());
        endereco.setCidade(data.cidade());
        endereco.setBairro(data.bairro());
        endereco.setComplemento(data.complemento());
        endereco.setBarbearia(barbearia);

        final Endereco editedEndereco = repository.save(endereco);

        return ResponseEntity.ok(editedEndereco);
    }
}
