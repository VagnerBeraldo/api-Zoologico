package br.com.senac.ado.zoologico.dto.EventoZoologico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EventoZoologicoDTO(
        @NotBlank String titulo,
        @NotBlank String descricao,
        @NotNull LocalDateTime data,
        @Positive Integer capacidade,
        @NotNull @Valid Set<UUID> animaisIds
) {}

