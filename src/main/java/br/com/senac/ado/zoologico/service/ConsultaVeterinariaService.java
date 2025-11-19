package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.ConsultaDTO;
import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.entity.Veterinario;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.ConsultaVeterinariaRepository;
import br.com.senac.ado.zoologico.repository.AnimalRepository;
import br.com.senac.ado.zoologico.repository.VeterinarioRepository;
import br.com.senac.ado.zoologico.repository.specifications.ConsultaSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultaVeterinariaService {

    private final ConsultaVeterinariaRepository repository;
    private final AnimalRepository animalRepo;
    private final VeterinarioRepository veterinarioRepo;


    public List<ConsultaVeterinaria> buscarPorFiltros(
            String especialidade, Boolean urgente, LocalDate dataMin){

        // Exemplo de chamada: Buscar consultas de "Cirurgia" E (urgentes OU a partir de hoje)
        Specification<ConsultaVeterinaria> spec =
                ConsultaSpecifications.buscarConsultasCompostas(especialidade, urgente, dataMin);

        return repository.findAll(spec);
    }


    public List<ConsultaVeterinaria> getAll() {
        return repository.findAll();
    }

    public ConsultaVeterinaria findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public UUID save(ConsultaDTO dto) {

        Animal animal = animalRepo.findById(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));

        Veterinario veterinario = veterinarioRepo.findById(dto.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado"));

        ConsultaVeterinaria consulta = new ConsultaVeterinaria();
        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);
        consulta.setDataConsulta(dto.getDataConsulta());
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setTratamento(dto.getTratamento());
        consulta.setObservacoes(dto.getObservacoes());
        consulta.setUrgente(dto.getUrgente());

        ConsultaVeterinaria saved = repository.save(consulta);

        // Regra de negócio
        if (Boolean.TRUE.equals(dto.getUrgente())) {
            animal.setStatus("em_tratamento");
            animalRepo.save(animal);
        }

        return saved.getId();
    }

    public void update(UUID id, ConsultaDTO dto) {
        ConsultaVeterinaria existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));

        existente.setDataConsulta(dto.getDataConsulta());
        existente.setDiagnostico(dto.getDiagnostico());
        existente.setTratamento(dto.getTratamento());
        existente.setObservacoes(dto.getObservacoes());
        existente.setUrgente(dto.getUrgente());

        repository.save(existente);
    }



    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
