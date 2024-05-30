package com.example.demo.controller;

import com.example.demo.DTO.ContatoDTO;
import com.example.demo.domain.Barbearia;
import com.example.demo.domain.Contato;
import com.example.demo.repository.BarbeariaRepository;
import com.example.demo.repository.ContatoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contato")
public class ContatoController {
    @Autowired
    private ContatoRepository repository;

    @Autowired
    private BarbeariaRepository barbeariaRepository;

    @GetMapping("/listar")
    public ResponseEntity listar(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody @Valid ContatoDTO data){
        Barbearia barbearia = barbeariaRepository.findById(data.barbeariaID())
                .orElseThrow(()-> new RuntimeException("barbearia não encontrada"));

        return ResponseEntity.ok(repository.save(new Contato(data, barbearia)));
    }

    @PutMapping("{id}")
    public  ResponseEntity editar (@PathVariable Long id, @RequestBody @Valid ContatoDTO data){
        Contato contato = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Contato não encontrado"));

        Barbearia barbearia = barbeariaRepository.findById(data.barbeariaID())
                .orElseThrow(() -> new RuntimeException("Barbearia não encontrada"));

        contato.setDescricao(data.descricao());
        contato.setLink(data.link());

        return ResponseEntity.ok(repository.save(contato));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar (@PathVariable Long id){
        Contato contato = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Contato não encontrado"));

        repository.deleteById(contato.getId());

        return ResponseEntity.ok("contato deletado");
    }
}

