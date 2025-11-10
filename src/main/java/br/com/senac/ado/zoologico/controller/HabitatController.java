package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.HabitatDTO;
import br.com.senac.ado.zoologico.entity.Habitat;
import br.com.senac.ado.zoologico.service.HabitatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/habitats")
public class HabitatController {

    private final HabitatService service;

    public HabitatController(HabitatService service) {
        this.service = service;
    }

    @GetMapping
    public List<Habitat> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Habitat buscarPorId(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public Habitat criar(@RequestBody HabitatDTO dto) {
        Habitat habitat = new Habitat();
        habitat.setNome(dto.getNome());
        habitat.setTipo(dto.getTipo());
        habitat.setAreaM2(Double.valueOf(dto.getAreaM2()));
        return service.salvar(habitat);
    }

    @PutMapping("/{id}")
    public Habitat atualizar(@PathVariable UUID id, @RequestBody HabitatDTO dto) {
        Habitat habitat = new Habitat();
        habitat.setNome(dto.getNome());
        habitat.setTipo(dto.getTipo());
        habitat.setAreaM2(Double.valueOf(dto.getAreaM2()));
        return service.atualizar(id, habitat);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.excluir(id);
    }
}
