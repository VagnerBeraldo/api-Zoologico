package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Alimentacao;
import br.com.senac.ado.zoologico.repository.AlimentacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlimentacaoServiceTest {

    @Mock
    private AlimentacaoRepository repository;

    // Outros Mocks para AnimalService, TratadorService, etc. seriam necessários para o teste de save()
    @Mock
    private AnimalService animalService;
    @Mock
    private TratadorService tratadorService;

    @InjectMocks
    private AlimentacaoService service;

    @Test
    void calcularMediaQuantidade_DeveRetornarMediaCorreta() {
        // ARRANGE
        Alimentacao a1 = new Alimentacao(); a1.setQuantidadeKg(2.0);
        Alimentacao a2 = new Alimentacao(); a2.setQuantidadeKg(3.0);
        Alimentacao a3 = new Alimentacao(); a3.setQuantidadeKg(4.0);

        // Média esperada: (2.0 + 3.0 + 4.0) / 3 = 3.0
        List<Alimentacao> mockList = List.of(a1, a2, a3);
        when(repository.findAll()).thenReturn(mockList);

        // ACT
        Double media = service.calcularMediaQuantidade();

        // ASSERT
        assertEquals(3.0, media);
    }

    @Test
    void calcularMediaQuantidade_ListaVazia_DeveRetornarZero() {
        // ARRANGE
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // ACT
        Double media = service.calcularMediaQuantidade();

        // ASSERT
        assertEquals(0.0, media);
    }
}