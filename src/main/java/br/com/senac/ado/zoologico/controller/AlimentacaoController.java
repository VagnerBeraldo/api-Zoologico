package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.AlimentacaoDTO;
import br.com.senac.ado.zoologico.entity.Alimentacao;
import br.com.senac.ado.zoologico.service.AlimentacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/alimentacoes")
public class AlimentacaoController {

    private final AlimentacaoService service;

    public AlimentacaoController(AlimentacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Alimentacao> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{animalId}")
    public List<Alimentacao> listarPorAnimal(@PathVariable UUID animalId) {
        return service.listarPorAnimal(animalId);
    }

    @PostMapping
    public Alimentacao registrar(@RequestBody AlimentacaoDTO dto) {
        return service.registrar(dto);
    }

    @PutMapping("/{animalId}/{tratadorId}/{data}")
    public Alimentacao atualizar(@PathVariable UUID animalId,
                                 @PathVariable UUID tratadorId,
                                 @PathVariable String data,
                                 @RequestBody AlimentacaoDTO dto) {
        return service.atualizar(animalId, tratadorId, data, dto);
    }

    @DeleteMapping("/{animalId}/{tratadorId}/{data}")
    public void deletar(@PathVariable UUID animalId,
                        @PathVariable UUID tratadorId,
                        @PathVariable String data) {
        service.deletar(animalId, tratadorId, data);
    }

    @GetMapping("/estatisticas/media-quantidade")
    public Map<String, Double> calcularMediaQuantidade() {
        return Map.of("mediaQuantidadeKg", service.calcularMediaQuantidade());
    }
}
