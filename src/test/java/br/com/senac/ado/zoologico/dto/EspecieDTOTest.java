package br.com.senac.ado.zoologico.dto;

import br.com.senac.ado.zoologico.dto.Especie.EspecieDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EspecieDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        // Inicializa o ambiente de validação (necessário para testar o @NotBlank)
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    // --- 1. Teste de Integridade do Record ---
    @Test
    @DisplayName("Deve criar o DTO corretamente e permitir acesso aos campos")
    void createAndAccessFields_ShouldBeSuccessful() {
        // Dados de teste
        String comum = "Mico-leão-dourado";
        String cientifico = "Leontopithecus rosalia";
        String status = "Em Perigo";

        // 1. Criação
        EspecieDTO dto = new EspecieDTO(comum, cientifico, status);

        // 2. Verificação dos acessores (getters) e de toString/equals
        assertEquals(comum, dto.nomeComum());
        assertEquals(cientifico, dto.nomeCientifico());
        assertEquals(status, dto.statusConservacao());

        // Garante que o toString e equals gerados pelo record são cobertos
        assertTrue(dto.toString().contains(cientifico));
        assertEquals(dto, new EspecieDTO(comum, cientifico, status));
    }

    // --- 2. Testes de Validação (@NotBlank) ---

    @Test
    @DisplayName("Deve passar na validação quando todos os campos são válidos")
    void validation_WhenAllFieldsAreValid_ShouldHaveNoViolations() {
        EspecieDTO dto = new EspecieDTO("Mico-leão", "L. rosalia", "Em Perigo");

        var violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve falhar na validação quando o campo nomeComum está vazio ou nulo")
    void validation_WhenNomeComumIsBlank_ShouldFail() {
        // Testa campo vazio ("")
        EspecieDTO dto = new EspecieDTO("", "L. rosalia", "Em Perigo");
        assertEquals(1, validator.validate(dto).size());

        // Testa campo só com espaços ("  ")
        EspecieDTO dto2 = new EspecieDTO("   ", "L. rosalia", "Em Perigo");
        assertEquals(1, validator.validate(dto2).size());

        // Testa campo nulo (null)
        EspecieDTO dto3 = new EspecieDTO(null, "L. rosalia", "Em Perigo");
        assertEquals(1, validator.validate(dto3).size());
    }

    @Test
    @DisplayName("Deve falhar na validação quando o campo statusConservacao é nulo")
    void validation_WhenStatusConservacaoIsNull_ShouldFail() {
        EspecieDTO dto = new EspecieDTO("Mico-leão", "L. rosalia", null);

        var violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("statusConservacao", violations.iterator().next().getPropertyPath().toString());
    }
}