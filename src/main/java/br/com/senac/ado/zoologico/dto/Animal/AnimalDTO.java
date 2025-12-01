package br.com.senac.ado.zoologico.dto.Animal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record AnimalDTO(
        @NotBlank String nome,
        @NotBlank String sexo,
        @NotNull LocalDate dataNascimento,
        @NotBlank String status,
        @NotNull UUID especieId,
        @NotNull UUID habitatId,
        @NotNull @Valid Set<UUID> tratadoresIds
) {}
