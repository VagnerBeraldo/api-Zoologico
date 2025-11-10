package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.TratadorDTO;
import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.service.TratadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tratadores")
public class TratadorController {

    private final TratadorService service;

    public TratadorController(TratadorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tratador> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Tratador buscarPorId(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public Tratador criar(@RequestBody TratadorDTO dto) {
        Tratador tratador = new Tratador();
        tratador.setNome(dto.getNome());
        tratador.setCpf(dto.getCpf());
        tratador.setTelefone(dto.getTelefone());
        return service.salvar(tratador);
    }

    @PutMapping("/{id}")
    public Tratador atualizar(@PathVariable UUID id, @RequestBody TratadorDTO dto) {
        Tratador tratador = new Tratador();
        tratador.setNome(dto.getNome());
        tratador.setCpf(dto.getCpf());
        tratador.setTelefone(dto.getTelefone());
        return service.atualizar(id, tratador);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.excluir(id);
    }
}
