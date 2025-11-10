package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Veterinario;
import br.com.senac.ado.zoologico.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VeterinarioService {

    private final VeterinarioRepository repo;

    public VeterinarioService(VeterinarioRepository repo) {
        this.repo = repo;
    }

    public List<Veterinario> listarTodos() {
        return repo.findAll();
    }

    public Veterinario buscar(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Veterinario salvar(Veterinario veterinario) {
        return repo.save(veterinario);
    }

    public Veterinario atualizar(UUID id, Veterinario veterinario) {
        Veterinario existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado"));
        existente.setNome(veterinario.getNome());
        existente.setCrmv(veterinario.getCrmv());
        existente.setEspecialidade(veterinario.getEspecialidade());
        return repo.save(existente);
    }

    public void excluir(UUID id) {
        repo.deleteById(id);
    }
}
