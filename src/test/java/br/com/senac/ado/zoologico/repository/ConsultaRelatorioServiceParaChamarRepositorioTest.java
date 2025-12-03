package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.service.ConsultaRelatorioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultaRelatorioServiceParaChamarRepositorioTest {

    @Mock
    private ConsultaVeterinariaRepository repository; // O repositório será mockado

    @InjectMocks
    private ConsultaRelatorioService service; // O serviço será injetado com o mock

    /**
     * Testa se o método de serviço chama o método de contagem do repositório
     * e retorna os dados de relatório esperados.
     */
    @Test
    void consultasPorEspecie_DeveRetornarDadosDeRelatorioMockados() {
        // ARRANGE: Simula os dados que a consulta JPQL retornaria do banco
        List<Map<String, Object>> dadosSimulados = List.of(
                Map.of("especie", "Leão", "totalConsultas", 3L),
                Map.of("especie", "Cobra Píton", "totalConsultas", 1L)
        );

        // Configura o mock: Quando o serviço chamar repository.contarConsultasPorEspecie(),
        // ele deve retornar nossa lista simulada, sem ir ao banco.
        when(repository.contarConsultasPorEspecie()).thenReturn(dadosSimulados);

        // ACT
        List<Map<String, Object>> resultado = service.consultasPorEspecie();

        // ASSERT

        // 1. Verifica se o método correto do repositório foi chamado (confirma a integração service->repository)
        verify(repository).contarConsultasPorEspecie();

        // 2. Verifica a estrutura e os valores retornados (confirma que o serviço repassou os dados corretamente)
        assertFalse(resultado.isEmpty(), "O resultado não deve ser vazio.");
        assertEquals(2, resultado.size(), "Deve retornar 2 registros de espécies.");

        // 3. Verifica a contagem para 'Leão'
        Map<String, Object> leaoData = resultado.stream()
                .filter(m -> "Leão".equals(m.get("especie")))
                .findFirst().orElseThrow();

        assertEquals(3L, leaoData.get("totalConsultas"), "A contagem de Leão deve ser 3.");
    }
}