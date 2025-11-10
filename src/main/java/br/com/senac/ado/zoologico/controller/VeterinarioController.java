package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.VeterinarioDTO;
import br.com.senac.ado.zoologico.entity.Veterinario;
import br.com.senac.ado.zoologico.service.VeterinarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    private final VeterinarioService service;

    public VeterinarioController(VeterinarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Veterinario> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Veterinario buscarPorId(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public Veterinario criar(@RequestBody VeterinarioDTO dto) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNome(dto.getNome());
        veterinario.setCrmv(dto.getCrmv());
        veterinario.setEspecialidade(dto.getEspecialidade());
        return service.salvar(veterinario);
    }

    @PutMapping("/{id}")
    public Veterinario atualizar(@PathVariable UUID id, @RequestBody VeterinarioDTO dto) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNome(dto.getNome());
        veterinario.setCrmv(dto.getCrmv());
        veterinario.setEspecialidade(dto.getEspecialidade());
        return service.atualizar(id, veterinario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.excluir(id);
    }
}
