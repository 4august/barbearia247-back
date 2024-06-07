package com.example.demo.controller;

import com.example.demo.DTO.ClienteDTO;
import com.example.demo.domain.Cliente;
import com.example.demo.domain.Usuario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    public ResponseEntity veSeFunciona() {
        var allClientes = repository.findAll();

        return ResponseEntity.ok(allClientes);
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

        if(repository.findByEmailOrCpf(data.email(), data.cpf()).isEmpty())
            return ResponseEntity.ok(repository.save(new Cliente(data)));

        return ResponseEntity.ok(new RuntimeException("erro, email ou cpf já estão sendo utilizados"));
    }
}
