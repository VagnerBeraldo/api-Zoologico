package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.Alimentacao.AlimentacaoDTO;
import br.com.senac.ado.zoologico.entity.Alimentacao;
import br.com.senac.ado.zoologico.service.AlimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/alimentacoes")
@RequiredArgsConstructor
public class AlimentacaoController implements GenericController{

    private final AlimentacaoService service;


    @GetMapping
    public ResponseEntity<List<Alimentacao>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<List<Alimentacao>> listarPorAnimal(@PathVariable UUID animalId) {
        return ResponseEntity.ok(service.listByAnimal(animalId));
    }

    @PostMapping
    public Alimentacao save(@RequestBody AlimentacaoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{animalId}/{tratadorId}/{data}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID animalId,
                                                 @PathVariable UUID tratadorId,
                                                 @PathVariable String data,
                                                 @RequestBody AlimentacaoDTO dto) {

        service.atualizar(animalId, tratadorId, data, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{animalId}/{tratadorId}/{data}")
    public ResponseEntity<Void> deletar(@PathVariable UUID animalId,
                                        @PathVariable UUID tratadorId,
                                        @PathVariable String data) {

        service.deletar(animalId, tratadorId, data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estatisticas/media-quantidade")
    public ResponseEntity<Map<String, Double>> calcularMediaQuantidade() {
        double media = service.calcularMediaQuantidade();
        return ResponseEntity.ok(Map.of("mediaQuantidadeKg", media));
    }
}
