package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.TratadorDTO;
import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.TratadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TratadorService {

    private final TratadorRepository repository;

    public List<Tratador> getAll() {
        return repository.findAll();
    }

    public Tratador findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
    }

    public UUID save(TratadorDTO dto) {

        Tratador tratador = new Tratador();
        tratador.setNome(dto.getNome());
        tratador.setCpf(dto.getCpf());
        tratador.setTelefone(dto.getTelefone());

        return repository.save(tratador).getId();
    }

    public void update(UUID id, Tratador tratador) {

        Tratador existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
        existente.setNome(tratador.getNome());
        existente.setCpf(tratador.getCpf());
        existente.setTelefone(tratador.getTelefone());
        repository.save(existente);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não Encontrado com o ID informado");
        }
        repository.deleteById(id);
    }
}
