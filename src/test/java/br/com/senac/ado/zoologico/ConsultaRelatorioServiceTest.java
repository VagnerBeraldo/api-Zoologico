package br.com.senac.ado.zoologico;

import br.com.senac.ado.zoologico.repository.ConsultaVeterinariaRepository;
import br.com.senac.ado.zoologico.service.ConsultaRelatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultaRelatorioServiceTest {

    @Mock
    private ConsultaVeterinariaRepository repository;

    @InjectMocks
    private ConsultaRelatorioService service;

    @Test
    void consultasPorEspecie_DeveChamarMetodoDoRepositorio_eRetornarLista() {
        // ARRANGE
        List<Map<String, Object>> mockResult = List.of(
                Map.of("especie", "Leão", "totalConsultas", 12L),
                Map.of("especie", "Macaco", "totalConsultas", 5L)
        );
        when(repository.contarConsultasPorEspecie()).thenReturn(mockResult);

        // ACT
        List<Map<String, Object>> resultado = service.consultasPorEspecie();

        // ASSERT
        // Verifica se o método correto do repositório foi chamado
        verify(repository).contarConsultasPorEspecie();
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }
}