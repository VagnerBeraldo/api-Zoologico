package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.ConsultaDTO;
import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.entity.Veterinario;
import br.com.senac.ado.zoologico.repository.AnimalRepository;
import br.com.senac.ado.zoologico.repository.VeterinarioRepository;
import br.com.senac.ado.zoologico.service.ConsultaVeterinariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    /**
     * Endpoint para buscar consultas com múltiplos critérios e filtros compostos.
     * Combina: (Especialidade DO VETERINÁRIO AND (É URGENTE OR Data >= Data Mínima))
     * * @param especialidade A especialidade do veterinário (ex: "Cirurgia"). OBRIGATÓRIO.
     * @param urgente Se a consulta foi marcada como urgente (true/false). OBRIGATÓRIO.
     * @param dataMin Data mínima para a consulta, no formato YYYY-MM-DD. OBRIGATÓRIO.
     * @return Lista de ConsultasVeterinarias que atendem aos critérios.
     */
    @GetMapping("/busca-composta")
    public ResponseEntity<List<ConsultaVeterinaria>> buscarPorFiltros(
            @RequestParam("especialidade") String especialidade,
            @RequestParam("urgente") Boolean urgente,
            @RequestParam("dataMin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMin) {

        List<ConsultaVeterinaria> consultas =
                service.buscarPorFiltros(especialidade, urgente, dataMin);

        if (consultas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consultas);
    }


}
