package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.BilheteDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.service.BilheteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bilhetes")
@RequiredArgsConstructor
public class BilheteController implements GenericController {

    private static final String BASE_PATH = "/api/usuarios";

    private final BilheteService service;


    @GetMapping
    public ResponseEntity<List<Bilhete>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bilhete> findById(@PathVariable UUID id) {
      return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public Bilhete save(@RequestBody BilheteDTO dto) {
        return service.buy(dto);
    }

    @PutMapping("/{id}")
    public Bilhete update(@PathVariable UUID id, @RequestBody BilheteDTO dto) {
        return service.update(id, dto);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/estatisticas/total-por-evento")
    public Map<String, Long> totalPorEvento() {
        return service.totalBilhetesPorEvento();
    }



}
