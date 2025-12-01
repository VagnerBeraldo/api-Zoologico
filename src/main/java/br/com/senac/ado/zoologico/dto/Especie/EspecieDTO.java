package br.com.senac.ado.zoologico.dto.Especie;

import jakarta.validation.constraints.NotBlank;

public record EspecieDTO(
        @NotBlank String nomeComum,
        @NotBlank String nomeCientifico,
        @NotBlank String statusConservacao
) {}
