package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.Especie.EspecieDTO;
import br.com.senac.ado.zoologico.entity.Especie;
import br.com.senac.ado.zoologico.service.EspecieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/especies")
@RequiredArgsConstructor
public class EspecieController implements GenericController {

    private static final String BASE_PATH = "/api/especies";

    private final EspecieService service;

    @GetMapping
    public ResponseEntity<List<Especie>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public Especie findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody EspecieDTO dto) {
        var idGerado =  service.save(dto);
        URI location = gerarHeaderLocation(BASE_PATH, idGerado);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid EspecieDTO dto) {
      service.update(id, dto);
      return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
