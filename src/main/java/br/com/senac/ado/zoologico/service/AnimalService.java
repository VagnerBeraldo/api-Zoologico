package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Animal.AnimalDTO;
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
        a.setNome(dto.nome());
        a.setSexo(dto.sexo());
        a.setDataNascimento(dto.dataNascimento());
        a.setStatus(dto.status());

        if (dto.especieId() != null) {
            Especie especie = especieService.findById(dto.especieId());
            a.setEspecie(especie);
        }

        if (dto.habitatId() != null) {
            Habitat habitat = habitatService.findById(dto.habitatId());
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

        existente.setNome(dto.nome());
        existente.setSexo(dto.sexo());
        existente.setDataNascimento(dto.dataNascimento());
        existente.setStatus(dto.status());

        // Atualiza espécie se enviada
        if (dto.especieId() != null) {
            Especie especie = especieService.findById(dto.especieId());
            existente.setEspecie(especie);
        }

        // Atualiza habitat se enviado
        if (dto.habitatId() != null) {
            Habitat habitat = habitatService.findById(dto.habitatId());
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
