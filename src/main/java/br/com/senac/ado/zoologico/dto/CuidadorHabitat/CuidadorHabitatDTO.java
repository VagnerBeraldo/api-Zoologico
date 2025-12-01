package br.com.senac.ado.zoologico.dto.CuidadorHabitat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CuidadorHabitatDTO(
        @NotNull UUID id,
        @NotNull UUID tratadorId,
        @NotNull UUID habitatId,
        @NotBlank String turno
) {}

