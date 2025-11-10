package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.ConsultaDTO;
import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.entity.Veterinario;
import br.com.senac.ado.zoologico.repository.AnimalRepository;
import br.com.senac.ado.zoologico.repository.VeterinarioRepository;
import br.com.senac.ado.zoologico.service.ConsultaVeterinariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class ConsultaVeterinariaController {

    private final ConsultaVeterinariaService service;
    private final AnimalRepository animalRepo;
    private final VeterinarioRepository vetRepo;

    @GetMapping
    public List<ConsultaVeterinaria> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ConsultaVeterinaria buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }


    @PostMapping
    public ConsultaVeterinaria criar(@RequestBody ConsultaDTO dto) {
        ConsultaVeterinaria consulta = new ConsultaVeterinaria();
        consulta.setDataConsulta(dto.getDataConsulta());
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setTratamento(dto.getTratamento());
        consulta.setObservacoes(dto.getObservacoes());
        consulta.setUrgente(dto.getUrgente());

        Animal animal = animalRepo.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
        Veterinario veterinario = vetRepo.findById(dto.getVeterinarioId())
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado"));

        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);

        return service.salvar(consulta);
    }

    @PutMapping("/{id}")
    public ConsultaVeterinaria atualizar(@PathVariable UUID id, @RequestBody ConsultaDTO dto) {
        ConsultaVeterinaria existente = service.buscar(id);
        existente.setDataConsulta(dto.getDataConsulta());
        existente.setDiagnostico(dto.getDiagnostico());
        existente.setTratamento(dto.getTratamento());
        existente.setObservacoes(dto.getObservacoes());
        existente.setUrgente(dto.getUrgente());
        return service.salvar(existente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {
        service.excluir(id);
    }
}
