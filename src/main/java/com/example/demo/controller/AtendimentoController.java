package com.example.demo.controller;

import com.example.demo.DTO.AtendimentoDTO;
import com.example.demo.domain.*;
import com.example.demo.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {
    @Autowired
    private AtendimentoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    public List<Atendimento> listar (){
        return repository.findAll();
    }

    @PostMapping()
    public ResponseEntity salvar (@RequestBody @Valid AtendimentoDTO data){

        Barbeiro funcionario = barbeiroRepository.findById(data.barbeiroID())
                .orElseThrow(()-> new RuntimeException("barbeiro nao encontrado"));

        Servico servico = servicoRepository.findById(data.servicoID())
                .orElseThrow(()-> new RuntimeException("serviço não encontrado"));

        Usuario cliente = usuarioRepository.findById(data.clienteID())
                .orElseThrow(()-> new RuntimeException("cliente nao encontrado"));

        if(repository.findByBarbeiroAndDataHora(funcionario, data.dataHora()).isEmpty())
            return ResponseEntity.ok(repository.save(new Atendimento(cliente,servico, funcionario, data)));

        return ResponseEntity.ok(new RuntimeException("erro, Horário já marcado "));
        //return ResponseEntity.ok(repository.save(new Atendimento(cliente,servico, funcionario, data)));
    }

    /*@GetMapping("{id}/{dataHorario}")
    public ResponseEntity getAtendimento (@PathVariable Long id, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dataHorario){
        Funcionario funcionario = barbeiroRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("funcinario nao encontrado"));

       var query =  ;

       return ResponseEntity.ok(query);
    }*/

    @DeleteMapping("{id}")
    public void deletar (@PathVariable @Valid Long id){
        repository.deleteById(id);
    }

    /*@GetMapping("/encontrar-atendimento/{id}")
    public ResponseEntity getAtendimento(@PathVariable @Valid AtendimentoDTO data){
        //return ResponseEntity.ok(data);
        return ResponseEntity.ok(repository.findByBarbeiroAndDataHora(data.funcionarioID(), data.dataHora()));
    }*/

}
