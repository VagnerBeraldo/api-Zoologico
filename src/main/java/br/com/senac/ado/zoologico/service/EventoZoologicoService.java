package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.repository.EventoZoologicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoZoologicoService {

    private final EventoZoologicoRepository repository;


    public List<EventoZoologico> listarTodos() {
        return repository.findAll();
    }

    public EventoZoologico buscar(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public EventoZoologico salvar(EventoZoologico evento) {
        return repository.save(evento);
    }

    public EventoZoologico atualizar(UUID id, EventoZoologico evento) {
        EventoZoologico existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        existente.setTitulo(evento.getTitulo());
        existente.setData(evento.getData());
        existente.setCapacidade(evento.getCapacidade());
        existente.setDescricao(evento.getDescricao());
        return repository.save(existente);
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}

