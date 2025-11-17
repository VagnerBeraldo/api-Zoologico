package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.HabitatDTO;
import br.com.senac.ado.zoologico.entity.Habitat;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.HabitatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitatService {

    private final HabitatRepository repository;

    public List<Habitat> getAll() {
        return repository.findAll();
    }

    public Habitat findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
    }

    public UUID salvar(HabitatDTO dto) {

        Habitat habitat = new Habitat();
        habitat.setNome(dto.getNome());
        habitat.setTipo(dto.getTipo());
        habitat.setAreaM2(Double.valueOf(dto.getAreaM2()));

        return repository.save(habitat).getId();
    }

    public Habitat update(UUID id, HabitatDTO dto) {
        Habitat existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
        existente.setNome(dto.getNome());
        existente.setTipo(dto.getTipo());
        existente.setAreaM2(Double.valueOf(dto.getAreaM2()));
        return repository.save(existente);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não Encontrado com o ID informado para exclusão");
        }
        repository.deleteById(id);
    }
}
