package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.VeterinarioDTO;
import br.com.senac.ado.zoologico.entity.Veterinario;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository repository;

    public List<Veterinario> getAll() {
        return repository.findAll();
    }

    public Veterinario findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
    }

    public UUID save(VeterinarioDTO dto) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNome(dto.getNome());
        veterinario.setCrmv(dto.getCrmv());
        veterinario.setEspecialidade(dto.getEspecialidade());
       return repository.save(veterinario).getId();
    }

    public void update(UUID id, VeterinarioDTO dto) {

        Veterinario existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));

        existente.setNome(dto.getNome());
        existente.setCrmv(dto.getCrmv());
        existente.setEspecialidade(dto.getEspecialidade());
        repository.save(existente);
    }

    public void delete(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado ID informado para exclusao"));

        repository.deleteById(id);
    }
}
