package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.CuidadorHabitat.CuidadorHabitatDTO;
import br.com.senac.ado.zoologico.entity.CuidadorHabitat;
import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.entity.Habitat;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.CuidadorHabitatRepository;
import br.com.senac.ado.zoologico.repository.TratadorRepository;
import br.com.senac.ado.zoologico.repository.HabitatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CuidadorHabitatService {

    private final CuidadorHabitatRepository repository;
    private final TratadorRepository tratadorRepo;
    private final HabitatRepository habitatRepo;


    public List<CuidadorHabitat> getAll() {
        return repository.findAll();
    }

    public CuidadorHabitat findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Associação cuidador-habitat não encontrada"));
    }

    public UUID associar(CuidadorHabitatDTO dto) {
        Tratador tratador = tratadorRepo.findById(dto.tratadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Tratador não encontrado"));

        Habitat habitat = habitatRepo.findById(dto.habitatId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitat não encontrado"));

        CuidadorHabitat registro = new CuidadorHabitat();
        registro.setTratador(tratador);
        registro.setHabitat(habitat);
        registro.setTurno(dto.turno());

        return repository.save(registro).getId();
    }

    public void update(UUID id, CuidadorHabitatDTO dto) {
        CuidadorHabitat existente = findById(id);
        existente.setTurno(dto.turno());
        repository.save(existente);
    }

    public void remover(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Associação Cuidador-Habitat não encontrada");
        }
        repository.deleteById(id);
    }
}
