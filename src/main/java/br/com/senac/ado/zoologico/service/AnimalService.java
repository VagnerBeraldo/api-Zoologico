package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.AnimalDTO;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.entity.Especie;
import br.com.senac.ado.zoologico.entity.Habitat;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository repository;
    private final EspecieService especieService;
    private final HabitatService habitatService;


    public List<Animal> findByFilters(String especie, String habitat, String status) {
        return repository.findByFilters(especie, habitat, status);
    }


    public Map<String, Long> contarPorEspecie() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        a -> a.getEspecie() != null ? a.getEspecie().getNomeComum() : "N/A",
                        Collectors.counting()
                ));
    }

    public Animal findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
    }


    public UUID save(AnimalDTO dto) {

        Animal a = new Animal();
        a.setNome(dto.getNome());
        a.setSexo(dto.getSexo());
        a.setDataNascimento(dto.getDataNascimento());
        a.setStatus(dto.getStatus());

        if (dto.getEspecieId() != null) {
            Especie especie = especieService.findById(dto.getEspecieId());
            a.setEspecie(especie);
        }

        if (dto.getHabitatId() != null) {
            Habitat habitat = habitatService.findById(dto.getHabitatId());
            a.setHabitat(habitat);
        }
        return repository.save(a).getId();
    }

    public List<Animal> getAll() {
        return repository.findAll();
    }





    @Transactional
    public UUID update(UUID id, AnimalDTO dto) {

        Animal existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));

        existente.setNome(dto.getNome());
        existente.setSexo(dto.getSexo());
        existente.setDataNascimento(dto.getDataNascimento());
        existente.setStatus(dto.getStatus());

        // Atualiza espécie se enviada
        if (dto.getEspecieId() != null) {
            Especie especie = especieService.findById(dto.getEspecieId());
            existente.setEspecie(especie);
        }

        // Atualiza habitat se enviado
        if (dto.getHabitatId() != null) {
            Habitat habitat = habitatService.findById(dto.getHabitatId());
            existente.setHabitat(habitat);
        }

        return repository.save(existente).getId();
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Animal não encontrado");
        }
        repository.deleteById(id);
    }




}
