package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.CuidadorHabitatDTO;
import br.com.senac.ado.zoologico.entity.CuidadorHabitat;
import br.com.senac.ado.zoologico.service.CuidadorHabitatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cuidadores-habitats")
@RequiredArgsConstructor
public class CuidadorHabitatController {

    private final CuidadorHabitatService service;

    @GetMapping
    public List<CuidadorHabitat> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public CuidadorHabitat buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public CuidadorHabitat associar(@RequestBody CuidadorHabitatDTO dto) {
        return service.associar(dto);
    }

    @PutMapping("/{id}")
    public CuidadorHabitat atualizar(@PathVariable UUID id, @RequestBody CuidadorHabitatDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable UUID id) {
        service.remover(id);
    }
}
