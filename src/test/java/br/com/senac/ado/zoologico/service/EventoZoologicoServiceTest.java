package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Bilhete.BilheteDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.repository.BilheteRepository;
import br.com.senac.ado.zoologico.repository.EventoZoologicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoZoologicoServiceTest {

    @Mock
    private EventoZoologicoRepository eventoRepository;

    @Mock
    private BilheteRepository bilheteRepository;

    @InjectMocks
    private EventoZoologicoService service;

    private final UUID eventoId = UUID.randomUUID();
    private final UUID bilheteId = UUID.randomUUID();

    @Test
    void venderBilhete_Sucesso_DeveVenderEBilhete_EDecrementarCapacidade() {
        // ARRANGE
        EventoZoologico evento = new EventoZoologico();
        evento.setId(eventoId);
        evento.setCapacidade(10); // Capacidade inicial

        Bilhete bilheteSalvo = new Bilhete();
        bilheteSalvo.setId(bilheteId);

        BilheteDTO dto = new BilheteDTO(
                "Comprador Teste",
                LocalDateTime.now(), // Argumento CORRIGIDO: dataCompra
                50.0,
                eventoId
        );

        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(evento));
        when(bilheteRepository.save(any(Bilhete.class))).thenReturn(bilheteSalvo);

        // ACT
        UUID idRetornado = service.venderBilhete(dto);

        // ASSERT
        assertEquals(bilheteId, idRetornado);

        // Verifica se a capacidade foi decrementada
        assertEquals(9, evento.getCapacidade());

        // Verifica se o evento foi salvo (persistencia implicita pelo @Transactional)
        verify(eventoRepository, times(1)).findById(eventoId);

        // Verifica se o bilhete foi salvo
        verify(bilheteRepository, times(1)).save(any(Bilhete.class));
    }

    @Test
    void venderBilhete_EventoNaoEncontrado_DeveLancarException() {
        // ARRANGE
        BilheteDTO dto = new BilheteDTO(
                "Comprador Teste",
                LocalDateTime.now(), // Argumento CORRIGIDO: dataCompra
                50.0,
                eventoId
        );
        when(eventoRepository.findById(eventoId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(EntityNotFoundException.class, () -> service.venderBilhete(dto));

        // Verifica que nem o bilhete, nem o evento foram salvos
        verify(bilheteRepository, never()).save(any());
    }

    @Test
    void venderBilhete_CapacidadeEsgotada_DeveLancarException() {
        // ARRANGE
        EventoZoologico evento = new EventoZoologico();
        evento.setId(eventoId);
        evento.setCapacidade(0); // Lotado

        BilheteDTO dto = new BilheteDTO(
                "Comprador Teste",
                LocalDateTime.now(), // Argumento CORRIGIDO: dataCompra
                50.0,
                eventoId
        );
        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(evento));

        // ACT & ASSERT
        assertThrows(IllegalStateException.class, () -> service.venderBilhete(dto));

        // Verifica que o bilhete não foi salvo
        verify(bilheteRepository, never()).save(any());

        // Verifica que a capacidade do evento permaneceu 0
        assertEquals(0, evento.getCapacidade());
    }
}