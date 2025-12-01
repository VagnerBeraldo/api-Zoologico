package br.com.senac.ado.zoologico.dto.Veterianario;

import jakarta.validation.constraints.NotBlank;


public record VeterinarioDTO(
        @NotBlank String nome,
        @NotBlank String especialidade,
        @NotBlank String crmv
) {}
