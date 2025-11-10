package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.BilheteDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.repository.BilheteRepository;
import br.com.senac.ado.zoologico.repository.EventoZoologicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BilheteService {

    private final BilheteRepository bilheteRepo;
    private final EventoZoologicoRepository eventoRepo;

    public BilheteService(BilheteRepository bilheteRepo, EventoZoologicoRepository eventoRepo) {
        this.bilheteRepo = bilheteRepo;
        this.eventoRepo = eventoRepo;
    }

    public List<Bilhete> listar() {
        return bilheteRepo.findAll();
    }

    public Bilhete buscar(UUID id) {
        return bilheteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bilhete não encontrado: " + id));
    }

    @Transactional
    public Bilhete comprar(BilheteDTO dto) {
        EventoZoologico evento = eventoRepo.findById(dto.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        long vendidos = bilheteRepo.findAll().stream()
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

        return bilheteRepo.save(bilhete);
    }

    public Bilhete atualizar(UUID id, BilheteDTO dto) {
        Bilhete existente = bilheteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bilhete não encontrado: " + id));

        existente.setComprador(dto.getComprador());
        existente.setDataCompra(dto.getDataCompra());
        existente.setValor(dto.getValor());

        if (dto.getEventoId() != null) {
            EventoZoologico evento = eventoRepo.findById(dto.getEventoId())
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
            existente.setEvento(evento);
        }

        return bilheteRepo.save(existente);
    }

    public void excluir(UUID id) {
        bilheteRepo.deleteById(id);
    }

    public Map<String, Long> totalBilhetesPorEvento() {
        return bilheteRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> b.getEvento().getTitulo(),
                        Collectors.counting()
                ));
    }

}
