package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Habitat;
import br.com.senac.ado.zoologico.repository.HabitatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HabitatService {

    private final HabitatRepository repo;

    public HabitatService(HabitatRepository repo) {
        this.repo = repo;
    }

    public List<Habitat> listarTodos() {
        return repo.findAll();
    }

    public Habitat buscar(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Habitat salvar(Habitat habitat) {
        return repo.save(habitat);
    }

    public Habitat atualizar(UUID id, Habitat habitat) {
        Habitat existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitat não encontrado"));
        existente.setNome(habitat.getNome());
        existente.setTipo(habitat.getTipo());
        existente.setAreaM2(habitat.getAreaM2());
        return repo.save(existente);
    }

    public void excluir(UUID id) {
        repo.deleteById(id);
    }
}
