package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.repository.TratadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TratadorService {

    private final TratadorRepository repo;

    public TratadorService(TratadorRepository repo) {
        this.repo = repo;
    }

    public List<Tratador> listarTodos() {
        return repo.findAll();
    }

    public Tratador buscar(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Tratador salvar(Tratador tratador) {
        return repo.save(tratador);
    }

    public Tratador atualizar(UUID id, Tratador tratador) {
        Tratador existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratador não encontrado"));
        existente.setNome(tratador.getNome());
        existente.setCpf(tratador.getCpf());
        existente.setTelefone(tratador.getTelefone());
        return repo.save(existente);
    }

    public void excluir(UUID id) {
        repo.deleteById(id);
    }
}
