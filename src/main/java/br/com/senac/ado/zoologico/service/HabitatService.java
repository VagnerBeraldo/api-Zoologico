package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Habitat;
import br.com.senac.ado.zoologico.repository.HabitatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitatService {

    private final HabitatRepository repository;

    public List<Habitat> listarTodos() {
        return repository.findAll();
    }

    public Habitat buscar(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Habitat salvar(Habitat habitat) {
        return repository.save(habitat);
    }

    public Habitat atualizar(UUID id, Habitat habitat) {
        Habitat existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitat não encontrado"));
        existente.setNome(habitat.getNome());
        existente.setTipo(habitat.getTipo());
        existente.setAreaM2(habitat.getAreaM2());
        return repository.save(existente);
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
