package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Especie;
import br.com.senac.ado.zoologico.repository.EspecieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EspecieService {

    private final EspecieRepository repo;

    public EspecieService(EspecieRepository repo) {
        this.repo = repo;
    }

    public List<Especie> listarTodos() {
        return repo.findAll();
    }

    public Especie buscar(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Especie salvar(Especie especie) {
        return repo.save(especie);
    }

    public Especie atualizar(UUID id, Especie especie) {
        Especie existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
        existente.setNomeCientifico(especie.getNomeCientifico());
        existente.setNomeComum(especie.getNomeComum());
        return repo.save(existente);
    }

    public void excluir(UUID id) {
        repo.deleteById(id);
    }
}
