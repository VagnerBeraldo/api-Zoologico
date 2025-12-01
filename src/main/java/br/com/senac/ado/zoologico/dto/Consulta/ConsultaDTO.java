package br.com.senac.ado.zoologico.dto.Consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record ConsultaDTO(
        @NotNull LocalDate dataConsulta,
        @NotBlank String diagnostico,
        @NotBlank String tratamento,
        String observacoes,
        @NotNull Boolean urgente,
        @NotNull UUID animalId,
        @NotNull UUID veterinarioId
) {}
