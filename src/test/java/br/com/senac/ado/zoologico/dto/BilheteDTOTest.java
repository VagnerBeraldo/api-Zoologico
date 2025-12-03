package br.com.senac.ado.zoologico.dto;

import br.com.senac.ado.zoologico.dto.Bilhete.BilheteDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BilheteDTOTest {

    private Validator validator;
    private LocalDateTime dataValida;
    private UUID eventoIdValido;

    @BeforeEach
    void setUp() {
        // Inicializa o ambiente de validação
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        dataValida = LocalDateTime.now().plusDays(1);
        eventoIdValido = UUID.randomUUID();
    }

    // --- 1. Teste de Integridade (Record Features) ---
    @Test
    @DisplayName("Deve criar o DTO corretamente e permitir acesso a todos os campos")
    void createAndAccessFields_ShouldBeSuccessful() {
        // Dados de teste
        String comprador = "Mário Almeida";
        Double valor = 150.0;

        // 1. Criação
        BilheteDTO dto = new BilheteDTO(comprador, dataValida, valor, eventoIdValido);

        // 2. Verificação dos acessores (getters)
        assertEquals(comprador, dto.comprador());
        assertEquals(dataValida, dto.dataCompra());
        assertEquals(valor, dto.valor());
        assertEquals(eventoIdValido, dto.eventoId());

        // 3. Garante a cobertura de toString/equals
        assertTrue(dto.toString().contains(comprador));
        assertEquals(dto, new BilheteDTO(comprador, dataValida, valor, eventoIdValido));
    }

    // --- 2. Testes de Validação (@NotBlank, @NotNull, @Positive) ---

    @Test
    @DisplayName("Deve passar na validação quando todos os campos são válidos")
    void validation_WhenAllFieldsAreValid_ShouldHaveNoViolations() {
        BilheteDTO dto = new BilheteDTO("Comprador Teste", dataValida, 80.0, eventoIdValido);

        var violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    // Testes para @NotBlank
    @Test
    @DisplayName("Deve falhar na validação se 'comprador' estiver vazio ou nulo")
    void validation_WhenCompradorIsBlank_ShouldFail() {
        // Testa String vazia
        BilheteDTO dto = new BilheteDTO(" ", dataValida, 80.0, eventoIdValido);
        var violations = validator.validate(dto);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("comprador")));
        assertTrue(violations.size() >= 1);
    }

    // Testes para @NotNull
    @Test
    @DisplayName("Deve falhar na validação se 'dataCompra' for nula")
    void validation_WhenDataCompraIsNull_ShouldFail() {
        BilheteDTO dto = new BilheteDTO("Comprador Teste", null, 80.0, eventoIdValido);
        var violations = validator.validate(dto);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dataCompra")));
        assertTrue(violations.size() >= 1);
    }

    @Test
    @DisplayName("Deve falhar na validação se 'eventoId' for nulo")
    void validation_WhenEventoIdIsNull_ShouldFail() {
        BilheteDTO dto = new BilheteDTO("Comprador Teste", dataValida, 80.0, null);
        var violations = validator.validate(dto);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("eventoId")));
        assertTrue(violations.size() >= 1);
    }

    // Testes para @Positive
    @Test
    @DisplayName("Deve falhar na validação se 'valor' for zero ou negativo")
    void validation_WhenValorIsZeroOrNegative_ShouldFail() {
        // Valor zero
        BilheteDTO dtoZero = new BilheteDTO("Comprador Teste", dataValida, 0.0, eventoIdValido);
        var violationsZero = validator.validate(dtoZero);

        assertTrue(violationsZero.stream().anyMatch(v -> v.getPropertyPath().toString().equals("valor")));
        assertTrue(violationsZero.size() >= 1);

        // Valor negativo
        BilheteDTO dtoNegativo = new BilheteDTO("Comprador Teste", dataValida, -10.0, eventoIdValido);
        var violationsNegativo = validator.validate(dtoNegativo);

        assertTrue(violationsNegativo.stream().anyMatch(v -> v.getPropertyPath().toString().equals("valor")));
        assertTrue(violationsNegativo.size() >= 1);
    }
}