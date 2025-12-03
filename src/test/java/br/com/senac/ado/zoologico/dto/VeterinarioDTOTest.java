package br.com.senac.ado.zoologico.dto;

import br.com.senac.ado.zoologico.dto.Veterianario.VeterinarioDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VeterinarioDTOTest {

    // Objeto usado para testar as anotações de validação
    private Validator validator;

    @BeforeEach
    void setUp() {
        // Inicializa o ambiente de validação do Bean Validation
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    // --- 1. Teste de Integridade (Record Features) ---
    @Test
    @DisplayName("Deve criar o DTO corretamente e acessar todos os campos")
    void createAndAccessFields_ShouldBeSuccessful() {
        // 1. Criação do DTO
        String nome = "Dr. Animal";
        String especialidade = "Felinos";
        String crmv = "12345/MG";

        VeterinarioDTO dto = new VeterinarioDTO(nome, especialidade, crmv);

        // 2. Verificação dos acessores (getters) gerados pelo record
        assertEquals(nome, dto.nome());
        assertEquals(especialidade, dto.especialidade());
        assertEquals(crmv, dto.crmv());

        // 3. Verifica o toString (para cobertura)
        assertTrue(dto.toString().contains(nome));
        assertTrue(dto.toString().contains(crmv));

        // 4. Verifica equals (para cobertura) - compara com uma cópia
        assertEquals(dto, new VeterinarioDTO(nome, especialidade, crmv));
    }

    // --- 2. Testes de Validação (@NotBlank) ---

    @Test
    @DisplayName("Deve passar na validação quando todos os campos são válidos")
    void validation_WhenAllFieldsAreValid_ShouldHaveNoViolations() {
        VeterinarioDTO dto = new VeterinarioDTO("Dr. Animal", "Felinos", "12345/MG");

        // Deve retornar uma lista vazia de violações
        var violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve falhar na validação quando o campo nome está em branco")
    void validation_WhenNomeIsBlank_ShouldFail() {
        // Nome vazio
        VeterinarioDTO dto = new VeterinarioDTO(" ", "Felinos", "12345/MG");

        var violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("nome", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("Deve falhar na validação quando o campo crmv é nulo")
    void validation_WhenCrmvIsNull_ShouldFail() {
        // CRMV nulo
        VeterinarioDTO dto = new VeterinarioDTO("Dr. Animal", "Felinos", null);

        var violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("crmv", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("Deve falhar na validação quando o campo especialidade é nulo")
    void validation_WhenEspecialidadeIsNull_ShouldFail() {
        // Especialidade nula
        VeterinarioDTO dto = new VeterinarioDTO("Dr. Animal", null, "12345/MG");

        var violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("especialidade", violations.iterator().next().getPropertyPath().toString());
    }
}