package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.HabitatDTO;
import br.com.senac.ado.zoologico.entity.Habitat;
import br.com.senac.ado.zoologico.service.HabitatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/habitats")
@RequiredArgsConstructor
public class HabitatController implements GenericController {

    private static final String BASE_PATH = "/api/habitats";

    private final HabitatService service;

    @GetMapping
    public ResponseEntity<List<Habitat>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitat> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody HabitatDTO dto) {
        var idGerado = service.salvar(dto);
        URI location = gerarHeaderLocation(BASE_PATH, idGerado);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody HabitatDTO dto) {
      service.update(id, dto);
      return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
