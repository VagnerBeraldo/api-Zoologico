package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.EspecieDTO;
import br.com.senac.ado.zoologico.entity.Especie;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.EspecieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EspecieService {

    private final EspecieRepository repository;

    public List<Especie> getAll() {
        return repository.findAll();
    }

    public Especie findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
    }

    public UUID save(EspecieDTO dto) {
        Especie especie = new Especie();
        especie.setNomeComum(dto.getNomeComum());
        especie.setNomeCientifico(dto.getNomeCientifico());
        especie.setStatusConservacao(dto.getStatusConservacao());
        return repository.save(especie).getId();
    }

    public void update(UUID id, EspecieDTO dto) {
        Especie existente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
        existente.setNomeComum(dto.getNomeComum());
        existente.setNomeCientifico(dto.getNomeCientifico());
        repository.save(existente);
    }

    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não Encontrado com o ID informado");
        }
        repository.deleteById(id);
    }
}
