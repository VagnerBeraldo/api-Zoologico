package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.AnimalDTO;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.entity.Especie;
import br.com.senac.ado.zoologico.entity.Habitat;
import br.com.senac.ado.zoologico.repository.EspecieRepository;
import br.com.senac.ado.zoologico.repository.HabitatRepository;
import br.com.senac.ado.zoologico.service.AnimalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/animais")
public class AnimalController {

    private final AnimalService service;
    private final EspecieRepository especieRepo;
    private final HabitatRepository habitatRepo;

    public AnimalController(AnimalService service, EspecieRepository especieRepo, HabitatRepository habitatRepo) {
        this.service = service;
        this.especieRepo = especieRepo;
        this.habitatRepo = habitatRepo;
    }

    @GetMapping
    public List<Animal> listar(@RequestParam(required = false) String especie,
                               @RequestParam(required = false) String habitat,
                               @RequestParam(required = false) String status) {
        if (especie != null && habitat != null && status != null) {
            return service.buscarPorFiltros(especie, habitat, status);
        }
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Animal buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @GetMapping("/contagem-por-especie")
    public Map<String, Long> contagemPorEspecie() {
        return service.contarPorEspecie();
    }

    @PostMapping
    public Animal criar(@RequestBody AnimalDTO dto) {
        Animal a = new Animal();
        a.setNome(dto.getNome());
        a.setSexo(dto.getSexo());
        a.setDataNascimento(dto.getDataNascimento());
        a.setStatus(dto.getStatus());

        if (dto.getEspecieId() != null) {
            Especie especie = especieRepo.findById(dto.getEspecieId())
                    .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
            a.setEspecie(especie);
        }

        if (dto.getHabitatId() != null) {
            Habitat habitat = habitatRepo.findById(dto.getHabitatId())
                    .orElseThrow(() -> new RuntimeException("Habitat não encontrado"));
            a.setHabitat(habitat);
        }

        return service.salvar(a);
    }

    @PutMapping("/{id}")
    public Animal atualizar(@PathVariable UUID id, @RequestBody AnimalDTO dto) {
        Animal existente = service.buscar(id);
        existente.setNome(dto.getNome());
        existente.setSexo(dto.getSexo());
        existente.setDataNascimento(dto.getDataNascimento());
        existente.setStatus(dto.getStatus());

        if (dto.getEspecieId() != null) {
            Especie especie = especieRepo.findById(dto.getEspecieId())
                    .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
            existente.setEspecie(especie);
        }

        if (dto.getHabitatId() != null) {
            Habitat habitat = habitatRepo.findById(dto.getHabitatId())
                    .orElseThrow(() -> new RuntimeException("Habitat não encontrado"));
            existente.setHabitat(habitat);
        }

        return service.atualizar(id, existente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {
        service.excluir(id);
    }


    @GetMapping("/estatisticas/por-especie")
    public Map<String, Long> contarPorEspecie() {
        return service.contarPorEspecie();
    }


}
