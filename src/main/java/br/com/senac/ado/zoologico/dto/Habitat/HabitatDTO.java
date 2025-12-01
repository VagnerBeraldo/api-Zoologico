package br.com.senac.ado.zoologico.dto.Habitat;

import jakarta.validation.constraints.NotBlank;

public record HabitatDTO(
        @NotBlank String nome,
        @NotBlank String tipo,
        @NotBlank String areaM2,
        @NotBlank String descricao
) {}
