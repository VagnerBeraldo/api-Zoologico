package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Bilhete.BilheteDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.exception.MaxCapacityReachedException;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.BilheteRepository;
import br.com.senac.ado.zoologico.repository.EventoZoologicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BilheteServiceTest {

    @Mock
    private BilheteRepository bilheteRepository;

    @Mock
    private EventoZoologicoRepository eventoRepo;

    @InjectMocks
    private BilheteService service;

    private UUID bilheteId;
    private UUID eventoId;
    private EventoZoologico evento;
    private Bilhete bilhete;
    private BilheteDTO bilheteDTO;

    @BeforeEach
    void setUp() {
        bilheteId = UUID.randomUUID();
        eventoId = UUID.randomUUID();

        // Configuração do Evento padrão
        evento = new EventoZoologico();
        evento.setId(eventoId);
        evento.setDescricao("Show de Focas");
        evento.setCapacidade(100); // Capacidade de 100

        // Configuração do Bilhete padrão
        bilhete = new Bilhete();
        bilhete.setId(bilheteId);
        bilhete.setComprador("Alice");
        bilhete.setValor(50.0);
        bilhete.setDataCompra(LocalDateTime.now());
        bilhete.setEvento(evento);

        // Configuração do DTO padrão
        bilheteDTO = new BilheteDTO("Bob", LocalDateTime.now(), 65.0,  eventoId);
    }

    // ------------------------------------------------------------------
    // -------------------------- FIND BY ID ----------------------------
    // ------------------------------------------------------------------

    @Test
    @DisplayName("findById deve retornar Bilhete quando encontrado")
    void findById_ShouldReturnBilhete() {
        when(bilheteRepository.findById(bilheteId)).thenReturn(Optional.of(bilhete));

        Bilhete result = service.findById(bilheteId);

        assertNotNull(result);
        assertEquals(bilheteId, result.getId());
    }

    @Test
    @DisplayName("findById deve lançar ResourceNotFoundException quando não encontrado")
    void findById_ShouldThrowNotFound() {
        when(bilheteRepository.findById(bilheteId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(bilheteId));
    }

    // ------------------------------------------------------------------
    // ----------------------------- BUY --------------------------------
    // ------------------------------------------------------------------

    @Test
    @DisplayName("buy deve salvar o bilhete se houver capacidade")
    void buy_ShouldSaveBilheteWhenCapacityAvailable() {
        // ARRANGE
        // 1. Evento existe
        when(eventoRepo.findById(eventoId)).thenReturn(Optional.of(evento));
        // 2. Contagem de vendidos (0) é menor que a capacidade (100)
        when(bilheteRepository.countByEventoId(eventoId)).thenReturn(0L);
        // 3. Simula o salvamento
        when(bilheteRepository.save(any(Bilhete.class))).thenAnswer(invocation -> {
            Bilhete saved = invocation.getArgument(0);
            saved.setId(UUID.randomUUID());
            return saved;
        });

        // ACT
        Bilhete result = service.buy(bilheteDTO);

        // ASSERT
        assertNotNull(result);
        assertEquals("Bob", result.getComprador());
        verify(bilheteRepository, times(1)).save(any(Bilhete.class));
    }

    @Test
    @DisplayName("buy deve lançar ResourceNotFoundException se o Evento não for encontrado")
    void buy_ShouldThrowNotFoundIfEventMissing() {
        // ARRANGE
        // Evento não existe
        when(eventoRepo.findById(eventoId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> service.buy(bilheteDTO));
        verify(bilheteRepository, never()).save(any()); // Garante que não salvou
    }

    @Test
    @DisplayName("buy deve lançar MaxCapacityReachedException se a capacidade máxima for atingida")
    void buy_ShouldThrowMaxCapacityReached() {
        // ARRANGE
        // 1. Evento existe
        when(eventoRepo.findById(eventoId)).thenReturn(Optional.of(evento));
        // 2. Contagem de vendidos (100) é igual à capacidade (100)
        when(bilheteRepository.countByEventoId(eventoId)).thenReturn(100L);

        // ACT & ASSERT
        assertThrows(MaxCapacityReachedException.class, () -> service.buy(bilheteDTO));
        verify(bilheteRepository, never()).save(any()); // Garante que não salvou
    }

    // ------------------------------------------------------------------
    // ----------------------------- UPDATE -----------------------------
    // ------------------------------------------------------------------

    @Test
    @DisplayName("update deve atualizar comprador e valor")
    void update_ShouldUpdateSimpleFields() {
        // ARRANGE
        BilheteDTO novoDto = new BilheteDTO("Charlie",LocalDateTime.now(), 75.0, eventoId); // Mantém o mesmo eventoId

        when(bilheteRepository.findById(bilheteId)).thenReturn(Optional.of(bilhete));
        when(bilheteRepository.save(any(Bilhete.class))).thenReturn(bilhete);

        // ACT
        Bilhete result = service.update(bilheteId, novoDto);

        // ASSERT
        assertEquals("Charlie", result.getComprador());
        assertEquals(75.0, result.getValor());
        verify(bilheteRepository, times(1)).save(bilhete);
        // Verifica que não houve tentativa de buscar o evento (pois o ID é o mesmo)
        verify(eventoRepo, never()).findById(any());
    }

    @Test
    @DisplayName("update deve atualizar o evento se o ID for diferente")
    void update_ShouldChangeEventIfIdIsDifferent() {
        // ARRANGE
        UUID novoEventoId = UUID.randomUUID();
        EventoZoologico novoEvento = new EventoZoologico();
        novoEvento.setId(novoEventoId);
        novoEvento.setDescricao("Nova Exibição");

        BilheteDTO dtoNovoEvento = new BilheteDTO("Charlie", LocalDateTime.now(), 75.0, novoEventoId);

        when(bilheteRepository.findById(bilheteId)).thenReturn(Optional.of(bilhete));
        // 1. Simula a busca do novo evento
        when(eventoRepo.findById(novoEventoId)).thenReturn(Optional.of(novoEvento));
        when(bilheteRepository.save(any(Bilhete.class))).thenReturn(bilhete);

        // ACT
        Bilhete result = service.update(bilheteId, dtoNovoEvento);

        // ASSERT
        assertEquals(novoEventoId, result.getEvento().getId());
        verify(eventoRepo, times(1)).findById(novoEventoId);
        verify(bilheteRepository, times(1)).save(bilhete);
    }

    @Test
    @DisplayName("update deve lançar ResourceNotFoundException se o Bilhete não existir")
    void update_ShouldThrowNotFoundIfBilheteMissing() {
        when(bilheteRepository.findById(bilheteId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(bilheteId, bilheteDTO));
        verify(bilheteRepository, never()).save(any());
    }

    @Test
    @DisplayName("update deve lançar ResourceNotFoundException se o novo Evento não existir")
    void update_ShouldThrowNotFoundIfNewEventMissing() {
        UUID novoEventoId = UUID.randomUUID();
        BilheteDTO dtoNovoEvento = new BilheteDTO("Charlie",  LocalDateTime.now(),75.0, novoEventoId);

        when(bilheteRepository.findById(bilheteId)).thenReturn(Optional.of(bilhete));
        // Novo evento não existe
        when(eventoRepo.findById(novoEventoId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(bilheteId, dtoNovoEvento));
        verify(bilheteRepository, never()).save(any());
    }

    // ------------------------------------------------------------------
    // ----------------------------- DELETE -----------------------------
    // ------------------------------------------------------------------

    @Test
    @DisplayName("delete deve deletar bilhete quando ID existe")
    void delete_ShouldDeleteBilhete() {
        when(bilheteRepository.existsById(bilheteId)).thenReturn(true);

        service.delete(bilheteId);

        verify(bilheteRepository, times(1)).deleteById(bilheteId);
    }

    @Test
    @DisplayName("delete deve lançar ResourceNotFoundException quando ID não existe")
    void delete_ShouldThrowNotFound() {
        when(bilheteRepository.existsById(bilheteId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(bilheteId));
        verify(bilheteRepository, never()).deleteById(any());
    }

    // ------------------------------------------------------------------
    // ----------------------- TOTAL POR EVENTO -------------------------
    // ------------------------------------------------------------------

    @Test
    @DisplayName("totalBilhetesPorEvento deve mapear os resultados corretamente")
    void totalBilhetesPorEvento_ShouldMapResultsCorrectly() {
        // ARRANGE
        // Simula o retorno do repository.contarPorEvento()
        List<Object[]> mockResultados = Arrays.asList(
                new Object[]{"Show de Focas", 50L},
                new Object[]{"Alimentação dos Leões", 10L}
        );
        when(bilheteRepository.contarPorEvento()).thenReturn(mockResultados);

        // ACT
        Map<String, Long> resultado = service.totalBilhetesPorEvento();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(50L, resultado.get("Show de Focas"));
        assertEquals(10L, resultado.get("Alimentação dos Leões"));
        verify(bilheteRepository, times(1)).contarPorEvento();
    }
}