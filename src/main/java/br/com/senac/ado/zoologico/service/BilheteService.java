package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.BilheteDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.repository.BilheteRepository;
import br.com.senac.ado.zoologico.repository.EventoZoologicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BilheteService {

    private final BilheteRepository repository;
    private final EventoZoologicoRepository eventoRepo;


    public Bilhete saveBilhete(BilheteDTO dto, EventoZoologico evento) {
        Bilhete novoBilhete = new Bilhete();
        novoBilhete.setComprador(dto.getComprador());
        novoBilhete.setDataCompra(LocalDateTime.now());
        novoBilhete.setValor(dto.getValor());
        novoBilhete.setEvento(evento);
        return novoBilhete;
    }

    public List<Bilhete> listar() {
        return repository.findAll();
    }

    public Bilhete buscar(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bilhete não encontrado: " + id));
    }

    @Transactional
    public Bilhete comprar(BilheteDTO dto) {
        EventoZoologico evento = eventoRepo.findById(dto.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        long vendidos = repository.findAll().stream()
                .filter(b -> b.getEvento().getId().equals(evento.getId()))
                .count();

        if (vendidos >= evento.getCapacidade()) {
            throw new RuntimeException("Capacidade máxima atingida para este evento.");
        }

        Bilhete bilhete = new Bilhete();
        bilhete.setComprador(dto.getComprador());
        bilhete.setDataCompra(dto.getDataCompra());
        bilhete.setValor(dto.getValor());
        bilhete.setEvento(evento);

        return repository.save(bilhete);
    }

    public Bilhete atualizar(UUID id, BilheteDTO dto) {
        Bilhete existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bilhete não encontrado: " + id));

        existente.setComprador(dto.getComprador());
        existente.setDataCompra(dto.getDataCompra());
        existente.setValor(dto.getValor());

        if (dto.getEventoId() != null) {
            EventoZoologico evento = eventoRepo.findById(dto.getEventoId())
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
            existente.setEvento(evento);
        }

        return repository.save(existente);
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }

    public Map<String, Long> totalBilhetesPorEvento() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getEvento().getTitulo(),
                        Collectors.counting()
                ));
    }

}
