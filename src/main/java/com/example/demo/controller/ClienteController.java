package com.example.demo.controller;

import com.example.demo.DTO.ClienteDTO;
import com.example.demo.domain.Cliente;
import com.example.demo.domain.Usuario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    public ResponseEntity veSeFunciona() {
        var allClientes = repository.findAll();

        return ResponseEntity.ok(allClientes);
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody @Valid ClienteDTO data) {

        /*if(repository.findByEmail(data.email()) ){
            passwordEncoder.encode(data.password());
            return ResponseEntity.ok(repository.save(new Cliente(data)));
        }*/

        if (repository.findByEmailOrCpf(data.email(), data.cpf()) == null) {
//            String encriptedPassword = new BCryptPasswordEncoder().encode(data.password());
            return ResponseEntity.ok(repository.save(new Cliente(data)));
        }

        return ResponseEntity.ok("nao encontrado");
    }

    @GetMapping("{id}")
    public ResponseEntity encontrarUsuario(@PathVariable Long id) {
        Usuario cliente = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("cliente não encontrado"));

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RuntimeException("erro ao deletar, cliente não encontrado"));

        repository.deleteById(id);

        return ResponseEntity.ok("cliente deletado");
    }

    @PutMapping("{id}")
    public ResponseEntity editar(@PathVariable Long id, @RequestBody @Valid ClienteDTO data) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RuntimeException("cliente não encontrado"));

        cliente.setNome(data.nome());
        cliente.setCpf(data.cpf());
        cliente.setEmail(data.email());

        final Cliente newCliente = repository.save(cliente);

        return ResponseEntity.ok(newCliente);
    }


    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar (@RequestBody @Valid ClienteDTO data){
        return ResponseEntity.ok(repository.save(new Cliente(data)));
    }
}
