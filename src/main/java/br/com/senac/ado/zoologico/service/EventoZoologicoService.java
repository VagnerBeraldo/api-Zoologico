package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.BilheteDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.repository.BilheteRepository;
import br.com.senac.ado.zoologico.repository.EventoZoologicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoZoologicoService {

    private final EventoZoologicoRepository repository;

    private final BilheteRepository bilheteRepository;



    @Transactional // teste para requisito do projeto
    public Bilhete venderBilhete(BilheteDTO dto) {

        // 1. Encontrar o Evento (Operação de Leitura)
        EventoZoologico evento = repository.findById(dto.getEventoId())
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado."));

        // 2. Validação (Garante a consistência antes da escrita)
        if (evento.getCapacidade() <= 0) {
            // Lançar exceção faz o Spring disparar o ROLLBACK automaticamente
            throw new IllegalStateException("Evento lotado. Capacidade esgotada.");
        }

        // 3. Primeira Operação de Escrita: Atualizar Capacidade (Decremento)
        evento.setCapacidade(evento.getCapacidade() - 1);
        repository.save(evento); // O save aqui é "adiado" até o commit

        // 4. Segunda Operação de Escrita: Criar o Bilhete
        Bilhete novoBilhete = new Bilhete();
        novoBilhete.setComprador(dto.getComprador());
        novoBilhete.setDataCompra(LocalDateTime.now());
        novoBilhete.setValor(dto.getValor());
        novoBilhete.setEvento(evento);

        Bilhete bilheteSalvo = bilheteRepository.save(novoBilhete);

        // 5. COMMIT implícito: Se o metodo terminar aqui sem exceção,
        // ambas as operações (atualização do evento e criação do bilhete) são salvas.

        return bilheteSalvo;
    }


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

