package com.example.demo.controller;

import com.example.demo.DTO.BannerPatchDTO;
import com.example.demo.DTO.BarbeariaReqDTO;
import com.example.demo.domain.Barbearia;
import com.example.demo.domain.Usuario;
import com.example.demo.repository.BarbeariaRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barbearia")
public class BarbeariaController {
    @Autowired
    private BarbeariaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity salvar(@RequestBody @Valid BarbeariaReqDTO data) {
        if (repository.findByCnpjOrEmail(data.cnpj(), data.email()).isEmpty())
            return ResponseEntity.ok(repository.save(new Barbearia(data)));

        return ResponseEntity.ok(new RuntimeException("Erro ao salvar, o CNPJ ou email já está sendo utilizado"));
    }

    @GetMapping("{id}")
    public ResponseEntity encontrarPorID(@PathVariable Long id) {
        Barbearia barbearia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("barbearia não encontrada"));

        return ResponseEntity.ok(barbearia);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        Barbearia barbearia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("erro ao deletar, barbearia não encontrada"));

        repository.deleteById(id);

        return ResponseEntity.ok("cliente deletado");
    }

    @PutMapping("{id}")
    public ResponseEntity editar(@PathVariable Long id, @RequestBody @Valid BarbeariaReqDTO data) {
        Barbearia barbearia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("barbearia não encontrada"));

        barbearia.setNome(data.nome());
        barbearia.setCnpj(data.cnpj());
        barbearia.setEmail(data.email());

        final Barbearia edit = repository.save(barbearia);

        return ResponseEntity.ok(edit);
    }

    @GetMapping("/pesquisa")
    public ResponseEntity pesquisar(@RequestParam("termo") String termo) {
        var resultados = repository.pesquisar(termo);

        for (Barbearia barbearia : resultados) {
            barbearia.setSenha(null);
            barbearia.setCnpj(null);
        }

        return ResponseEntity.ok(resultados);
    }

    @PatchMapping("/atualizar-banner")
    public ResponseEntity atualizarBanner(@RequestBody @Valid BannerPatchDTO data) {
        Barbearia barberia = repository.findById(data.barbeariaID())
                .orElseThrow(() -> new RuntimeException("barbearia não encontrada"));

        barberia.setBanner(data.banner());
        barberia.setLogo(data.logo());
        barberia.setNome(data.nome());

        repository.save(barberia);
        return ResponseEntity.ok(barberia);

    }


}
