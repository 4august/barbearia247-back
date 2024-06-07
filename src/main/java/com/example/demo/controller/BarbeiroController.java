package com.example.demo.controller;

import com.example.demo.DTO.BarbeiroDTO;
import com.example.demo.domain.Barbearia;
import com.example.demo.domain.Barbeiro;
import com.example.demo.repository.BarbeariaRepository;
import com.example.demo.repository.BarbeiroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barbeiro")
public class BarbeiroController {
    @Autowired
    private BarbeiroRepository repository;
    @Autowired
    private BarbeariaRepository barbeariaRepository;

    @GetMapping("/listar")
    public ResponseEntity listarTodos (){
        var allFuncionarios = repository.findAll();

        return ResponseEntity.ok(allFuncionarios);
    }

    @PostMapping()
    public ResponseEntity salvar (@RequestBody @Valid BarbeiroDTO data){
        Barbearia barbearia = barbeariaRepository.findById(data.barbeariaID())
                .orElseThrow(()-> new RuntimeException("erro ao cadastrar barbeiro, barbearia nao encontrada"));

        Barbeiro novoFuncionario = new Barbeiro(data, barbearia);

        repository.save(novoFuncionario);

        return ResponseEntity.ok(novoFuncionario);
    }

    @PutMapping("{id}")
    public ResponseEntity editar(@PathVariable Long id, @RequestBody @Valid BarbeiroDTO data){
        Barbeiro funcionario = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado. Não foi possivel editar dados do funcionário"));

        Barbearia barbeariaEdit = barbeariaRepository.findById(data.barbeariaID()).orElseThrow(() ->
                new RuntimeException("Barbearia não encontrada. Não foi possivel editar o funcionário"));

        funcionario.setNome(data.nome());
        funcionario.setCpf(data.cpf());

        funcionario.setBarbearia(barbeariaEdit);

        final Barbeiro edit = repository.save(funcionario);

        return ResponseEntity.ok(edit);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id){
        Barbeiro funcionario = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Não foi possível deletar usuário"));

        repository.deleteById(id);

        return ResponseEntity.ok("Usuário deletado");
    }

}
