package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.CuidadorHabitatDTO;
import br.com.senac.ado.zoologico.entity.CuidadorHabitat;
import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.entity.Habitat;
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


    public List<CuidadorHabitat> listarTodos() {
        return repository.findAll();
    }

    public CuidadorHabitat buscar(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Associação Cuidador-Habitat não encontrada"));
    }

    public CuidadorHabitat associar(CuidadorHabitatDTO dto) {
        Tratador tratador = tratadorRepo.findById(dto.getTratadorId())
                .orElseThrow(() -> new RuntimeException("Tratador não encontrado"));

        Habitat habitat = habitatRepo.findById(dto.getHabitatId())
                .orElseThrow(() -> new RuntimeException("Habitat não encontrado"));

        CuidadorHabitat registro = new CuidadorHabitat();
        registro.setTratador(tratador);
        registro.setHabitat(habitat);
        registro.setTurno(dto.getTurno());

        return repository.save(registro);
    }

    public CuidadorHabitat atualizar(UUID id, CuidadorHabitatDTO dto) {
        CuidadorHabitat existente = buscar(id);
        existente.setTurno(dto.getTurno());
        return repository.save(existente);
    }

    public void remover(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Associação Cuidador-Habitat não encontrada");
        }
        repository.deleteById(id);
    }
}
