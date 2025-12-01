package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.CuidadorHabitat.CuidadorHabitatDTO;
import br.com.senac.ado.zoologico.entity.CuidadorHabitat;
import br.com.senac.ado.zoologico.service.CuidadorHabitatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cuidadores-habitats")
@RequiredArgsConstructor
public class CuidadorHabitatController implements GenericController {

    private static final String BASE_PATH = "/api/cuidadores-habitats";

    private final CuidadorHabitatService service;

    @GetMapping
    public ResponseEntity<List<CuidadorHabitat>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public CuidadorHabitat findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Void> associar(@RequestBody CuidadorHabitatDTO dto) {
        var idGerado =  service.associar(dto);
        URI location =  gerarHeaderLocation(BASE_PATH, idGerado);
         return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CuidadorHabitatDTO dto) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
