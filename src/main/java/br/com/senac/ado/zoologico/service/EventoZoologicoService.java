package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Bilhete.BilheteDTO;
import br.com.senac.ado.zoologico.dto.EventoZoologico.EventoZoologicoDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.BilheteRepository;
import br.com.senac.ado.zoologico.repository.EventoZoologicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoZoologicoService {

    private final EventoZoologicoRepository repository;

    private final BilheteRepository bilheteRepository;


    @Transactional
    public UUID venderBilhete(BilheteDTO dto) {

        EventoZoologico evento = repository.findById(dto.eventoId())
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado."));

        if (evento.getCapacidade() <= 0) {
            throw new IllegalStateException("Evento lotado. Capacidade esgotada.");
        }

        evento.setCapacidade(evento.getCapacidade() - 1);

        Bilhete bilhete = new Bilhete();
        bilhete.setComprador(dto.comprador());
        bilhete.setDataCompra(LocalDateTime.now());
        bilhete.setValor(dto.valor());
        bilhete.setEvento(evento);

        return bilheteRepository.save(bilhete).getId();
    }


    public List<EventoZoologico> getAll() {
        return repository.findAll();
    }

    public EventoZoologico findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
    }

    public UUID save(EventoZoologicoDTO dto) {

        EventoZoologico evento = new EventoZoologico();
        evento.setTitulo(dto.titulo());
        evento.setDescricao(dto.descricao());
        evento.setData(LocalDate.from(dto.data()));
        evento.setCapacidade(dto.capacidade());
        return repository.save(evento).getId();
    }

    public void update(UUID id, EventoZoologicoDTO dto) {
        EventoZoologico existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        existente.setTitulo(dto.titulo());
        existente.setData(LocalDate.from(dto.data()));
        existente.setCapacidade(dto.capacidade());
        existente.setDescricao(dto.descricao());
        repository.save(existente);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado para exclusão");
        }
        repository.deleteById(id);
    }
}

