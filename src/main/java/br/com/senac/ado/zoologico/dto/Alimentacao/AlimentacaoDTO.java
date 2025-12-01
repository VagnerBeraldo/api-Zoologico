// DTO corrigido (assegure-se de salvar este arquivo)
package br.com.senac.ado.zoologico.dto.Alimentacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AlimentacaoDTO(
        @NotNull UUID animalId,
        @NotNull UUID tratadorId,
        @NotNull LocalDate data,
        @NotBlank String tipoRacao,
        @Positive Double quantidadeKg,
        @NotNull LocalDateTime horario
) {}
