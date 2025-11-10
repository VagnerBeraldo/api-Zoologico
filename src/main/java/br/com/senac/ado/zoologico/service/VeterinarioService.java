package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Veterinario;
import br.com.senac.ado.zoologico.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository repository;

    public List<Veterinario> listarTodos() {
        return repository.findAll();
    }

    public Veterinario buscar(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Veterinario salvar(Veterinario veterinario) {
        return repository.save(veterinario);
    }

    public Veterinario atualizar(UUID id, Veterinario veterinario) {
        Veterinario existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado"));
        existente.setNome(veterinario.getNome());
        existente.setCrmv(veterinario.getCrmv());
        existente.setEspecialidade(veterinario.getEspecialidade());
        return repository.save(existente);
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
