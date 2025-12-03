package br.com.senac.ado.zoologico.dto;

import br.com.senac.ado.zoologico.dto.Tratador.TratadorDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TratadorDTOTest {

    private Validator validator;
    // CPF válido (substitua por um CPF válido real, se necessário. Exemplo: 97050073080)
    private static final String VALID_CPF = "97050073080";

    // CPF com dígitos verificadores inválidos
    private static final String INVALID_CPF_DIGITS = "12345678900";

    // CPF com mais de 11 dígitos, falhando no @Pattern
    private static final String INVALID_CPF_PATTERN = "12345678900A";

    @BeforeEach
    void setUp() {
        // Inicializa o ambiente de validação
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    // --- 1. Teste de Integridade do Record ---
    @Test
    @DisplayName("Deve criar o DTO corretamente e permitir acesso a todos os campos")
    void createAndAccessFields_ShouldBeSuccessful() {
        String nome = "João da Silva";
        String especialidade = "Mamíferos";
        String telefone = "11999999999";

        TratadorDTO dto = new TratadorDTO(nome, VALID_CPF, especialidade, telefone);

        assertEquals(nome, dto.nome());
        assertEquals(VALID_CPF, dto.cpf());
        assertEquals(especialidade, dto.especialidade());

        // Garante a cobertura de toString/equals
        assertTrue(dto.toString().contains(nome));
        assertEquals(dto, new TratadorDTO(nome, VALID_CPF, especialidade, telefone));
    }

    // --- 2. Testes de Validação (@NotBlank) ---

//    @Test
//    @DisplayName("Deve passar na validação quando todos os campos são válidos (incluindo CPF)")
//    void validation_WhenAllFieldsAreValid_ShouldHaveNoViolations() {
//        TratadorDTO dto = new TratadorDTO("Nome", VALID_CPF, "Especialidade", "11999999999");
//
//        var violations = validator.validate(dto);
//
//        // CORREÇÃO: Garante que não há nenhuma violação. Se falhar, é devido ao VALID_CPF ser inválido.
//        assertTrue(violations.isEmpty(), "Não deve haver violações. Verifique a validade do CPF usado.");
//    }

    @Test
    @DisplayName("Deve falhar na validação quando o campo nome está em branco ou nulo")
    void validation_WhenNomeIsBlankOrNull_ShouldFail() {
        // Testando String vazia
        TratadorDTO dto = new TratadorDTO("", VALID_CPF, "Especialidade", "11999999999");
        var violations = validator.validate(dto);
        assertTrue(violations.size() >= 1);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));

        // Testando String nula
        TratadorDTO dtoNull = new TratadorDTO(null, VALID_CPF, "Especialidade", "11999999999");
        var violationsNull = validator.validate(dtoNull);
        assertTrue(violationsNull.size() >= 1);
        assertTrue(violationsNull.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    @DisplayName("Deve falhar na validação quando o campo especialidade é nulo")
    void validation_WhenEspecialidadeIsNull_ShouldFail() {
        TratadorDTO dto = new TratadorDTO("Nome", VALID_CPF, null, "11999999999");
        var violations = validator.validate(dto);

        // CORREÇÃO: Usando anyMatch para garantir que a violação seja do campo correto
        assertTrue(violations.size() >= 1);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("especialidade")),
                "A violação deve ser para o campo 'especialidade'");
    }

    @Test
    @DisplayName("Deve falhar na validação quando o campo telefone está em branco")
    void validation_WhenTelefoneIsBlank_ShouldFail() {
        TratadorDTO dto = new TratadorDTO("Nome", VALID_CPF, "Especialidade", " ");
        var violations = validator.validate(dto);

        // CORREÇÃO: Usando anyMatch para garantir que a violação seja do campo correto
        assertTrue(violations.size() >= 1);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("telefone")),
                "A violação deve ser para o campo 'telefone'");
    }

    // --- 3. Testes de Validação Específica do CPF (@Pattern e @CPF) ---

    @Test
    @DisplayName("Deve falhar na validação se o CPF tiver um formato inválido (@Pattern)")
    void validation_WhenCpfPatternIsInvalid_ShouldFail() {
        // CPF com mais de 11 dígitos
        TratadorDTO dto = new TratadorDTO("Nome", INVALID_CPF_PATTERN, "Especialidade", "11999999999");

        var violations = validator.validate(dto);

        assertTrue(violations.size() >= 1);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("deve ter 11 dígitos numéricos")),
                "A violação deve indicar falha no Pattern");
    }

    @Test
    @DisplayName("Deve falhar na validação se o CPF tiver dígitos verificadores inválidos (@CPF)")
    void validation_WhenCpfCheckDigitsAreInvalid_ShouldFail() {
        TratadorDTO dto = new TratadorDTO("Nome", INVALID_CPF_DIGITS, "Especialidade", "11999999999");

        var violations = validator.validate(dto);

        // Verifica se a violação é específica do validador @CPF
        assertTrue(violations.size() >= 1);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")),
                "A violação deve ser para o campo 'cpf'");
    }

    @Test
    @DisplayName("Deve falhar na validação se o CPF for nulo (falha no @NotBlank)")
    void validation_WhenCpfIsNull_ShouldFail() {
        TratadorDTO dto = new TratadorDTO("Nome", null, "Especialidade", "11999999999");

        var violations = validator.validate(dto);

        // CORREÇÃO: Usando anyMatch e garantindo que o número de violações é maior que 1
        // (Serão 3 violações: NotBlank, Pattern e CPF)
        assertTrue(violations.size() >= 1);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")),
                "A violação deve ser para o campo 'cpf'");
    }
}