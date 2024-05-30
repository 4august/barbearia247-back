package com.example.demo.controller;

import com.example.demo.DTO.ServicoDTO;
import com.example.demo.domain.Servico;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.UsuarioRole;
import com.example.demo.repository.BarbeariaRepository;
import com.example.demo.repository.ServicoRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servico")
public class ServicoController {
    @Autowired
    private ServicoRepository repository;

    @Autowired
    private BarbeariaRepository barbeariaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    public ResponseEntity listar (){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody @Valid ServicoDTO data){
        Usuario usuario = usuarioRepository.findById(data.barbeariaID())
                .orElseThrow(()-> new RuntimeException("Erro ao salvar serviço, usuario não encontrada"));

        if(usuario.getRole() == UsuarioRole.User)
            return ResponseEntity.ok(new RuntimeException("Erro ao salvar serviço, usuario não encontrada"));

        return ResponseEntity.ok(repository.save(new Servico(data, usuario)));

    }

    @PutMapping("{id}")
    public ResponseEntity editar (@PathVariable Long id, @RequestBody @Valid ServicoDTO data){
        Servico servico = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Serviço da barbearia nao encontrado"));

        Usuario barbearia = usuarioRepository.findById(data.barbeariaID())
                .orElseThrow(()-> new RuntimeException("Barbearia não encontrada"));

        servico.setNome(data.nome());
        servico.setValor(data.valor());
        servico.setBarbearia(barbearia);

        if(barbearia.getRole() == UsuarioRole.User)
            return ResponseEntity.ok(new RuntimeException("Erro ao salvar serviço, usuario não encontrada"));


        return ResponseEntity.ok(repository.save(servico));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar (@PathVariable Long id){
        Servico servico = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Serviço da barbearia nao encontrado"));
        repository.deleteById(servico.getId());

        return ResponseEntity.ok("Serviço deletado");
    }
}

