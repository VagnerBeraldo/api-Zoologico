package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.repository.EventoZoologicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventoZoologicoService {

    private final EventoZoologicoRepository repo;

    public EventoZoologicoService(EventoZoologicoRepository repo) {
        this.repo = repo;
    }

    public List<EventoZoologico> listarTodos() {
        return repo.findAll();
    }

    public EventoZoologico buscar(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public EventoZoologico salvar(EventoZoologico evento) {
        return repo.save(evento);
    }

    public EventoZoologico atualizar(UUID id, EventoZoologico evento) {
        EventoZoologico existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        existente.setTitulo(evento.getTitulo());
        existente.setData(evento.getData());
        existente.setCapacidade(evento.getCapacidade());
        existente.setDescricao(evento.getDescricao());
        return repo.save(existente);
    }

    public void excluir(UUID id) {
        repo.deleteById(id);
    }
}

