package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.Veterianario.VeterinarioDTO;
import br.com.senac.ado.zoologico.entity.Veterinario;
import br.com.senac.ado.zoologico.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController implements GenericController {

    private static final String BASE_PATH = "/api/veterinarios";

    private final VeterinarioService service;

    @GetMapping
    public ResponseEntity<List<Veterinario>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody VeterinarioDTO dto) {
        var idGerado =  service.save(dto);
        URI location = gerarHeaderLocation(BASE_PATH, idGerado);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody VeterinarioDTO dto) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
