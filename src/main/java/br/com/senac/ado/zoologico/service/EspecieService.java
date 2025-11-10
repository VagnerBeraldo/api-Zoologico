package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Especie;
import br.com.senac.ado.zoologico.repository.EspecieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EspecieService {

    private final EspecieRepository repository;

    public List<Especie> listarTodos() {
        return repository.findAll();
    }

    public Especie buscar(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Especie salvar(Especie especie) {
        return repository.save(especie);
    }

    public Especie atualizar(UUID id, Especie especie) {
        Especie existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
        existente.setNomeCientifico(especie.getNomeCientifico());
        existente.setNomeComum(especie.getNomeComum());
        return repository.save(existente);
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
