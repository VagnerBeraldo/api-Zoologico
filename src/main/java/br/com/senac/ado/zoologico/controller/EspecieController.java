package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.EspecieDTO;
import br.com.senac.ado.zoologico.entity.Especie;
import br.com.senac.ado.zoologico.service.EspecieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    private final EspecieService service;

    public EspecieController(EspecieService service) {
        this.service = service;
    }

    @GetMapping
    public List<Especie> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Especie buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public Especie criar(@RequestBody EspecieDTO dto) {
        Especie especie = new Especie();
        especie.setNomeComum(dto.getNomeComum());
        especie.setNomeCientifico(dto.getNomeCientifico());
        especie.setStatusConservacao(dto.getStatusConservacao());
        return service.salvar(especie);
    }

    @PutMapping("/{id}")
    public Especie atualizar(@PathVariable UUID id, @RequestBody EspecieDTO dto) {
        Especie especie = new Especie();
        especie.setNomeComum(dto.getNomeComum());
        especie.setNomeCientifico(dto.getNomeCientifico());
        return service.atualizar(id, especie);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {
        service.excluir(id);
    }
}
