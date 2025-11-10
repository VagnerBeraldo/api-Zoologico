package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.repository.TratadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TratadorService {

    private final TratadorRepository repository;

    public List<Tratador> listarTodos() {
        return repository.findAll();
    }

    public Tratador buscar(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Tratador salvar(Tratador tratador) {
        return repository.save(tratador);
    }

    public Tratador atualizar(UUID id, Tratador tratador) {
        Tratador existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratador não encontrado"));
        existente.setNome(tratador.getNome());
        existente.setCpf(tratador.getCpf());
        existente.setTelefone(tratador.getTelefone());
        return repository.save(existente);
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
