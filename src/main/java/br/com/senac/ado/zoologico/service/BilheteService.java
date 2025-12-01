package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Bilhete.BilheteDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.exception.MaxCapacityReachedException;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
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
        novoBilhete.setComprador(dto.comprador());
        novoBilhete.setDataCompra(LocalDateTime.now());
        novoBilhete.setValor(dto.valor());
        novoBilhete.setEvento(evento);
        return novoBilhete;
    }

    public List<Bilhete> getAll() {
        return repository.findAll();
    }

    public Bilhete findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bilhete não encontrado: " + id));
    }

    @Transactional
    public Bilhete buy(BilheteDTO dto) {

        EventoZoologico evento = eventoRepo.findById(dto.eventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        long vendidos = repository.countByEventoId(evento.getId());

        if (vendidos >= evento.getCapacidade()) {
            throw new MaxCapacityReachedException(
                    "Capacidade máxima atingida para este evento."
            );
        }

        Bilhete bilhete = new Bilhete();
        bilhete.setComprador(dto.comprador());
        bilhete.setDataCompra(dto.dataCompra());
        bilhete.setValor(dto.valor());
        bilhete.setEvento(evento);

        return repository.save(bilhete);
    }

    @Transactional
    public Bilhete update(UUID id, BilheteDTO dto) {

        Bilhete existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bilhete não encontrado: " + id));

        existente.setComprador(dto.comprador());
        existente.setDataCompra(dto.dataCompra());
        existente.setValor(dto.valor());

        // Atualizando evento somente se cliente pediu
        if (dto.eventoId() != null && !dto.eventoId().equals(existente.getEvento().getId())) {

            EventoZoologico evento = eventoRepo.findById(dto.eventoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

            existente.setEvento(evento);
        }

        return repository.save(existente);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Bilhete não encontrado para exclusão: " + id);
        }
        repository.deleteById(id);
    }

    public Map<String, Long> totalBilhetesPorEvento() {

        List<Object[]> resultados = repository.contarPorEvento();

        return resultados.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }

}
