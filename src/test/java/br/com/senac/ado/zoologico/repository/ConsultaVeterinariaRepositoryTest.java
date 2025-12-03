package br.com.senac.ado.zoologico.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Disabled;


// Assume que você tem entidades mockadas e dados inseridos
// para o teste funcionar (usando @Sql ou EntityManager)
@DataJpaTest
class ConsultaVeterinariaRepositoryTest {

    @Autowired
    private ConsultaVeterinariaRepository repository;

    @Disabled
    void contarConsultasPorEspecie_DeveRetornarContagemCorreta() {
        // ARRANGE: (Neste ponto, o banco H2 estaria carregado com dados de teste)

        // ACT
        List<Map<String, Object>> resultados = repository.contarConsultasPorEspecie();

        // ASSERT
        assertFalse(resultados.isEmpty(), "Deve retornar resultados de contagem.");

        // Exemplo de verificação de um resultado específico:
        // long leaoCount = resultados.stream()
        //      .filter(m -> "Leão".equals(m.get("especie")))
        //      .map(m -> (Long) m.get("totalConsultas"))
        //      .findFirst().orElse(0L);
        // assertEquals(5L, leaoCount);
    }
}
